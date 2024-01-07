package dominio;

import java.io.Serializable;
import java.util.Optional;

public class Segnalazione implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String testo;
	private StatoSegnalazione stato;
	private UtenteRegistrato utenteSegnalante;
	private UtenteRegistrato utenteSegnalato;
	private Optional<Assistente> assistente;
	
	
	
	public Segnalazione() {
		super();
		this.id = -1;
		this.testo = "";
		this.stato = StatoSegnalazione.DA_GESTIRE;
		this.utenteSegnalante = new UtenteRegistrato();
		this.utenteSegnalato = new UtenteRegistrato();
		this.assistente = Optional.empty();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTesto() {
		return testo;
	}
	
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	public StatoSegnalazione getStato() {
		return stato;
	}
	
	public void setStato(StatoSegnalazione stato) {
		this.stato = stato;
	}
	
	public UtenteRegistrato getUtenteSegnalante() {
		return utenteSegnalante;
	}
	
	public void setUtenteSegnalante(UtenteRegistrato utenteSegnalante) {
		this.utenteSegnalante = utenteSegnalante;
	}
	
	public UtenteRegistrato getUtenteSegnalato() {
		return utenteSegnalato;
	}
	
	public void setUtenteSegnalato(UtenteRegistrato utenteSegnalato) {
		this.utenteSegnalato = utenteSegnalato;
	}
	
	public Optional<Assistente> getAssistente() {
		return assistente;
	}
	
	public void setAssistente(Optional<Assistente> assistente) {
		this.assistente = assistente;
	}
	
}
