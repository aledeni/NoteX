package filesystem.util;

import java.io.IOException;

import org.apache.tika.exception.TikaException;

public class FileSystemUtil {
	private static ControllerFile controllerFile=initControllerFile();
	
	private static ControllerFile initControllerFile()
	{
		try {
			return new ControllerFile();
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		} 
	}
	
	public static ControllerFile getControllerFile()
	{
		return controllerFile;
	}
}
