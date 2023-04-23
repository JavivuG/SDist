package taller2.ser;

import taller2.protocolo.MensajeProtocolo;

import taller2.MultiMap;
import taller2.protocolo.Primitiva;

class Sirviente implements Runnable {
  private final java.net.Socket socket;
  private final MultiMap<String, String> mapa;
  private final java.io.ObjectOutputStream oos;
  private final java.io.ObjectInputStream  ois;
  private final int ns;
  private static java.util.concurrent.atomic.AtomicInteger nInstance=
	           new java.util.concurrent.atomic.AtomicInteger();

  Sirviente(java.net.Socket s, MultiMap
           <String, String> c) throws java.io.IOException {
    this.socket = s;
    this.mapa   = c;
    this.ns     = nInstance.getAndIncrement();
    this.oos = new java.io.ObjectOutputStream(socket.getOutputStream());
    this.ois = new java.io.ObjectInputStream(socket.getInputStream());
  } // se invoca en el Servidor, usualmente

  public void run() {
    try {
      //añadir variables de nuestro hilo crear el thoken!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      while (true) {
	     String mensaje; // String multipurpose
        MensajeProtocolo me = (MensajeProtocolo) ois.readObject();
        MensajeProtocolo ms = null;
              // me y ms: mensajes entrante y saliente
        System.out.println("Sirviente: "+ns+": [ME: "+ me); // depuracion me
        switch (me.getPrimitiva()) {
        case AUTH:
            ms = new MensajeProtocolo(Primitiva.AUTH, "["+ns+": "+socket+"]");
            break;
        case ADD2PL:
            mapa.push(me.getPlaylist(), me.getMensaje());
	         synchronized (mapa) {
	             mapa.notify();
	         } // despierta un sirviente esperando en un bloqueo de "mapa"
            ms = new MensajeProtocolo(Primitiva.ADDED);
            break;
        case WAIT4PL:
	         synchronized (mapa) {
               while (null == (mensaje = mapa.pop(me.getPlaylist()))) {
                 mapa.wait(); // duerme el sirviente actual y libera bloqueo
	            }
            } 
            ms = new MensajeProtocolo(Primitiva.NEWGHAP, mensaje);
            break;
        default:
          if(me.getPrimitiva()!=null){
            ms = new MensajeProtocolo(Primitiva.ERROR);
          }else if(!me.getMensaje().equals(socket)){
            ms = new MensajeProtocolo(Primitiva.NOAUTH);
          }
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
    } catch (taller2.MalMensajeProtocoloException ex) {
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
