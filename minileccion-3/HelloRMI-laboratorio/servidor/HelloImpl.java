package servicioHello.servidor;

public class HelloImpl extends java.rmi.server.UnicastRemoteObject implements servicioHello.Hello {

  final String mensaje = "\nSoy un objeto muy remoto";

  // Nacimiento de un objeto remoto
  public HelloImpl() throws java.rmi.RemoteException {
    super();
  }
  
  public String dimeHola(String miNombre) throws java.rmi.RemoteException {
    return "Hello "+ miNombre +", nice to meet you!" + mensaje;
  }

}
