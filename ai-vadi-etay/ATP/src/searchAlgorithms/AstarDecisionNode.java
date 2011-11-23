package searchAlgorithms;

import generalAlgorithms.Dijkstra;
import generalAlgorithms.Graph;
import generalAlgorithms.Node;
import java.util.ArrayList;
import simulator.Car;
import simulator.Defs;
import simulator.Enviornment;
import simulator.Road;
import simulator.Vertex;

public class AstarDecisionNode extends GreedyDecisionNode{

	private double _accWeight;


	public AstarDecisionNode(Vertex vertex, Car car, Road road,
			AtpDecisionNode parent,int nestingLevel) 
		{
		super(vertex, car, road, parent,nestingLevel);
		if(_parent!=null){
			_accWeight = ((AstarDecisionNode) _parent).get_accWeight()+calcLocalWeight(road,car);
		}
		else{
			_accWeight=0.0;
		}
	}


	private double calcLocalWeight(Road e, Car car){
		if (e == null){
			return 0.0;
		}
		double coff = car.get_coff();
		int speed = car.get_speed();
		double weight = e.get_weight();
		boolean flooded = e.is_flooded();
		
		//impossible situation
		if ((flooded) && (coff==0)){
			return Double.MAX_VALUE;  
		}
		if ((flooded) && (coff>0)){
			return (weight/speed*coff);
		}
		
		return (weight/coff);
	}

	public double get_accWeight() {
		return _accWeight;
	}


	public void set_accWeight(double accWeight) {
		_accWeight = accWeight;
	}
	
	public double clacHuristic(Car c , Enviornment env, Vertex vFrom, Vertex vTo) {
		Graph g = getDijkstraHuristicGraph(c,env);
		ArrayList<Node> result = new ArrayList<Node>();
		Node from = g.get_node_by_ID(vFrom.get_number());
		Node to = g.get_node_by_ID(vTo.get_number());
		double switchCarTime = 0.0;
		if((_parent!=null) && (!c.equals(_parent._car))){
			switchCarTime = Defs.TSWITCH;
		}
		return switchCarTime+Dijkstra.findShortestPath(g,from, to, result );
	}
	
	@Override
	public int compareTo(DecisionNode o) {
		if (get_H()+get_accWeight()>((AstarDecisionNode)o).get_H()+get_accWeight()) return 1;
		if (get_H()+get_accWeight()<((AstarDecisionNode)o).get_H()+get_accWeight()) return -1;
		return 0;
	}

}
