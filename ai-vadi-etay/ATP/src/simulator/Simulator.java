package simulator;

import graph.searchAlgorithms.Dijkstra;
import graph.searchGraph.Edge;
import graph.searchGraph.Graph;
import graph.searchGraph.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import tools.ATPLogger;
import agents.Agent;
import agents.HumanAgent;
import agents.SimpleGreedyAgent;
import agents.SpeedNutAutomationAgent;



public class Simulator {
	public final double TSWITCH = 0.12; 
	private Map<Integer,Vertex> _vertexes;
	private Vector<Road> _edges;
	private Map<Agent,Chart> _agents;
	private Map<Agent,Chart> _finishedAgents;
	
	public Simulator(Map<Integer,Vertex> vertexes, Map<Agent, Chart> agents, Vector<Road> edges) {
		super();
		_vertexes = vertexes;
		_agents = agents;
		_edges = edges;
		_finishedAgents = new HashMap<Agent,Chart>();
	}
		
	public Simulator(Map<Integer,Vertex> vertexes, Vector<Road> edges ) {
		super();
		_vertexes = vertexes;
		_edges = edges;
		_agents = new HashMap<Agent, Chart>();
		_finishedAgents = new HashMap<Agent,Chart>();
	}
	
	public Simulator() {
		super();
		_vertexes = new HashMap<Integer,Vertex>();
		_edges = new Vector<Road>();
		_agents = new HashMap<Agent, Chart>();
		_finishedAgents = new HashMap<Agent,Chart>();
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
	

	public Map<Agent, Chart> get_finishedAgents() {
		return _finishedAgents;
	}

	public void get_finishedAgents(Map<Agent, Chart> f_agents) {
		_finishedAgents = f_agents;
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
	
	
	public void addEdge(int v1number,int v2number,int weight, Boolean flooded){
		Vertex v1 = addVertex(v1number);
		Vertex v2 = addVertex(v2number);
		Road e_v1v2 =  new Road(v1, v2, weight, flooded);
		Road e_v2v1 =  new Road(v2, v1, weight, flooded);
		addEdge(e_v1v2);
		addEdge(e_v2v1);
		v1.addNeighbour(v2, e_v1v2);
		v2.addNeighbour(v1, e_v2v1);
	}
	
	
	@Override
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
	
	public void updateAgentScore(Agent agent,Vertex old_vertex, Vertex new_vertex){
		double turnTime = 0;
		Chart chart  = get_agents().get(agent);
		
		//the agent succeed and made the move
		//if (agent.get_vertex()==new_vertex){
			chart.incrementAction();
		//}
		//i'm not sure this is possible to get to this situation
		/*
		if (agent.get_vertex()==old_vertex){
			ATPLogger.log("agent tried to move from given vertex to it's self!");
			//return;
		}
		*/
		if (!old_vertex.get_neighbours().containsKey(new_vertex)){
			ATPLogger.log("agent tried to move from vertex to ileagal vertex!");
		}
		
		//Calculating turnTime in flooded road
		else if(old_vertex.get_neighbours().get(agent.get_vertex()).is_flooded()){
				turnTime +=	(old_vertex.get_neighbours().get(agent.get_vertex()).get_weight())/
							(agent.get_car().get_speed()*agent.get_car().get_coff());
		}
		
		//Calculating turnTime in clear road
		else{
			turnTime +=	(double)(old_vertex.get_neighbours().get(agent.get_vertex()).get_weight())/
						(agent.get_car().get_speed());
		}	
		ATPLogger.log("Agent "+agent.get_name()+" spent "+turnTime+" time moving to vertex #"+new_vertex.get_number());
		chart.addTime(turnTime);	
	}

	public void addCar(int vertexNum,String carName, int carSpeed, double carCoff) {
		get_vertexes().get(vertexNum).addCar(carName ,carSpeed , carCoff);
	}
	
	public void addAgent(Agent agent){
		get_agents().put(agent, new Chart());
	}
	
	public void agentSwitchCar(Agent agent,Car old_car,String new_car_name)
	{
		Car new_car = agent.get_vertex().get_cars().get(new_car_name);
				
		if (new_car!=null){
			if(new_car.equals(old_car)){
				return;			
			}

			//remove the new car from the vertex
			agent.get_vertex().get_cars().remove(new_car_name);
			//give the new car to  the agent
			agent.set_car(new_car);
			
			//add the old car to the vertex car list
			agent.get_vertex().get_cars().put(old_car.get_name(),old_car);
			
			ATPLogger.log("agent "+agent.get_name()+" switched car to: coff"+
								agent.get_car().get_coff()+", speed "+agent.get_car().get_speed()+".");
		}
		else{
			ATPLogger.log("agent "+agent.get_name()+" failed to switche cars!\n");
		}
		//updae the agent's chart with "switch cars time"
		get_agents().get(agent).addTime(TSWITCH);
		ATPLogger.log("Agent "+agent.get_name()+" spent "+TSWITCH+" time switching cars.");
	}
	
	public boolean moveAgent(Agent agent, Vertex new_vertex)
	{	
		Vertex old_vertex = agent.get_vertex();
		//check if destination vertex is a neighbor of agent vertex
		if (!agent.get_vertex().get_neighbours().containsKey(new_vertex)){
			updateAgentScore(agent,old_vertex,new_vertex);
			
			ATPLogger.log("Agent tried to move from vertex "+old_vertex.get_number()
								+" to not-neighbour vertex "+new_vertex.get_number()+"!");
			return false;
		}
		
		//check if the road is flooded and agent have a amphibious  car
		if (agent.get_vertex().get_neighbours().get(new_vertex).is_flooded() && 
				agent.get_car().get_coff()<=0){
			
			updateAgentScore(agent,old_vertex,new_vertex);
			
			ATPLogger.log("Agent tried to move on flooded road, from vertex "+
								old_vertex.get_number()+" to vertex "+new_vertex.get_number()+".");
			return false;
		}
		
		//make the move
		ATPLogger.log("Agent moved from vertex "+
				old_vertex.get_number()+" to vertex "+new_vertex.get_number()+".");
		agent.set_vertex(new_vertex);
		updateAgentScore(agent,old_vertex,new_vertex);
		return true;
	}


	public boolean finished() {
		boolean answer = true;
		Vector<Agent> toBeRemoved = new Vector<Agent>(); 
		if(get_agents().size()==0){
			return true;
		}		
		for (Agent agent: get_agents().keySet()){
			if(agent.get_state("stuck")!=null){
				get_finishedAgents().put(agent, get_agents().get(agent));
				//get_agents().remove(agent);
				toBeRemoved.add(agent);
				ATPLogger.log("Agent "+agent.get_name()+" is stuck! "+agent.get_state("stuck"));
			}
			else if (!agent.get_vertex().equals(agent.get_goalPosition())){
				answer = false;
			}	
			else{
				get_finishedAgents().put(agent, get_agents().get(agent));
				//get_agents().remove(agent);
				toBeRemoved.add(agent);
				ATPLogger.log("Agent "+agent.get_name()+" Finished!");
				//ATPLogger.log("get_agents().keySet(): "+get_agents().keySet());
			}			
		}
		for(Agent agent:toBeRemoved){
			get_agents().remove(agent);
		}
		if(answer){
			ATPLogger.log("All agents Finished!");
		}
		return answer;
	}


	public void startSimulation() {
		int round=1;
		ATPLogger.log("Starting Simulation:");
		
		for (Agent agent: get_agents().keySet()){
			ATPLogger.log("Agent: "+agent.get_name()+", start node: "+agent.get_initPosition().get_number()+", goal node: "+agent.get_goalPosition().get_number());
			agent.search(this);
		}
				
		while (!finished()){
			ATPLogger.log("\n<<<<<<<< Start of Round #"+(round++)+" >>>>>>>>");
			for (Agent agent: get_agents().keySet()){
				ATPLogger.log("\n"+agent.get_name()+"'s turn:");
				agent.chooseBestAction(this);
				Action a = agent.getAction();
				if(a!=null)	// currently null when SpeedNut gets 3 time to the same vertex
					a.performAction(this);
				ATPLogger.log(get_agents().get(agent).toString());
			}			
		}
		ATPLogger.log("\nFinal Stats:");
		for (Agent agent: get_finishedAgents().keySet()){
			ATPLogger.log("Agent: "+agent.get_name()+": "+get_finishedAgents().get(agent));	
		}
		
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
	
	public Graph getGraph(int fromVertex, int toVertex) {
		return null;
	}
	
	public double calcPath(int fromVertex, int toVertex,ArrayList<Node> result){
		Graph _graph = getGraph(true);
		Node from = _graph.get_node_by_ID(fromVertex);
		Node to = _graph.get_node_by_ID(toVertex);
		return Dijkstra.findShortestPath(_graph,from, to, result);		
	}
	
	
	public Graph getGraph(boolean clearOnly){
		Node[] nodes = new Node[this._vertexes.size()];
		//int i=0;
		for(Integer v:_vertexes.keySet()){
			int num = v.intValue();
			nodes[num]=new Node(num);			
		}		
		
		Vector<Edge> edgesVector = new Vector<Edge>(); 
		//Edge[] edges = new Edge[this._edges.size()];		
		for(Road e:_edges){
			if(e.is_flooded()&&clearOnly){
				continue;
			}
			int from = e.get_from().get_number();
			int target = e.get_to().get_number();						
			edgesVector.add(new Edge(nodes[from],nodes[target],e.get_weight()));
		}
		
		Edge[] edges = new Edge[edgesVector.size()];
		for(int k=0;k<edgesVector.size();k++){
			edges[k] = edgesVector.get(k);			
		}
		
		return new Graph(nodes,edges);	
	}
	
	public Graph getDijkstraHuristicGraph(Car car){
		Node[] nodes = new Node[this._vertexes.size()];
		int i=0;
		for(Integer v:_vertexes.keySet()){
			nodes[i++]=new Node(v.intValue());			
		}		
		
		Edge[] edges = new Edge[this._edges.size()];
		i=0;
		for(Road e:_edges){
			int from = e.get_from().get_number();
			int target = e.get_to().get_number();
			
			edges[i++] = new Edge(nodes[from],nodes[target],e.get_weight());			
		}		
		return new Graph(nodes,edges);	
	}

	public void addAgent(int typeAsNum,String agentName,int fromVertex, int toVertex) throws IOException {		
		while (fromVertex==toVertex){
			toVertex = (int)(this._vertexes.size()*Math.random())+1;
		}
		// what if car is null?
		Car car;						

		if(typeAsNum==1){
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
			
			addAgent(new HumanAgent(agentName, 
					getVertex(fromVertex),
					getVertex(toVertex),
					car));
			removeCarOfVertex(fromVertex, car.get_name());						
		}
		else if(typeAsNum==2) {		
			car = getFirstCarOfVertex(fromVertex);
			addAgent(new SpeedNutAutomationAgent(agentName, 
					getVertex(fromVertex),
					getVertex(toVertex),
					car));
			removeCarOfVertex(fromVertex, car.get_name());
		}
		else if(typeAsNum==3) {
			car = getFirstCarOfVertex(fromVertex);
			addAgent(new SimpleGreedyAgent(agentName, 
					getVertex(fromVertex),
					getVertex(toVertex),
					car));
			removeCarOfVertex(fromVertex, car.get_name());
		}
		else return;				
	}	
	
}
