package netflix.socket;

import java.util.concurrent.ConcurrentHashMap;


public class Servidor {
  public static final int PUERTO = 2000;
  public static ConcurrentHashMap<String,String> usuariosExistentes;
  public static int clientesActivos;

  public static void main(String[] args) {
    try (java.net.ServerSocket servidor = new java.net.ServerSocket(PUERTO)) {
      usuariosExistentes = new ConcurrentHashMap<>();
      while (true) {
        try {
          java.net.Socket sock = servidor.accept();
          java.io.BufferedReader inred = new java.io.BufferedReader(
              new java.io.InputStreamReader(sock.getInputStream()));
          java.io.PrintStream outred =
              new java.io.PrintStream(sock.getOutputStream());

          Runnable sirviente = () -> {
            try {
              // Muestro mensaje de bienvenidao
              clientesActivos=Thread.activeCount()-1;
              System.out.println("Hay conectados: "+clientesActivos+ " usuarios");
              outred.println("Welcome to NETFLIX, please type your credentials to REGISTER it");

              String datosUsuario, usuario=null;
              String[] listaDatosUsuario;

              while ((datosUsuario = inred.readLine()) != null) {
                listaDatosUsuario = datosUsuario.split("-");

                if (listaDatosUsuario.length != 2) { // Tiene mal formato el usuario introducido
                  outred.println("Bad format used. Please type <username>-<password>");
                } 
                else {
                  // Meto los usuarios al ConcurrentHashMap
                  if (usuariosExistentes.putIfAbsent(listaDatosUsuario[0], listaDatosUsuario[1] ) != null) { // El usuario ya existe
                    outred.println("Err: USERNAME already taken. Enter new credentials:");
                  } 
                  else { // Si el usuario no existia
                    outred.println("User successfully registered in. Enter new credentials:");
                    usuario = listaDatosUsuario[0];
                  }
                }
                
                if (sock.getInputStream().read() == -1){ // Detecta si se cierra la conexion del lado del cliente
                  clientesActivos--;
                  System.out.println("Hay conectados: "+clientesActivos+ " usuarios");
                  if (usuario==null){
                    System.out.println("El cliente no ha registrado ningun usuario");
                  }
                  else {
                    System.out.println(usuario); // Muestra el ultimo usuario registrado por dicho usuario
                    System.out.flush();
                  }
                  sock.close();
                }
              }
            } catch (java.io.IOException ioe) {
              System.err.println("Cerrando socket de cliente");
              ioe.printStackTrace(System.err);
            }
          };
          Thread t = new Thread(sirviente, "Sirviente echo");
          t.start();
        } catch (java.io.IOException e) {
          System.err.println("Cerrando socket de cliente");
          e.printStackTrace(System.err);
        }
      } // fin del while()
    } catch (java.io.IOException e) {
      System.err.println("Cerrando socket de servicio");
      e.printStackTrace(System.err);
    }
  }
}
