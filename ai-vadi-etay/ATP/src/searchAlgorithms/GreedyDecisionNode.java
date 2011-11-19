package searchAlgorithms;

import java.util.ArrayList;
import java.util.Vector;

import graph.searchAlgorithms.Dijkstra;
import graph.searchGraph.Edge;
import graph.searchGraph.Graph;
import graph.searchGraph.Node;
import simulator.Car;
import simulator.Road;
import simulator.Simulator;
import simulator.Vertex;
import simulator.atpProblem;

public class GreedyDecisionNode extends AtpDecisionNode {


	
	public GreedyDecisionNode(Vertex vertex, Car car, Road road,
			AtpDecisionNode parent) {
		super(vertex, car, road, parent);
		// TODO Auto-generated constructor stub
	}

	public int compareTo(AtpDecisionNode o) {
		if (get_H()>o.get_H()) return 1;
		if (get_H()<o.get_H()) return -1;
		return 0;
	}
	
	
	@Override
	public void expand(Problem problem){
		_children = new Vector<AtpDecisionNode>();
		for(Vertex v : _vertex.get_neighbours().keySet()){
			_children.add(new GreedyDecisionNode(v, _car, _vertex.get_neighbours().get(v), this));
		}
		for (Car c : _vertex.get_cars().values()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				GreedyDecisionNode newNode = new GreedyDecisionNode(v, c, _vertex.get_neighbours().get(v), this);
				newNode._H = clacHuristic(c,((atpProblem) problem).get_sim(), 
											((atpProblem)problem).get_current(), 
											((atpProblem)problem).get_goal());
				_children.add(newNode);
			}
		}	
	}
	private double clacHuristic(Car c , Simulator sim, Vertex vFrom, Vertex vTo) {
		Graph g = getDijkstraHuristicGraph(c,sim);
		ArrayList<Node> result = new ArrayList<Node>();
		Node from = g.get_node_by_ID(vFrom.get_number());
		Node to = g.get_node_by_ID(vTo.get_number());
		_H = Dijkstra.findShortestPath(g,from, to, result );
		return 0;
	}

	public Graph getDijkstraHuristicGraph(Car car,Simulator sim){
		Node[] nodes = new Node[sim.get_vertexes().size()];
		int i=0;
		for(Integer v:sim.get_vertexes().keySet()){
			nodes[i++]=new Node(v.intValue());			
		}		
		
		Vector <Edge> edges = new Vector<Edge>();
		i=0;
		for(Road e:sim.get_edges()){
			int from = e.get_from().get_number();
			int target = e.get_to().get_number();
			Double weight = calcWeight(e,car);
			if (weight!=null){
				edges.add(new Edge(nodes[from],nodes[target],weight));
			}
		}		
		return new Graph(nodes,(Edge[])edges.toArray());	
	}
	
	private Double calcWeight(Road e, Car car) {
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
		
		return (weight/coff);
	}
	
}
