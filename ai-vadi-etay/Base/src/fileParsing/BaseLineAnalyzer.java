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

	private BaseNet bn;
	
	public BaseLineAnalyzer (BaseNet bn) {
		super();
		this.bn = bn;
	}

	/*
	 * N	name	type(norm/nois)		perent_1...perent_n 
	 * L	name	label1		label2
	 * D  	name    parent_label_1...parent_label_n  dist1... distN 
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
		if (line[0].equalsIgnoreCase("BL")){
			parseBackgroundLabels(line);
		}
		if (line[0].equalsIgnoreCase("B")){
			parseBackground(line);
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
		if (line[0].equalsIgnoreCase("Q")){
			parseQuery(line);
		}
		if (line[0].equalsIgnoreCase("E")){
			parseEvidence(line);
		}
		return;	
	}

	private void parseBackground(String[] line) {
		List<String> pdist = new ArrayList<String>();
		List<Double> ndist = new ArrayList<Double>();
		
		bn.addNode(line[1],"norm");
		List<String> labels = bn.getBackgrounds().get(line[1]);
		bn.setLables(line[1], labels);
		
		for(int i=0;i<labels.size();i++){
			if(labels.get(i).equals(line[2])){
				ndist.add(1.0);
			}
			else{
				ndist.add(0.0);
			}			
		}
		bn.addNodeDistTableRow(line[1],pdist,ndist);
	}

	private void parseBackgroundLabels(String[] line) {
		
		/*List<String> labels = new ArrayList<String>();
		
		for(int i=2; i<line.length; i++){
			labels.add(line[i]);
		}
		
		bn.addBackground(line[1],labels);
		*/
		
		List<String> pdist = new ArrayList<String>();
		List<Double> ndist = new ArrayList<Double>();
		
		bn.addNode(line[1],"norm");
		//List<String> labels = bn.getBackgrounds().get(line[1]);
		List<String> labels = new ArrayList<String>();
		for(int i=2; i<line.length; i++){
			labels.add(line[i]);
		}
		
		bn.setLables(line[1], labels);
		
		for(int i=0;i<labels.size();i++){
			if(labels.get(i).equals(line[2])){
				ndist.add(0.5);
			}
			else{
				ndist.add(0.5);
			}			
		}
		bn.addNodeDistTableRow(line[1],pdist,ndist);
	}

	private void parseEvidence(String[] line) {		
		bn.addEvidance(line[1],line[2]);
	}

	private void parseQuery(String[] line) {
		bn.addQuery(line[1]);
	}
	
	
	private void parseNode(String[] line) {
		List<String> parents = new ArrayList<String>();
		bn.addNode(line[1],line[2]);
		
		for(int i=3; i<line.length; i++){
			parents.add(line[i]);
		}
		
		bn.setParents(line[1],parents);
	}

	private void parseLables(String[] line) {
		List<String> labels = new ArrayList<String>();
		
		for(int i=2; i<line.length; i++){
			labels .add(line[i]);
		}
		
		bn.setLables(line[1],labels);
	}

	
	private void parseDist(String[] line) {
		List<String> pdist = new ArrayList<String>();
		List<Double> ndist = new ArrayList<Double>();
		int parents = bn.getParentsCount(line[1]);
		int lables = bn.getLablesCount(line[1]);
		
		int i=2;
		for(; i<parents+2; i++){
			pdist.add(line[i]);
		}
		
		for(; i<parents+lables+2; i++){
			ndist.add(Double.parseDouble(line[i]));
		}

		bn.addNodeDistTableRow(line[1],pdist,ndist);
		
		
		
	}

	@Override
	public Object getParsedObject() {
		return bn;
	}

}
