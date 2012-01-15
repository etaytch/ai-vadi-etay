package fileParsing;


import agents.PropabilityAgent;
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
	 * 7 = A* with speed nut automaton presence 
	 * @param line
	 */
	private void parseAgent(String[] line) {
		
		
		_env.addAgent(new PropabilityAgent(line[1], 
		_env.getVertex(Integer.parseInt(line[2])),
		_env.getVertex(Integer.parseInt(line[3])),
		_env.getCarOfVertex(Integer.parseInt(line[2]), (line[4]))));
		_env.removeCarOfVertex(Integer.parseInt(line[2]),line[4]);
	
	}
	
	private void parseEdge(String[] line) {
		_env.addEdge(Integer.parseInt(line[1]), Integer.parseInt(line[2]),
					Integer.parseInt(line[3]), Double.parseDouble(line[4]));
	}

	@Override
	public Object getParsedObject() {
		return _sim;
	}

}
