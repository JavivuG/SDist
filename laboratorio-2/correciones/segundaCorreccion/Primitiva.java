package protocolo;

public enum Primitiva {
  AUTH,//HELLO,          /* nombre usuario/respuesta */
  NOAUTH,//PUSH,           /* id-cola + mensaje */
  ADDED,//PUSH_OK,        /* (ack de push) */
  WAIT4PL, //PULL_WAIT,      /* id-cola */
  //PULL_NOWAIT,    /* id-cola */
  NEWCHAP, //PULL_OK,        /* mensaje (resp. pull)*/
  //NOTHING,        /* (cola vacía, resp. pull_nowait) */
  //NOTUNDERSTAND;  /* (error protocolo) */
  
  //PULL_WAIT; SI MUCHOS CLIENTES return OVERLOAD
  //PULL_OK
  OVERLOAD,
  ERROR;
}
