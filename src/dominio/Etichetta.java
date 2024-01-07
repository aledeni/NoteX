package dominio;

import java.io.Serializable;

public class Etichetta implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private String valore;
	
	
	
	public Etichetta() {
		super();
		this.id=-1;
		this.nome = "";
		this.valore = "";
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



	public String getValore() {
		return valore;
	}



	public void setValore(String valore) {
		this.valore = valore;
	}
	
	
	
}
