package taller2.protocolo;

import taller2.MalMensajeProtocoloException;

public class MensajeProtocolo implements java.io.Serializable {
  private final Primitiva primitiva;
  private final String capitulo; /* HELLO, PUSH, PULL_OK */
  private final String playlist;  /* PUSH, PULL_WAIT, PULL_NOWAIT */

  /* Constructor para PUSH_OK, NOTHING, NOTUNDERSTAND */
  public MensajeProtocolo(Primitiva p)
      throws MalMensajeProtocoloException {
    if (p == Primitiva.NEWGHAP || p == Primitiva.ERROR || p == Primitiva.NOAUTH || p == Primitiva.ADDED) {
       this.primitiva = p;
       this.capitulo = this.playlist = null;
    } else
       throw new MalMensajeProtocoloException();
  }

  /* Constructor para HELLO, PULL_OK, PULL_WAIT, PULL_NOWAIT */
  public MensajeProtocolo(Primitiva p, String mensaje)
      throws MalMensajeProtocoloException {
    if (p == Primitiva.AUTH || p == Primitiva.NEWGHAP) {
      this.capitulo = mensaje;
      this.playlist  = null;
    } else if (p == Primitiva.WAIT4PL) {
      this.playlist  = mensaje;
      this.capitulo = null;
    } else
      throw new MalMensajeProtocoloException();
    this.primitiva = p;
  }

  /* Constructor para ADD2PL */
  public MensajeProtocolo(Primitiva p, String idCola, String mensaje)
      throws MalMensajeProtocoloException {
    if (p == Primitiva.ADD2PL) {
      this.primitiva = p;
      this.capitulo = mensaje;
      this.playlist = idCola;
    } else 
      throw new MalMensajeProtocoloException();
  }

  public Primitiva getPrimitiva() { return this.primitiva; }
  public String getMensaje() { return this.capitulo; }
  public String getPlaylist() { return this.playlist; }

  public String toString() { /* prettyPrinter de la clase */
    switch (this.primitiva) {
      case AUTH: 
      case NOAUTH:
        return this.primitiva+":"+this.capitulo ;
      case WAIT4PL:
        return this.primitiva+":"+this.playlist ;
      case ADD2PL:
        return this.primitiva+":"+this.playlist+":"+this.capitulo ;
      default :
        return this.primitiva.toString() ;
    }
  }
}
