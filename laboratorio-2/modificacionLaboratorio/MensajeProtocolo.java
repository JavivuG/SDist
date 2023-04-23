package protocolo;

public class MensajeProtocolo implements java.io.Serializable {
  private final Primitiva primitiva;
  private final String mensaje; /* HELLO, PUSH, PULL_OK */
  private final String playlist;  /* PUSH, PULL_WAIT, PULL_NOWAIT */
  private final String capitulo;
  private final String token;

  /* Constructor para ADDED, ERROR */
  public MensajeProtocolo(Primitiva p) throws MalMensajeProtocoloException {
    if (p == Primitiva.ADDED || p == Primitiva.OVERLOAD || p == Primitiva.NOAUTH) {
       this.primitiva = p;
       this.mensaje = this.playlist = this.token = this.capitulo = null;
    } else
       throw new MalMensajeProtocoloException();
  }

  /* Constructor para HELLO, PULL_OK, PULL_WAIT, PULL_NOWAIT */
  public MensajeProtocolo(Primitiva p, String mensaje) throws MalMensajeProtocoloException {
    if (p == Primitiva.AUTH || p==Primitiva.ERROR) {
      this.mensaje = mensaje;
      this.playlist = this.token = this.capitulo = null;
    } else if(p== Primitiva.NEWCHAP){
      this.capitulo = mensaje;
      this.playlist = this.token = this.mensaje = null;
    } else {
      throw new MalMensajeProtocoloException();
    }
    this.primitiva = p;
  }

  public MensajeProtocolo(Primitiva p, String playlist, String token) throws MalMensajeProtocoloException {
    if (p == Primitiva.WAIT4PL) {
      this.playlist = playlist;
      this.token  = token; // No hay que reasignar el token
      this.capitulo = this.mensaje = null; 
    } else
      throw new MalMensajeProtocoloException();
    this.primitiva = p;
  }

  /* Constructor para ADD2PL */
  public MensajeProtocolo(Primitiva p, String playlist, String capitulo, String token ) throws MalMensajeProtocoloException {
    if (p == Primitiva.ADD2PL) {
      this.primitiva = p;
      this.capitulo = capitulo;
      this.playlist = playlist;
      this.token = token; // No hay que reasignar el token
      this.mensaje = null;
    } else 
      throw new MalMensajeProtocoloException();
  }

  public Primitiva getPrimitiva() { return this.primitiva; }
  public String getMensaje() { return this.mensaje; }
  public String getPlaylist() { return this.playlist; }
  public String getCapitulo() { return this.capitulo; }
  public String getToken() { return this.token; }
  

  public String toString() { /* prettyPrinter de la clase */
    switch (this.primitiva) {
      case AUTH: 
      case ERROR:
        return this.primitiva+":"+this.mensaje ;
      case WAIT4PL:
        return this.primitiva+":"+this.playlist+":"+this.token;
      case ADD2PL:
        return this.primitiva+":"+this.playlist+":"+this.capitulo+":"+this.token ;
      case NEWCHAP:
        return this.primitiva+":"+this.capitulo;
      default :
        return this.primitiva.toString() ;
    }
  }
}
