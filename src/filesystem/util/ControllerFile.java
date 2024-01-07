package filesystem.util;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

public class ControllerFile {

	private TikaConfig tikaConfig;
	private final String RELATIVE_REMOVED_PATH = "notes_removed";
	private final String RELATIVE_READABLE_PATH = "notes_readable";
	private List<String> supportedTypes;

	public ControllerFile() throws TikaException, IOException {
		super();
		this.tikaConfig = new TikaConfig();
		this.supportedTypes = List.of("application/pdf");
	}

	public String persist(ServletContext context, Part fileDiAppunti) throws IllegalFileTypeException {
		// parameter check
		if (context == null)
			throw new NullPointerException("context is null");
		if (fileDiAppunti == null)
			throw new NullPointerException("fileDiAppunti is null");
		
		// declaration and initialization
		String relativeFilePath = "";
		Metadata metadata = new Metadata();
		String mimeType = "";
		File file = null;
		
		// file type analysis and file creation on the server
		try {
			mimeType = tikaConfig.getDetector().detect(fileDiAppunti.getInputStream(), metadata).toString();
			if (supportedTypes.contains(mimeType)) {
				do {
					relativeFilePath = RELATIVE_READABLE_PATH + File.separator + UUID.randomUUID().toString();
					file = new File(context.getRealPath(relativeFilePath));
				} while (file.exists());
				System.out.println(context.getRealPath(relativeFilePath));
				fileDiAppunti.write(context.getRealPath(relativeFilePath));
			} else {
				throw new IllegalFileTypeException();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		// return 
		return relativeFilePath;
	}
	
	public boolean elimina(ServletContext context, String percorsoFileDiAppunti)
	{
		// parameter check
		if(context==null) throw new NullPointerException("context is null");
		if(percorsoFileDiAppunti==null) throw new NullPointerException("context is null");
		
		// declaration and initialization
		boolean result=false;
		File file = new File(context.getRealPath(percorsoFileDiAppunti));
		
		if(file.exists())
		{
			result=file.delete();
		}else {
			throw new IllegalArgumentException("percorsoFileDiAppunti is not valid");
		}
		return result;
		
	}

	public String rimuovi(ServletContext context, String percorsoFileDiAppunti) {
		// parameter check
		if (context == null)
			throw new NullPointerException("contex is null");
		if (percorsoFileDiAppunti == null)
			throw new NullPointerException("percorsoFileDiAppunti is null");
		
		// declaration and initialization
		String relativeRemovedFilePath = "";
		File file = new File(context.getRealPath(percorsoFileDiAppunti));
		File removedFile = null;
		
		// removing the file
		if(file.exists())
		{
			do {
				relativeRemovedFilePath=RELATIVE_REMOVED_PATH+File.separator+UUID.randomUUID();
				removedFile=new File(relativeRemovedFilePath);
			}while(removedFile.exists());
			file.renameTo(removedFile);
		}else
		{
			throw new IllegalArgumentException("percorsoFileDiAppunti is not valid");
		}
		
		// return 
		return relativeRemovedFilePath;
	}

	public String ripristina(ServletContext context, String percorsoFileDiAppunti) {
		// parameter check
		if (context == null)
			throw new NullPointerException("contex is null");
		if (percorsoFileDiAppunti == null)
			throw new NullPointerException("percorsoFileDiAppunti is null");
		
		// declaration and initialization
		String relativeRestoredFilePath = "";
		File file = new File(context.getRealPath(percorsoFileDiAppunti));
		File removedFile = null;
		
		// restoring the file
		if(file.exists())
		{
			do {
				relativeRestoredFilePath=RELATIVE_READABLE_PATH+File.separator+UUID.randomUUID();
				removedFile=new File(relativeRestoredFilePath);
			}while(removedFile.exists());
			file.renameTo(removedFile);
		}else
		{
			throw new IllegalArgumentException("percorsoFileDiAppunti is not valid");
		}
		
		// return 
		return relativeRestoredFilePath;
	}

}