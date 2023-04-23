package helloServer;

public class HelloImpl
  extends java.rmi.server.UnicastRemoteObject
  implements Hello {
   
    public HelloImpl() throws java.rmi.RemoteException {
      super(); // es el constructor de UnicastRemoteObject.
    }

    public String sayHello() throws java.rmi.RemoteException {
      return "Hola, mundo!";
    }
}
