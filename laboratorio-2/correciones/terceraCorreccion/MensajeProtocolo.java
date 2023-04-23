package protocol;

public class MensajeProtocolo implements java.io.Serializable {
  private final Primitiva primitiva;
  private final String chapter; 
  private final String playList;
  private final String token; 

  /* Constructor para 1 argumento*/
  public MensajeProtocolo(Primitiva p) throws MalMensajeProtocoloException {
    if (p == Primitiva.ADDED || p == Primitiva.ERROR || p == Primitiva.NOAUTH || p == Primitiva.OVERLOAD) {
       this.primitiva = p;
       this.chapter = this.playList = this.token = null;
    } else
       throw new MalMensajeProtocoloException();
  }

  /* Constructor para 2 argumentos */
  public MensajeProtocolo(Primitiva p, String chapter) throws MalMensajeProtocoloException {
    if (p == Primitiva.AUTH || p == Primitiva.ADDED) {
      	this.chapter = chapter;
      	this.playList  = null;
    } else if (p == Primitiva.WAIT4PL) {
      	this.playList  = chapter;
      	this.chapter = null;
    }else{throw new MalMensajeProtocoloException();}
    this.primitiva = p;
  }

  /* Constructor para 3 argumentos */
  public MensajeProtocolo(Primitiva p, String playList, String chapter) throws MalMensajeProtocoloException {
    if (p == Primitiva.ADD2PL) {
      	this.primitiva = p;
      	this.chapter = chapter;
      	this.playList = playList;
    } else 
      	throw new MalMensajeProtocoloException();
  }
  
  /* Constructor para 4 argumentos */
  public MensajeProtocolo(Primitiva p, String playList, String chapter, String token) throws MalMensajeProtocoloException {
    if (p == Primitiva.ADD2PL) {
      	this.primitiva = p;
      	this.chapter = chapter;
      	this.playList = playList;
      	this.token = token;
    } else 
      	throw new MalMensajeProtocoloException();
  }
  
  public Primitiva getPrimitiva() { return this.primitiva; }
  public String getChapter() { return this.chapter; }
  public String getPlayList() { return this.playList; }
  public String getToken() { return this.token; }

  public String toString() { /* prettyPrinter de la clase */
    switch (this.primitiva) {
      case AUTH: 
      case NEWCHAP: 
       	return this.primitiva+":"+this.chapter ;
      case WAIT4PL:
        return this.primitiva+":"+this.playList ;
      case ADD2PL:
        return this.primitiva+":"+this.playList+":"+this.chapter+":"+this.token ;
      default :
        return this.primitiva.toString() ;
    }
  }
}
