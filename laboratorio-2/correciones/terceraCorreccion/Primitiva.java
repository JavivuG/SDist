package protocol;

public enum Primitiva {
  AUTH,   //HELLO       	/* nombre usuario/respuesta */ 
  NOAUTH, 			/*Cuando el token no coincide con lo del cliente devuelve esto*/
  ADD2PL,   //PUSH        	/* id-cola + mensaje */
  ADDED,    //PUSH_OK	   	/* (ack de push) */
  WAIT4PL,  //PULL_WAIT    	/* id-cola */
  NEWCHAP,   //PULL_OK     		/* mensaje (resp. pull)*/
  OVERLOAD,			/* respuesta cuando hay muchos clientes*/	
  ERROR; //NOUNDERSTAND		/* (error protocolo) */
}
