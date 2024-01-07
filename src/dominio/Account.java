package dominio;

import java.io.Serializable;

public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String passwordHash;
	private String salt;
	private String hashAlgorithm;
	
	
	
	public Account() {
		super();
		this.id = -1;
		this.username = "";
		this.passwordHash = "";
		this.salt = "";
		this.hashAlgorithm = "";
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getHashAlgorithm() {
		return hashAlgorithm;
	}
	
	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}
	
}
