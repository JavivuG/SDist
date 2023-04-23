import java.util.UUID;
public class Prueba {

  public static void main(String[] args) {
    String token=UUID.randomUUID().toString();
    System.out.println(token.length());
    System.out.println(token.substring(0,10).length());
    System.out.println(token.substring(0,10));
    System.out.println(token);
  }
}
