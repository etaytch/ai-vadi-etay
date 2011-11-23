package agents;

import generalAlgorithms.Dijkstra;
import generalAlgorithms.Edge;
import generalAlgorithms.Graph;
import generalAlgorithms.Node;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import searchAlgorithms.AtpDecisionNode;
import simulator.Car;
import simulator.Enviornment;
import simulator.MoveAction;
import simulator.Road;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import tools.ATPLogger;

public class SimpleGreedyAgent extends Agent {	

	public SimpleGreedyAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);	
		
	}

	@Override
	public void chooseBestAction(Enviornment env) {				
		ArrayList<Node> result = new ArrayList<Node>();
		calcPath(get_vertex().get_number(),get_goalPosition().get_number(),result,env);		
		//ATPLogger.log("Dijkstra result from "+get_vertex().get_number()+" to "+get_goalPosition().get_number()+":\n"+result);		
		if(result.isEmpty()){
			set_state("stuck","Could not find a path to goal!");
			return;			
		}
		else{
			int nextVerNumber = result.get(1).getId();	// result.get(0) = from_node
			
			Set<Vertex> vertexs = get_vertex().get_neighbours().keySet();
			Vertex nextVer=null;			
			for(Vertex v:vertexs){
				if(v.get_number() == nextVerNumber){
					nextVer = v;
					break;
				}
			}		
			String maxCarName = this.get_car().get_name();
			if (get_vertex().get_cars().isEmpty()) {
				ATPLogger.log("Vertex has no cars!.. "+get_vertex()+"Keeping current car: "+maxCarName);						
			}
			else{
				Map<String,Car> cars = get_vertex().get_cars();
				Set<String> carsNames = cars.keySet();
				int maxSpeed = get_car().get_speed();
				
				for(String cName:carsNames){
					Car c = cars.get(cName);
					if(c.get_speed()>maxSpeed){
						maxSpeed = c.get_speed();
						maxCarName = cName;
					}						
				}			
			}
			if(maxCarName.equals(get_car().get_name())){				
				ATPLogger.log("Agent "+get_name()+" chose to keep original car. "+get_car());
				get_actions().offer(new MoveAction(this, nextVer));
			}
			else{
				get_actions().offer(new SwitchCarAndMoveAction(this, maxCarName, nextVer));
			}
		}			
	}
	@Override
	public AtpDecisionNode getInitNode() {
		return null;
	}
	
	public Graph getGraph(boolean clearOnly,Enviornment env){
		Node[] nodes = new Node[env.get_vertexes().size()];
		//int i=0;
		for(Integer v:env.get_vertexes().keySet()){
			int num = v.intValue();
			nodes[num]=new Node(num);			
		}		
		
		Vector<Edge> edgesVector = new Vector<Edge>(); 
		//Edge[] edges = new Edge[this._edges.size()];		
		for(Road e:env.get_edges()){
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
	
	public double calcPath(int fromVertex, int toVertex,ArrayList<Node> result, Enviornment env){
		Graph _graph = getGraph(true,env);
		Node from = _graph.get_node_by_ID(fromVertex);
		Node to = _graph.get_node_by_ID(toVertex);
		return Dijkstra.findShortestPath(_graph,from, to, result);		
	}
}
