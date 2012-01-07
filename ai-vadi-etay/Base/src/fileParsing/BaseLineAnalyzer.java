package fileParsing;

import java.util.ArrayList;
import java.util.List;

import base.BaseNet;


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

	/*
	 * N	name	type(norm/nois)		perent_1...perent_n 
	 * L	name	label1		label2
	 * D  	name    distribution  parent_label_1...parent_label_n 
	 */
	

	@Override
	public void parseLine(String strLine) {
		String[] line  = strLine.split("\\s+");
		
		if (line.length==0 || line[0].isEmpty()){
			return; 
		}
		if (line[0].charAt(0)==(';')){
			return;
		}
		if (line[0].equalsIgnoreCase("N")){
			parseNode(line);
		}
		if (line[0].equalsIgnoreCase("L")){
			parseLables(line);
		}
		if (line[0].equalsIgnoreCase("D")){
			parseDist(line);
		}
		
		
		
		return;	
	}

	private void parseNode(String[] line) {
		List<String> parents = new ArrayList<String>();
		_bn.addNode(line[1],line[2]);
		
		for(int i=3; i<line.length; i++){
			parents.add(line[i]);
		}
		
		_bn.setParents(line[1],parents);
	}

	private void parseLables(String[] line) {
		List<String> labels = new ArrayList<String>();
		
		for(int i=2; i<line.length; i++){
			labels .add(line[i]);
		}
		
		_bn.setLables(line[1],labels);
	}

	
	private void parseDist(String[] line) {
		List<String> pdist = new ArrayList<String>();
		for(int i=3; i<line.length; i++){
			pdist.add(line[i]);
		}
		_bn.addNodeDistTableRow(line[1],pdist,Double.parseDouble(line[2]));
		
	}

	@Override
	public Object getParsedObject() {
		return _bn;
	}

}
