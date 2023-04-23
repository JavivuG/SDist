package cliente;

import protocolo.MalMensajeProtocoloException;
import protocolo.MensajeProtocolo;
import protocolo.Primitiva;

public class Wait4PL {
  final private int PUERTO = 2000;
  static java.io.ObjectInputStream ois = null;
  static java.io.ObjectOutputStream oos = null;
  public static void main(String[] args) throws java.io.IOException {
    String host = args[0]; // El host lo meto al ejecutar el programa
    String usuario = args[1];
    String nombrePlaylist = args[2];
    String linea;
    java.net.Socket sock = new java.net.Socket(host, 2000);
    try {
      // Creación de los canales de serialización de objetos 
      oos =new java.io.ObjectOutputStream(sock.getOutputStream());

      ois = new java.io.ObjectInputStream(sock.getInputStream());

      // Sin teclado, probemos las primitivas por programa 
      System.out.println("CONNECTION ESTABLISHED. TRYING TO GET API TOKEN FOR USER: "+ usuario);

      // INICIO Escenario 1 
      String token = autenticacion(new MensajeProtocolo(Primitiva.AUTH, usuario)); // Espera respuesta del servidor

      pruebaPeticionRespuesta(new MensajeProtocolo(Primitiva.WAIT4PL, nombrePlaylist, token)); // Espera respuesta del servidor

      // a estas alguras algún cliente externo debería insertar un mensaje en la cola FIN Esceniario 1
    } catch (java.io.EOFException e) {
      System.err.println("Cliente: Fin de conexión.");

    } catch (java.io.IOException e) {
      System.err.println("Cliente: Error de apertura o E/S sobre objetos:"+e);

    } catch (MalMensajeProtocoloException e) {
      System.err.println("Cliente: Error mensaje Protocolo: " + e);

    } catch (Exception e) {
      System.err.println("Cliente: Excepción. Cerrando Sockets: " + e);

    } finally {
      ois.close();
      oos.close();
      sock.close();
    }
  }
  /* Prueba una interacción de escritura y lectura con el servidor */
  static String autenticacion(MensajeProtocolo mp) throws java.io.IOException, MalMensajeProtocoloException, ClassNotFoundException {
    System.out.println("> " + mp);
    oos.writeObject(mp);

    MensajeProtocolo mensajeServidor = (MensajeProtocolo)ois.readObject();
    System.out.print("TOKEN received: " + mensajeServidor.getMensaje()+". ");

    return mensajeServidor.getMensaje();

  }

  static void pruebaPeticionRespuesta(MensajeProtocolo mp) throws java.io.IOException, MalMensajeProtocoloException, ClassNotFoundException {
    System.out.println("EXECUTING: " + mp.toString());
    oos.writeObject(mp);

    MensajeProtocolo mensajeServidor = (MensajeProtocolo)ois.readObject();


  }
}

