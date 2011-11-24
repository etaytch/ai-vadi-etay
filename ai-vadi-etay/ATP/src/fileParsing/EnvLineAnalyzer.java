package fileParsing;

import agents.AstarAgent;
import agents.GreedyAgent;
import agents.RealTimeAstarAgent;
import agents.SimpleGreedyAgent;
import agents.HumanAgent;
import agents.SpeedNutAutomationAgent;
import simulator.Enviornment;
import simulator.Simulator;

/**
 * Line parser for the ATP problem input file
 * @author Vadi
 *
 */
public class EnvLineAnalyzer implements LineAnalyzerInterface {

	private Simulator _sim;
	private Enviornment _env;
	
	public EnvLineAnalyzer() {
		super();
		_env = new Enviornment();
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
		
		return;	
	}

	private void parseCar(String[] line) {
		_env.addVertex(Integer.parseInt(line[1]));
		_env.addCar(Integer.parseInt(line[1]) ,line[2] ,Integer.parseInt(line[3]),
					Double.parseDouble(line[4]));
	}

	
	/**
	 * parse agents:
	 * 1 = Human
	 * 2 = Speed nut
	 * 3 = Simple Greedy
	 * 4 = Greedy (With huristic) 
	 * 5 = A* 
	 * 6 = RT A*
	 * 
	 * @param line
	 */
	private void parseAgent(String[] line) {
		
		switch (Integer.parseInt(line[1])) {
		case 1:
			_env.addAgent(new HumanAgent(line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 2:
			_env.addAgent(new SpeedNutAutomationAgent(line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
			
		case 3:
			_env.addAgent(new SimpleGreedyAgent(line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 4:
			_env.addAgent(new GreedyAgent(line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 5:
			_env.addAgent(new AstarAgent(line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
			_env.removeCarOfVertex(Integer.parseInt(line[3]),line[5]);
			break;
		case 6:
			_env.addAgent(new RealTimeAstarAgent(line[2], 
					_env.getVertex(Integer.parseInt(line[3])),
					_env.getVertex(Integer.parseInt(line[4])),
					_env.getCarOfVertex(Integer.parseInt(line[3]), (line[5]))));
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
