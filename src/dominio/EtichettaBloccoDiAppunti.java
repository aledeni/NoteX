package dominio;

import java.io.Serializable;

public class EtichettaBloccoDiAppunti extends Etichetta implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BloccoDiAppunti bloccoDiAppunti;
	public EtichettaBloccoDiAppunti() {
		super();
		this.bloccoDiAppunti=new BloccoDiAppunti();
	}
	public BloccoDiAppunti getBloccoDiAppunti() {
		return bloccoDiAppunti;
	}
	public void setBloccoDiAppunti(BloccoDiAppunti bloccoDiAppunti) {
		this.bloccoDiAppunti = bloccoDiAppunti;
	}
	
	
	

}
