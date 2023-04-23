package taller2.protocolo;

public enum Primitiva {
  AUTH,          /* nombre usuario/respuesta token de accion */
  NOAUTH,         /*mensaje cuando el token del servidor no coincide */
  ADD2PL,         /* pl  parecido a push: id-cola + mensaje*/
  ADDED,          /*ack de ADD2PL */
  WAIT4PL,        /* parecido a pull_wait id-cola */
  NEWGHAP,        /* parecido a pull_ok  mensaje (resp. pull)*/
  OVERLOAD,       /*repuesta del wait4pl cuandpp el numero de espera de clientes supera el limite */
  ERROR;          /*msg Bad primitive */
}
