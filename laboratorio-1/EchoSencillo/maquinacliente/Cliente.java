package netflix.socket;

public class Cliente {
  public static final int PUERTO = 2000;

  public static void main(String[] args) {
    String linea = null;
    try {
      java.io.BufferedReader tec =
          new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

      java.net.Socket miSocket = new java.net.Socket("localhost", PUERTO);

      java.io.BufferedReader inred = new java.io.BufferedReader(
          new java.io.InputStreamReader(miSocket.getInputStream()));

      java.io.PrintStream outred =
          new java.io.PrintStream(miSocket.getOutputStream());

      linea = inred.readLine();
      System.out.println(linea);

      while (true) {
        String credenciales;

        System.out.print("Introduce las credenciales: ");

        credenciales=tec.readLine();

        outred.println(credenciales);

        System.out.println(inred.readLine()); // Leo la respuesta que me da el servidor tras enviar el usuario y contrasena
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
