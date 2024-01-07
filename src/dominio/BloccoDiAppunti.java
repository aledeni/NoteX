package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BloccoDiAppunti implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private List<Etichetta> etichette;
	private UtenteRegistrato utenteRegistrato;
	private List<Nota> note;
	
	
	
	public BloccoDiAppunti() {
		super();
		this.id = -1;
		this.nome = "";
		this.etichette = new ArrayList<>();
		this.utenteRegistrato=new UtenteRegistrato();
		this.note = new ArrayList<>();
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Etichetta> getEtichette() {
		return etichette;
	}
	
	public void setEtichette(List<Etichetta> etichette) {
		this.etichette = etichette;
	}
	
	public UtenteRegistrato getUtenteRegistrato() {
		return utenteRegistrato;
	}
	
	public void setUtenteRegistrato(UtenteRegistrato utenteRegistrato) {
		this.utenteRegistrato = utenteRegistrato;
	}
	
	public List<Nota> getNote() {
		return note;
	}
	
	public void setNote(List<Nota> note) {
		this.note = note;
	}
	
	public boolean creaNota(String nomeNota, String percorsoFile, List<Etichetta> etichette) {
		boolean esiste = false;
		for(Nota n : note) {
			if(n.getNome().equals(nomeNota)) {
				esiste = true;
			}
		}
		if(esiste) {
			return false;
		}
		else {
			Nota nota = new Nota();
			nota.setNome(nomeNota);
			nota.setPercorsoFile(percorsoFile);
			nota.setEtichette(etichette);
			nota.setBloccoDiAppunti(this);
			note.add(nota);
			return true;
		}
	}
	
	public boolean eliminaNota(String nomeNota) {
		boolean esiste = false;
		Nota notaDaEliminare = null;
		for(Nota n : note) {
			if(n.getNome().equals(nomeNota)) {
				esiste = true;
				notaDaEliminare = n;
			}
		}
		if(esiste) {
			note.remove(notaDaEliminare);
			return true;
		}
		else {
			return false;
		}
	}
	
	public Optional<Nota> getNota(String nomeNota) {
		return this.note.stream().filter(n->{return n.getNome().equals(nomeNota);}).findFirst();
	}
	
	public double getPunteggioMedio() {
		return -1;
	}

	@Override
	public String toString() {
		return "BloccoDiAppunti [nome=" + nome + "]";
	}
	
	
	
}
