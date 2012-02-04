package simulator;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import tools.ATPLogger;
import agents.Agent;
import agents.PropabilityAgent;

/**
 * Data object of the  ATP world graph, and agents in this world
 * 
 */
public class Environment {
	private Map<Integer,Vertex> _vertexes;
	private Vector<Road> _edges;
	private Map<Agent,Chart> _agents;
	private Vector<Car> _cars;
	
	public Environment(Map<Integer,Vertex> vertexes, Map<Agent, Chart> agents, Vector<Road> edges) {
		super();
		_vertexes = vertexes;
		_agents = agents;
		_edges = edges;
	}
		
	public Environment(Map<Integer,Vertex> vertexes, Vector<Road> edges ) {
		super();
		_vertexes = vertexes;
		_edges = edges;
		_agents = new LinkedHashMap<Agent, Chart>();
	}
	
	public Environment() {
		super();
		_vertexes = new LinkedHashMap<Integer,Vertex>();
		_edges = new Vector<Road>();
		_agents = new LinkedHashMap<Agent, Chart>();
		_cars = new Vector<Car>();
	}
	
	public Vector<Road> get_edges() {
		return _edges;
	}

	public void set_edges(Vector<Road> edges) {
		_edges = edges;
	}
	
	public void addEdge(Road e) {
		_edges.add(e);
	}



	public Map<Integer,Vertex>  get_vertexes() {
		return _vertexes;
	}

	public void set_vertexes(Map<Integer,Vertex> vertexes) {
		_vertexes = vertexes;
	}

	public Map<Agent, Chart> get_agents() {
		return _agents;
	}
	


	public void set_agents(Map<Agent, Chart> agents) {
		_agents = agents;
	}

	//return the new added vertex
	public Vertex  addVertex(int vnumber){
		Vertex new_vertex = get_vertexes().get(vnumber);
		if (new_vertex==null){
			new_vertex = new Vertex(vnumber);
			get_vertexes().put(vnumber, new Vertex(vnumber));
		}

		return get_vertexes().get(vnumber);
	}
	
	public void addEdge(int v1number,int v2number,int weight, double floodedProb){
		Vertex v1 = addVertex(v1number);
		Vertex v2 = addVertex(v2number);
		
		Boolean flooded = false; 
		if (Math.random()<= floodedProb){
			flooded = true;
		}
		
		Road e_v1v2 =  new Road(v1, v2, weight, flooded,floodedProb);
		Road e_v2v1 =  new Road(v2, v1, weight, flooded,floodedProb);
		
		if (flooded)
		{
			System.out.println("//////////////////////");
			System.out.println(e_v1v2+" is flooded!!!");
			System.out.println("//////////////////////");
		}
		
		addEdge(e_v1v2);
		addEdge(e_v2v1);
		v1.addNeighbour(v2, e_v1v2);
		v2.addNeighbour(v1, e_v2v1);
	}
	
	public String toString() {
		return "Environment:\n############\n\nAgents\n------" + agentsToString() +
		"\n\n**********************************************\n"+
		"\nVertexes\n--------" + vertexesToString()+
		"\n\n**********************************************\n"+
		"\nEdges\n-----" + edgesToString()+"\n";
	}
	

	private String vertexesToString() {
		String ret = "";
		for(int v: _vertexes.keySet()){
			ret = ret.concat("\n");
			ret = ret.concat(_vertexes.get(v).toString());
		}
		
		return ret;
	}
	
	private String edgesToString() {
		String ret = "";
		for(Road e: _edges){
			ret = ret.concat("\n");
			ret = ret.concat(e.toString());
		}
		
		return ret;
	}
	
	private String agentsToString() {
		String ret = "";
		for(Agent a: _agents.keySet()){
			ret = ret.concat("\n");
			ret = ret.concat(a.toString());
			ret = ret.concat("\n\t");
			ret = ret.concat(_agents.get(a).toString());
				
		}
		
		return ret;
	}
	
	public void addCar(int vertexNum,String carName, int carSpeed, double carCoff) {
		Car tmp = new Car(carName ,carSpeed , carCoff);
		get_vertexes().get(vertexNum).addCar(tmp);
		_cars.add(tmp);
	}
	
	public void addAgent(Agent agent){
		get_agents().put(agent, new Chart());
	}
	public Vertex getVertex(int newVertexNumber) {
		return get_vertexes().get(newVertexNumber);
		
	}
	
	public Car getFirstCarOfVertex(int newVertexNumber){
		Set<String> carNames = get_vertexes().get(newVertexNumber).get_cars().keySet();
		String str;
		if((str = carNames.iterator().next()) != null){
			return get_vertexes().get(newVertexNumber).get_cars().get(str);	
		}		
		return null;
	}
	
	public Car getCarOfVertex(int newVertexNumber,String carName) {		
		return get_vertexes().get(newVertexNumber).get_cars().get(carName);		
	}
	public void removeCarOfVertex(int newVertexNumber,String carName) {
		get_vertexes().get(newVertexNumber).get_cars().remove(carName);
		
	}		
	
	public void addAgent(String agentName,int fromVertex, int toVertex) throws IOException {		

		Car car;						

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		ATPLogger.log("Please select a car:");
		Set<String> carNames = get_vertexes().get(fromVertex).get_cars().keySet();
		String selectedCar;
		int i=1;
		if((selectedCar = carNames.iterator().next()) != null){
			ATPLogger.log("	Car #"+i+": "+selectedCar);
		}	
		ATPLogger.log("Enter car's name:");
		selectedCar = br.readLine();
		car = getCarOfVertex(fromVertex, selectedCar);
		
		addAgent(new PropabilityAgent(agentName, 
				getVertex(fromVertex),
				getVertex(toVertex),
				car));
		removeCarOfVertex(fromVertex, car.get_name());
		
		}


	public Agent getAgentByName(String name) {
		for(Agent a:get_agents().keySet()){
			if (a.get_name().equals(name)){
				return a;
			}
		}
		return null;
	}

	public void set_cars(Vector<Car> _cars) {
		this._cars = _cars;
	}

	public Vector<Car> get_cars() {
		return _cars;
	}	

	public Agent getFirstAgent(){
		return (Agent)(this._agents.keySet().toArray())[0];
		
	}
	
	public String getOtherCar(Car car){
		for(Car c : _cars){
			if(!c.equals(car)){
				return c.get_name();
			}			
		}		
		return null;
	}
	
}
