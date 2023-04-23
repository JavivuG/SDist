package ser;

import protocolo.MensajeProtocolo;
import protocolo.Primitiva;
import java.util.UUID;
import distribuidos.mapqueue.MultiMap;

class Sirviente implements Runnable {
  private final java.net.Socket socket;
  private final String token = UUID.randomUUID().toString().substring(0,10);
  private final MultiMap<String, String> mapa;
  private final java.io.ObjectOutputStream oos;
  private final java.io.ObjectInputStream  ois;
  private final int ns;
  private static java.util.concurrent.atomic.AtomicInteger nInstance=new java.util.concurrent.atomic.AtomicInteger();

  Sirviente(java.net.Socket s, MultiMap<String, String> c) throws java.io.IOException {
    this.socket = s;
    this.mapa   = c;
    this.ns     = nInstance.getAndIncrement();
    this.oos = new java.io.ObjectOutputStream(socket.getOutputStream());
    this.ois = new java.io.ObjectInputStream(socket.getInputStream());
  } // se invoca en el Servidor, usualmente

  public void run() {
    try {
      String usuario=null;
      int contador=0;
      while (true) {
	     String mensaje; // String multipurpose
        MensajeProtocolo me = (MensajeProtocolo) ois.readObject();
        MensajeProtocolo ms;
        // me y ms: mensajes entrante y saliente
        if (contador==0 && me.getPrimitiva()!=Primitiva.AUTH){
          System.out.println("No se ha autenticado");
          break;
        }
        contador++;
        System.out.println("Sirviente: "+ns+": [ME: "+ me); // depuracion me

        switch (me.getPrimitiva()) {
          case AUTH:
            usuario=me.getMensaje();
            ms = new MensajeProtocolo(Primitiva.AUTH, token);
            break;
          case ADD2PL:
            if(!token.equals(me.getToken())){
              ms = new MensajeProtocolo(Primitiva.NOAUTH);
            }
            else {
              mapa.push(me.getPlaylist(), me.getCapitulo());
             synchronized (mapa) {
                 mapa.notify();
             } // despierta un sirviente esperando en un bloqueo de "mapa"
              ms = new MensajeProtocolo(Primitiva.ADDED);
              // Tengo que devolver el overload si hay muchos hilos esperand
            }
            break;
          case WAIT4PL:
            if(!token.equals(me.getToken())){
              ms = new MensajeProtocolo(Primitiva.NOAUTH);
            }
            else {
             synchronized (mapa) {
                 while (null == (mensaje = mapa.pop(me.getPlaylist()))) {
                   mapa.wait(); // duerme el sirviente actual y libera bloqueo
                }
              } 
              ms = new MensajeProtocolo(Primitiva.NEWCHAP, mensaje);
            }
            break;
          default:
            ms = new MensajeProtocolo(Primitiva.ERROR, "Bad Primitive");
        } // fin del selector segun el mensaje entrante
        
        oos.writeObject(ms); // concentra la escritura de mensajes ¿bueno?
         // depuracion de mensaje saliente
        System.out.println("Sirviente: "+ns+": [RESP: "+ms+"]");
      }
    } catch (java.io.IOException e) {
      System.err.println("Sirviente: "+ns+": [FIN]");
    } catch (ClassNotFoundException ex) {
      System.err.println("Sirviente: "+ns+": [ERR Class not found]");
    } catch (InterruptedException ex) {
      System.err.println("Sirviente: "+ns+": [Interrumpido wait()]");
    } catch (protocolo.MalMensajeProtocoloException ex) {
      System.err.println("Sirviente: "+ns+": [ERR MalMensajeProtocolo !!]");
    } finally {
      // seguimos deshaciéndonos de los sockets y canales abiertos.
      try {
          ois.close();
          oos.close();
          socket.close();
      } catch (Exception x) {
          System.err.println("Sirviente: "+ns+": [ERR Cerrando sockets]");
      }
    }
  }
}
