package tcp.server;

public class Launcher {
  public static void main(String[] args) throws java.io.IOException {
    Server ser = new Server(
        2000, (entrada) -> { return entrada; }); // este es el servicio ECHO
    ser.startServer(true);
  }
}
