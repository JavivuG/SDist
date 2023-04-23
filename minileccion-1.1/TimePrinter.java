/*
 * TimePrinter.java Imprime la hora cada segundo.
 */
package ejemplo.timeprinter;

public class TimePrinter implements Runnable {
  private Integer entero;

  public TimePrinter(int num) { entero = num; }

  public void run() {
    while (true) {
      System.out.println(entero + ": " + new java.util.Date());
      entero++;
      try {
        Thread.sleep(1000); // milis
      } catch (InterruptedException x) {
        System.out.println("Fin del reloj");
      }
    }
  }
}
