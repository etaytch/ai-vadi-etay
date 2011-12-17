package fileParsing;

import agents.GTSAgent;
import agents.HumanAgent;

import simulator.Defs;
import simulator.Environment;
import simulator.Simulator;

/**
 * Line parser for the ATP problem input file
 * @author Vadi
 *
 */
public class EnvLineAnalyzer implements LineAnalyzerInterface {

	private Simulator _sim;
	private Environment _env;
	
	public EnvLineAnalyzer() {
		super();
		_env = new Environment();
		_sim = new Simulator(_env);
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
		if (line[0].equalsIgnoreCase("G")){
			parseGame(line);
		}
		return;	
	}

	private void parseGame(String[] line) {
		_env.set_typeGame(Integer.parseInt(line[1]));		
	}

	private void parseCar(String[] line) {
		_env.addVertex(Integer.parseInt(line[1]));
		_env.addCar(Integer.parseInt(line[1]) ,line[2] ,Integer.parseInt(line[3]),
					Double.parseDouble(line[4]));
	}

	
	/**
	 * parse agents:
	 * 1 = Human
	 * 2 = Game Search Tree
	 * @param line
	 */
	private void parseAgent(String[] line) {		
		switch (Integer.parseInt(line[1])) {
		case 1:
			_env.addAgent(new HumanAgent(_env.get_typeGame(),Defs.HUMAN,line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 2:
			_env.addAgent(new GTSAgent(_env.get_typeGame(),Defs.GTS,line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5])),_env));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
			
		default:
			break;
		}
	}
	
	private void parseEdge(String[] line) {
		_env.addEdge(Integer.parseInt(line[1]), Integer.parseInt(line[2]),
					Integer.parseInt(line[3]), isFlooded(line[4]));
	}

	private Boolean isFlooded(String flooded) {
		 if (flooded.equalsIgnoreCase("F")) return true;
		 if (flooded.equalsIgnoreCase("C")) return false;
		 return null;
	}

	@Override
	public Object getParsedObject() {
		return _sim;
	}

}
