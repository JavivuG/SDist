package servicioHello;

// Cuando una interface hereda de otra interface lo hace con extends
public interface Hello extends java.rmi.Remote {
  String dimeHola(String miNombre) throws java.rmi.RemoteException;
}
