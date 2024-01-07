package dominio;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RecordLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime timestamp;
	private String descrizione;
	
	
	
	public RecordLog() {
		super();
		this.timestamp = LocalDateTime.now();
		this.descrizione = "";
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}
