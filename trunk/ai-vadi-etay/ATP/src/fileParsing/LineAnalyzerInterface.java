package fileParsing;

/**
 * Interface to parse line from  file
 * @author Vadi
 *
 */
public interface LineAnalyzerInterface {

	void parseLine(String strLine);

	Object getParsedObject();

}
