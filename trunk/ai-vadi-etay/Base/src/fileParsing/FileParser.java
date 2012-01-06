package fileParsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * 
 * this class responsible to parse the  user input file line by line.
 *
 */
public class FileParser {

	/**
	 * the main parsing method
	 * @param filename
	 * @param la
	 * @return
	 */
	public static Object parseEnv(String filename,LineAnalyzerInterface la){
		try{
			  FileInputStream fstream = new FileInputStream(filename);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				  la.parseLine(strLine);
			  }
			  //Close the input stream
			  in.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		return la.getParsedObject();
	}
}
