package protocolo;

public enum Primitiva {
  AUTH,          /* nombre usuario/respuesta */
  NOAUTH,
  ADD2PL,           /* id-cola + mensaje */
  ADDED,        /* (ack de push) */
  WAIT4PL,      /* id-cola */
  NEWCHAP,        /* mensaje (resp. pull)*/
  ERROR,  /* (error protocolo) */
  OVERLOAD;
}
