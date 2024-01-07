package dominio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Log implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<RecordLog> records;
	private File fileLog;
	private final String LOG_FILE_PATH="./log/log.txt";
	
	
	
	public Log() {
		super();
		this.records = new ArrayList<>();
		this.fileLog = new File(LOG_FILE_PATH);
	}

	public List<RecordLog> getRecords() {
		return records;
	}
	
	public void setRecords(List<RecordLog> records) {
		this.records = records;
	}
	
	public File getFileLog() {
		return fileLog;
	}
	
	public void setFileLog(File fileLog) {
		this.fileLog = fileLog;
	}
	
	public void scriviLog(String record) {
		// supponendo che record corrisponda alla descrizione
		RecordLog rl = new RecordLog();
		rl.setDescrizione(record);
		rl.setTimestamp(LocalDateTime.now());
		records.add(rl);
		DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
		try {
			FileWriter fw = new FileWriter(fileLog);
			fw.append(rl.getTimestamp().format(formatter) + " " + rl.getDescrizione());
			fw.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
