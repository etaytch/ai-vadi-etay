package fileParsing;


/**
 * Line parser for the ATP problem input file
 * @author Vadi
 *
 */
public class BaseLineAnalyzer implements LineAnalyzerInterface {

	private BaseNet _bn;
	
	public BaseLineAnalyzer (BaseNet bn) {
		super();
		_bn = bn;
	}

	@Override
	public void parseLine(String strLine) {
		String[] line  = strLine.split("\\s+");
	
		
		
		return;	
	}

	@Override
	public Object getParsedObject() {
		return _bn;
	}

}
