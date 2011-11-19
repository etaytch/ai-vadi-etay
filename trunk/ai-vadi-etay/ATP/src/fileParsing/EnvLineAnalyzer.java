package fileParsing;

import agents.GreedyAgent;
import agents.SimpleGreedyAgent;
import agents.HumanAgent;
import agents.SpeedNutAutomationAgent;
import simulator.Simulator;

public class EnvLineAnalyzer implements LineAnalyzerInterface {

	private Simulator env;
	
	public EnvLineAnalyzer() {
		super();
		env = new Simulator();
	}

	@Override
	public void parseLine(String strLine) {
		String[] line  = strLine.split("\\s+");
		
		if (line.length==0 || line[0].isEmpty()){
			return; 
		}
		if (line[0].charAt(0)==(';')){
			return;
		}
		if (line[0].equalsIgnoreCase("E")){
			parseEdge(line);
		}
		if (line[0].equalsIgnoreCase("V")){
			parseCar(line);
		}
		if (line[0].equalsIgnoreCase("A")){
			parseAgent(line);
		}
		
		return;	
	}

	private void parseCar(String[] line) {
		env.addVertex(Integer.parseInt(line[1]));
		env.addCar(Integer.parseInt(line[1]) ,line[2] ,Integer.parseInt(line[3]),
					Double.parseDouble(line[4]));
	}

	private void parseAgent(String[] line) {
		
		switch (Integer.parseInt(line[1])) {
		case 1:
			env.addAgent(new HumanAgent(line[2], 
					env.getVertex(Integer.parseInt(line[3])),
					env.getVertex(Integer.parseInt(line[4])),
					env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 2:
			env.addAgent(new SpeedNutAutomationAgent(line[2], 
					env.getVertex(Integer.parseInt(line[3])),
					env.getVertex(Integer.parseInt(line[4])),
					env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
			
		case 3:
			env.addAgent(new SimpleGreedyAgent(line[2], 
					env.getVertex(Integer.parseInt(line[3])),
					env.getVertex(Integer.parseInt(line[4])),
					env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 4:
			env.addAgent(new GreedyAgent(line[2], 
					env.getVertex(Integer.parseInt(line[3])),
					env.getVertex(Integer.parseInt(line[4])),
					env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
			
		default:
			break;
		}
	}
	
	private void parseEdge(String[] line) {
		env.addEdge(Integer.parseInt(line[1]), Integer.parseInt(line[2]),
					Integer.parseInt(line[3]), isFlooded(line[4]));
	}

	private Boolean isFlooded(String flooded) {
		 if (flooded.equalsIgnoreCase("F")) return true;
		 if (flooded.equalsIgnoreCase("C")) return false;
		 return null;
	}

	@Override
	public Object getParsedObject() {
		return env;
	}

}
