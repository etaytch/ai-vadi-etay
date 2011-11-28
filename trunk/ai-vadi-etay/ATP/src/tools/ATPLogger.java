package tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


  public class ATPLogger {
	  
	  public static void log(String msg) {
		  Logger logger;
	      LogManager lm = LogManager.getLogManager();	      	  	
	      logger = Logger.getLogger("ATPLogger");	
	      lm.addLogger(logger);
	      logger.setLevel(Level.INFO);	      
		  Date dNow = new Date();
	      SimpleDateFormat ft = new SimpleDateFormat ("dd_mm_yyyy_hhmmss");
		  FileHandler fh;

		try {
			fh = new FileHandler("Log/log_"+ft.format(dNow)+".log",true);			
			fh.setFormatter(new Formatter() {
		          public String format(LogRecord record) {
		    		  Date dNow = new Date();
		    	      SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss	");
		        	  return ft.format(dNow)+record.getMessage() + "\r\n";
		          }
		    });
			logger.addHandler(fh);
			System.out.println(msg);
			
			//logger.info(msg);
			fh.close();
		}
		catch (Exception e) { e.printStackTrace(); }
			
	  }
  }