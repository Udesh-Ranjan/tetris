package tetris.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Date;
import java.io.File;
import java.io.FileNotFoundException;

public final class TetrisLogger{
	private final static TetrisLogger tetrisLogger;
	private final static DateFormat dateFormat;
	private static PrintStream out=null;
	private static boolean deletePreviousFiles;
	private static String logDir;
	private static final String extension;
	private static String logFileName;
	private static File logFile;
	static{
		tetrisLogger=new TetrisLogger();
		dateFormat=new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");

		logDir="/home/dev/logs/";
		extension=".log";
		logFileName=logDir+getTime()+extension;
		logFile=new File(logFileName);

		deletePreviousFiles=true;

		try{
			out=new PrintStream(new FileOutputStream(logFileName));
			if(deletePreviousFiles){
				try{
					tetrisLogger.logInfo("Cleaning all log files inside : "+logDir);
					cleanUp(new File(logDir));
				}catch(IOException ioException){
					tetrisLogger.logInfo("Exception while cleaning previous files : "+ioException.toString());
				}
			}
			System.setErr(out);
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}
	private TetrisLogger(){
	}
	public static TetrisLogger getLogger(){
		return tetrisLogger;
	}
	private void display(final String log){
		//try{
		out.println(log);
		out.flush();
		//}catch(final IOException ioException){
		//	ioException.printStackTrace();
		//}
	}

	public void log(Level level,final String message){
		Objects.requireNonNull(level);
		//Objects.requiresNonNull(thread);
		Objects.requireNonNull(message);
		String log=getTime()+":"+Thread.currentThread().getStackTrace()[2]+":"+level.getName()+" : "+message;
		display(log);
	}
	public void logInfo(final String message){
		Objects.requireNonNull(message);
		String log=getTime()+":"+Thread.currentThread().getStackTrace()[2]+":"+Level.INFO.getName()+":"+message;
		display(log);
	}
	public void logSevere(final String message){
		Objects.requireNonNull(message);
		String log=getTime()+":"+Thread.currentThread().getStackTrace()[2]+":"+Level.SEVERE.getName()+": "+message;
		display(log);
	}
	private static String getTime(){
		return dateFormat.format(new Date());
	}
	public void close(){
		//try{
		out.close();
		//}catch(final IOException exception){
		//exception.printStackTrace();
		//}
	}
	private static void cleanUp(final File file)throws IOException{
		if(file.isDirectory())
			for(final File f: file.listFiles())
				cleanUp(f);
		final String fileName=file.getName();
		final int index=fileName.lastIndexOf('.');
		if(index!=-1 && !fileName.equals(logFile.getName()) && fileName.substring(index).equals(extension))
			if(!file.delete())
				throw new FileNotFoundException("Failed to delete file  : "+file);
	}
}
