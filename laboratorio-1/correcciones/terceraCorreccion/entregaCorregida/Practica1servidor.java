package SDIS;

public class Practica1servidor {
  public static final int PUERTO = 2000;
  public static void main(String[] args) {
    java.util.concurrent.ConcurrentHashMap<String, String> tabla;
    try (java.net.ServerSocket servidor = new java.net.ServerSocket(PUERTO)) {
      tabla = new java.util.concurrent.ConcurrentHashMap<>();

      try {
        java.net.Socket sock = servidor.accept();
        java.io.BufferedReader inred = new java.io.BufferedReader(
            new java.io.InputStreamReader(sock.getInputStream()));
        java.io.PrintStream outred =
            new java.io.PrintStream(sock.getOutputStream());

        Runnable sirviente = () -> {

          outred.println(
              " Welcome to NETFLIX, please type your credentials to REGISTER it");
          try {
            String credenciales;

            while ((credenciales = inred.readLine()) != null) { // lee de la red
              String contrasena = "";
              String usuario = "";
              int index = 0;
              if ((index = credenciales.indexOf("-")) == -1) {
                outred.println(
                    " Bad format used. Please type <username>-<password>");
              } else {
                for (int i = 0; i < index; i++) {
                  usuario = usuario + credenciales.charAt(i);
                }

                for (int j = index + 1; j < credenciales.length(); j++) {
                  contrasena = contrasena + credenciales.charAt(j);
                }

                if ((tabla.putIfAbsent(usuario, contrasena) != null)) {
                  outred.println(
                      "Err: USERNAME already taken. Enter new credentials:");
                } else {
                  outred.println("Introduzca nuevos usario y contrasena");
                }
              }
            }
          } catch (java.io.IOException e) {
            System.err.println("Cerrando socket de cliente");
            e.printStackTrace(System.err);
          }
        };
        Thread hservidor = new Thread(sirviente, "Hilo servidor");
        hservidor.start();
      } catch (java.io.IOException e) {
        System.err.println("Cerrando socket de cliente");
        e.printStackTrace(System.err);
      }

    } catch (java.io.IOException e) {
      System.err.println("Cerrando socket de servicio");
      e.printStackTrace(System.err);
    }
  }
}
