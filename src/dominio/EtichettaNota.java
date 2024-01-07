package dominio;

import java.io.Serializable;

public class EtichettaNota extends Etichetta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Nota nota;
	public EtichettaNota() {
		super();
		this.nota=new Nota();
	}
	public Nota getNota() {
		return nota;
	}
	public void setNota(Nota nota) {
		this.nota = nota;
	}
	
	
	

}
