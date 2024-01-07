package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Nota implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private List<Etichetta> etichette;
	private BloccoDiAppunti bloccoDiAppunti;
	private String percorsoFile;
	private List<Recensione> recensioni;
	
	
	
	public Nota() {
		super();
		this.id = -1;
		this.nome = "";
		this.etichette = new ArrayList<>();
		this.bloccoDiAppunti = new BloccoDiAppunti();
		this.percorsoFile = "";
		this.recensioni = new ArrayList<>();
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
	
	public BloccoDiAppunti getBloccoDiAppunti() {
		return bloccoDiAppunti;
	}
	
	public void setBloccoDiAppunti(BloccoDiAppunti bloccoDiAppunti) {
		this.bloccoDiAppunti = bloccoDiAppunti;
	}
	
	public String getPercorsoFile() {
		return percorsoFile;
	}
	
	public void setPercorsoFile(String percorsoFile) {
		this.percorsoFile = percorsoFile;
	}
	
	public List<Recensione> getRecensioni() {
		return recensioni;
	}
	
	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}
	
	public double getPunteggioMedio() {
		return -1;
	}
	
	public boolean addRecensione(Recensione recensione) {
		boolean esiste = false;
		for(Recensione r : recensioni) {
			if(r.getRecensore().getUsername().equals(recensione.getRecensore().getUsername())) {
				esiste = true;
			}
		}
		if(esiste) {
			return false;
		}
		else {
			recensioni.add(recensione);
			return true;
		}
	} 
	
}
