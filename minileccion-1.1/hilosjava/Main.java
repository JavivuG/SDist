/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hilosjava;

/**
 *
 * @author cllamas
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    ColaStrings cola = new ColaStrings();
    Runnable genera = new Productor(cola);
    Runnable come1 = new Consumidor(cola);
    Runnable come2 = new Consumidor(cola);
    Runnable come3 = new Consumidor(cola);

    new Thread(genera).start();
    new Thread(come1).start();
    new Thread(come2).start();
    new Thread(come3).start();
  }
}
