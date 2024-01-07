package dominio;

import java.io.Serializable;
import java.util.Optional;

public class Recensione implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int punteggio;
	private String commento;
	private UtenteRegistrato recensore;
	private Nota notaRecensita;
	
	
	
	public Recensione() {
		super();
		this.id = -1;
		this.punteggio = -1;
		this.commento = "";
		this.recensore = new UtenteRegistrato();
		this.notaRecensita = new Nota();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getPunteggio() {
		return punteggio;
	}
	
	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
	public String getCommento() {
		return commento;
	}
	
	public void setCommento(String commento) {
		this.commento = commento;
	}
	
	public UtenteRegistrato getRecensore() {
		return recensore;
	}
	
	public void setRecensore(UtenteRegistrato recensore) {
		this.recensore = recensore;
	}
	
	public Nota getNotaRecensita() {
		return notaRecensita;
	}
	
	public void setNotaRecensita(Nota notaRecensita) {
		this.notaRecensita = notaRecensita;
	}
	
}
