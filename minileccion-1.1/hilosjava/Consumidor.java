/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hilosjava;
import java.util.UUID;

/**
 *
 * @author cllamas
 */
public class Consumidor implements Runnable {
  private final ColaStrings cola;
  private final String identificador;

  public Consumidor(ColaStrings c) {
    this.cola = c;
    this.identificador = UUID.randomUUID().toString();
  }

  public void run() {
    String linea;

    while (true) {
      linea = cola.pop();
      System.out.println(">> " + linea + " id: " + identificador); // imprime
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        System.out.println("Fin del reloj");
      }
    }
  }
}
