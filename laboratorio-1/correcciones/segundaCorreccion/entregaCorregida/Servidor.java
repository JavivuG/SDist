package ejemplo.socket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Servidor {
  public static final int PUERTO = 2000;

  public static void main(String[] args) {
    try (java.net.ServerSocket servidor =
            new java.net.ServerSocket(PUERTO)) {

      System.out.println("----Servidor esperando al cliente ----");
      try (java.net.Socket sock = servidor.accept()) {
  
	
        java.io.BufferedReader inred =
	    new java.io.BufferedReader(
                  new java.io.InputStreamReader(sock.getInputStream()));
        java.io.PrintStream    outred =
	    new java.io.PrintStream(sock.getOutputStream());
  
        outred.println("Welcome to NETFLIX, please type your credentials to             REGISTER it");

	String linea;

	Map<String, String> usuarios
            = new ConcurrentHashMap<String, String>();
	
             
        while ((linea = inred.readLine()) != null) { 
          outred.println("Introduzca: <username>-<password>");

	//Separación del string username-password 
		try{

		    String[]parts=linea.split("-");
	            String usuario = parts[0]; 
	            String contra = parts[1];

	//Usuario libre
		if (usuarios.containsKey(usuario)==false){
        	   usuarios.put(usuario,contra);
		   outred.println(" User successfully registered in. Enter new credentials:");
		}

	//Usuario ya existe
		else{
	    	   outred.println(" Err: USERNAME already taken. Enter new credentials:");
		}
		}

                catch(Exception e){ outred.println("Bad format used. Please type <username>-<password>");}


	System.out.println("Echoing: "+linea);
           outred.println(linea);
        }
      } catch (java.io.IOException e) {
	System.err.println("Cerrando socket de cliente");
        e.printStackTrace(System.err);
      }
    } catch (java.io.IOException e) {
      System.err.println("Cerrando socket de servicio");
      e.printStackTrace(System.err);
    }
  }
}
