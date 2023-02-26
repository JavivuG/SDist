package ejemplo;

public class Launcher {

  public static void main(String[] args) {
    final Runnable tarea = new ejemplo.timeprinter.TimePrinter(101);
    final Runnable otraTarea = new ejemplo.timeprinter.TimePrinter(201);

    Thread hiloNuevo =
        new Thread(tarea, "Hilo de TimePrinter"); // Creo el primer hilo
    Thread otroHiloNuevo =
        new Thread(otraTarea, "Otro hilo de TimePrinter"); // Creo el otro hilo
    // Ahora tengo 3 hilos
    hiloNuevo.start();
    otroHiloNuevo.start();
    // Ahora queda 2 hilso
  }
}
