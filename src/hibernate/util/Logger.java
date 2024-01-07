package hibernate.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
	
	private File f;
	private PrintStream  fout;
	
	public Logger(String fileName) throws FileNotFoundException
	{
		f=new File(fileName);
		fout=new PrintStream(f);
	}
	
	public void info(String msg)
	{
		System.out.println("INFO: "+msg);
		fout.println("INFO: "+msg);
	}
	
	public void success(String msg)
	{
		System.out.println("SUCCESS: "+msg);
		fout.println("SUCCESS: "+msg);
	}
	
	public void alert(String msg)
	{
		System.out.println("ALERT: "+ msg);
		fout.println("ALERT: "+ msg);
	}
	
	public void alert(String msg, Exception e)
	{
		System.out.println("ALERT: "+msg);
		System.out.println("CAUSED BY: "+e);
		fout.println("ALERT: "+msg);
		fout.println("CAUSED BY: "+e);
	}
	public void error(String msg)
	{
		System.out.println("ERROR: "+ msg);
	}
	
	public void error(String msg, Exception e)
	{
		System.out.println("ERROR: "+msg);
		System.out.println("CAUSED BY: "+e);
		fout.println("ERROR: "+msg);
		fout.println("CAUSED BY: "+e);
	}
	
	public void println(String msg)
	{
		System.out.println(msg);
		fout.println(msg);
	}
	public void println()
	{
		System.out.println();
		fout.println();
	}
	public void close()
	{
		fout.close();
	}
	


}
