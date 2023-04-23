package protocol.common;

public class MensajeProtocolo implements java.io.Serializable {
  private final Primitiva primitiva;
  private final String mensaje; /* HELLO, PUSH, PULL_OK */
  private final String idCola;  /* PUSH, PULL_WAIT, PULL_NOWAIT */
  private final String token;

  /* Constructor para PUSH_OK, NOTHING, NOTUNDERSTAND, ADDED */
  public MensajeProtocolo(Primitiva p)
      throws MalMensajeProtocoloException {
    if (p == Primitiva.PUSH_OK || p == Primitiva.NOTUNDERSTAND ||
        p == Primitiva.NOTHING) {
       this.primitiva = p;
       this.mensaje = this.idCola = null;
    } else
       throw new MalMensajeProtocoloException();
  }

  /* Constructor para HELLO, PULL_OK, PULL_WAIT, PULL_NOWAIT */	 	//WAIT4PL, AUTH, ERROR
  public MensajeProtocolo(Primitiva p, String mensaje)
      throws MalMensajeProtocoloException {
    if (p == Primitiva.AUTH || p == Primitiva.WAIT4PL || p == Primitiva.NEWCHAP) {
      this.mensaje = mensaje;
      this.idCola  = null;
    } else if (p == Primitiva.PULL_WAIT || p == Primitiva.PULL_NOWAIT) {
      this.idCola  = mensaje;
      this.mensaje = null;
    } else
      throw new MalMensajeProtocoloException();
    this.primitiva = p; //ELSE ERROR
  }

  /* Constructor para PUSH */ 		//ADD2PL
  public MensajeProtocolo(Primitiva p, String playlist, String chapter, String token)
      throws MalMensajeProtocoloException {
    if (p == Primitiva.ADD2PL) {
      this.primitiva = p;
      this.mensaje = mensaje;
      this.idCola = idCola;
      this.token = token
    } else 
      throw new MalMensajeProtocoloException();
  }

  public Primitiva getPrimitiva() { return this.primitiva; }
  public String getMensaje() { return this.mensaje; }
  public String getIdCola() { return this.idCola; }

  public String toString() { /* prettyPrinter de la clase */
    switch (this.primitiva) {
      case HELLO: 
      case PULL_OK:
        return this.primitiva+":"+this.mensaje ;
      case PULL_WAIT:
      case PULL_NOWAIT:
        return this.primitiva+":"+this.idCola ;
      case PUSH:
        return this.primitiva+":"+this.idCola+":"+this.mensaje ;
      default :
        return this.primitiva.toString() ;
    }
  }
}
