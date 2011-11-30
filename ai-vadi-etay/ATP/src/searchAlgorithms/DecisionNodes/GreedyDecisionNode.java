package searchAlgorithms.DecisionNodes;

import java.util.ArrayList;
import java.util.Vector;

import generalAlgorithms.Dijkstra;
import generalAlgorithms.Edge;
import generalAlgorithms.Graph;
import generalAlgorithms.Node;
import searchAlgorithms.AtpProblem;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Defs;
import simulator.Environment;
import simulator.Road;
import simulator.Vertex;

/**
 * 
 * Greedy decision node
 *
 */
public class GreedyDecisionNode extends AtpDecisionNode {

	

	public GreedyDecisionNode(Vertex vertex, Car car, Road road,
			AtpDecisionNode parent, int nestingLevel) {
		super(vertex, car, road, parent, nestingLevel);
	}


	@Override
	public int compareTo(DecisionNode o) {
		if (get_H()>((GreedyDecisionNode)o).get_H()) return 1;	
		if (get_H()<((GreedyDecisionNode)o).get_H()) return -1;  
		return 0;
	}
	
	
	@Override
	public void expand(Problem problem){
		_children = new Vector<DecisionNode>();
		if (_nestingLevel==Defs.NESTING_LEVEL) return;		
		for(Vertex v : _vertex.get_neighbours().keySet()){
			if((_parent!=null)&&(_parent._vertex.equals(v))) continue;							// don't calc parent
			if (_vertex.get_neighbours().get(v).is_flooded() && _car.get_coff()==0) continue;	// don't calc flooded road with regular car
			GreedyDecisionNode newNode = new GreedyDecisionNode(v, _car, _vertex.get_neighbours().get(v),this, _nestingLevel++);
			newNode._H = clacHuristic(_car,((AtpProblem) problem).get_env(), 
										v, 
										((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
			_children.add(newNode);
		}
		for (Car c : _vertex.get_cars().values()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				if((_parent!=null)&&(_parent._vertex.equals(v))) continue;
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				GreedyDecisionNode newNode = new GreedyDecisionNode(v, c, _vertex.get_neighbours().get(v), this,_nestingLevel++);
				newNode._H = clacHuristic(c,((AtpProblem) problem).get_env(), 
											v, 
											((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
				_children.add(newNode);
			}
		}	
	}

	
	public double clacHuristic(Car c , Environment env, Vertex vFrom, Vertex vTo, Road road) {
		Graph g = getDijkstraHuristicGraph(constractBestCar(env),env);
		ArrayList<Node> result = new ArrayList<Node>();
		Node from = g.get_node_by_ID(vFrom.get_number());
		Node to = g.get_node_by_ID(vTo.get_number());
		double switchCarTime = 0.0;
		if((_parent!=null) && (!c.get_name().equals(_parent._car.get_name()))){
			switchCarTime = Defs.TSWITCH;
		}
		double ans = switchCarTime+(calcWeight(road, c))+ Dijkstra.findShortestPath(g,from, to, result );
		return ans;
	} 

	private Car constractBestCar(Environment env) {
		double bcoff = 0;
		int bspeed = 0;
		for(Car c: env.get_cars())
		{
			if (c.get_coff()>bcoff)
			{
				bcoff = c.get_coff();
			}
			if (c.get_speed()>bspeed)
			{
				bspeed =c.get_speed(); 
			}
		}		
		return new Car("superCar",bspeed,bcoff);
	}


	public double clacHuristic2(Car c , Environment env, Vertex vFrom, Vertex vTo, Road road) {
		Graph g = getDijkstraHuristicGraph(c,env);
		ArrayList<Node> result = new ArrayList<Node>();
		Node from = g.get_node_by_ID(vFrom.get_number());
		Node to = g.get_node_by_ID(vTo.get_number());
		double switchCarTime = 0.0;
		if((_parent!=null) && (!c.get_name().equals(_parent._car.get_name()))){
			switchCarTime = Defs.TSWITCH;
		}
		double ans = switchCarTime+(calcWeight(road, c))+ Dijkstra.findShortestPath(g,from, to, result );
		return ans;
	} 

	/**
	 * return Simple graph with calculated edges for the greedy agent heuristic calculation    
	 * @param car
	 * @param env
	 * @return
	 */
	public Graph getDijkstraHuristicGraph(Car car,Environment env){
		Node[] nodes = new Node[env.get_vertexes().size()];
		int i=0;
		for(Integer v:env.get_vertexes().keySet()){
			nodes[i++]=new Node(v.intValue());			
		}		
		
		Vector <Edge> edges = new Vector<Edge>();
		i=0;
		for(Road e:env.get_edges()){
			int from = e.get_from().get_number();
			int target = e.get_to().get_number();
			Double weight = calcWeight(e,car);
			if (weight!=null){
				edges.add(new Edge(nodes[from],nodes[target],weight));
			}
		}	
		Edge[] edgesArr = new Edge[edges.size()];
		for(int k=0;k<edges.size();k++){
			edgesArr[k] = edges.get(k);			
		}
		return new Graph(nodes,edgesArr);	
	}
	
	public Double calcWeight(Road e, Car car) {
		if(e==null){
			return 0.0;
		}
		double coff = car.get_coff();
		int speed = car.get_speed();
		double weight = e.get_weight();
		boolean flooded = e.is_flooded();
		
		if ((flooded) && (coff==0)){
			return null;  
		}
		if ((flooded) && (coff>0)){
			return (weight/speed*coff);
		}
		
		return (weight/speed);
	}
	
}
