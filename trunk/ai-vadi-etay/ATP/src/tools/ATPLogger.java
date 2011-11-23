package tools;

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
	      logger.setLevel(Level.ALL);	      
		  FileHandler fh;
		  
		try {
			fh = new FileHandler("logger.log");
			fh.setFormatter(new Formatter() {
		          public String format(LogRecord record) {	              	        	  
		        	  return record.getMessage() + "\n";
		          }
		    });
			logger.addHandler(fh);
			System.out.println(msg);
			
			logger.info(msg);
			fh.close();
		}
		catch (Exception e) { e.printStackTrace(); }
			
	  }
  }