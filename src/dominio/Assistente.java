package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Assistente extends Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Segnalazione> segnalazioni;
	
	

	public Assistente() {
		super();
		this.segnalazioni = new ArrayList<>();;
	}

	public List<Segnalazione> getSegnalazioni() {
		return segnalazioni;
	}

	public void setSegnalazioni(List<Segnalazione> segnalazioni) {
		this.segnalazioni = segnalazioni;
	}
	
}
