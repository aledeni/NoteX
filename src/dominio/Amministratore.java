package dominio;

import java.io.Serializable;

public class Amministratore extends Account implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	
	

	public Amministratore() {
		super();
		this.email = "";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
