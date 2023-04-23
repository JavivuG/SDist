package servicioHello.servidor;

public class Lanzador {
  public static void main(String[] args){
    try {
      // Siempre debo de crear objetos remotos
      HelloImpl objetoRemoto = new HelloImpl();

      java.rmi.registry.Registry conserje = java.rmi.registry.LocateRegistry.getRegistry(2101);

      conserje.rebind("veryKindObject", objetoRemoto);
      System.out.println("Servicio de objetos lanzado\n-----------\n");
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }
}

