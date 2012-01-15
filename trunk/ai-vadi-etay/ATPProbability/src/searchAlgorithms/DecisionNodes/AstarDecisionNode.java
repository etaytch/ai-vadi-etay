package searchAlgorithms.DecisionNodes;


import java.util.Vector;

import searchAlgorithms.AtpProblem;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Defs;
import simulator.Road;
import simulator.Vertex;

/**
 * A* decision node
 * 
 *
 */
public class AstarDecisionNode extends GreedyDecisionNode{

	/**
	 * Accumulated weight from the first decision node
	 */
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

	@Override
	public void expand(Problem problem){
		_children = new Vector<DecisionNode>();
		if (_nestingLevel==Defs.NESTING_LEVEL) return;		
		for(Vertex v : _vertex.get_neighbours().keySet()){
			//if((_parent!=null)&&(_parent._vertex.equals(v))&&(_car.equals(_parent.get_car()))) continue;							// don't calc parent
			if (_vertex.get_neighbours().get(v).is_flooded() && _car.get_coff()==0) continue;	// don't calc flooded road with regular car
			AstarDecisionNode newNode = new AstarDecisionNode(v, _car, _vertex.get_neighbours().get(v),this, _nestingLevel++);
			newNode._H = clacHuristic(_car,((AtpProblem) problem).get_env(), 
										v, 
										((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
			_children.add(newNode);
		}
		for (Car c : _vertex.get_cars().values()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				//if((_parent!=null)&&(_parent._vertex.equals(v))&&(_car.equals(_parent.get_car()))) continue;							// don't calc parent
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				AstarDecisionNode newNode = new AstarDecisionNode(v, c, _vertex.get_neighbours().get(v), this,_nestingLevel++);
				newNode._H = clacHuristic(c,((AtpProblem) problem).get_env(), 
											v, 
											((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
				_children.add(newNode);
			}
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
			return (weight/(speed*coff));
		}
		
		return (weight/speed);
	}

	public double get_accWeight() {
		return _accWeight;
	}


	public void set_accWeight(double accWeight) {
		_accWeight = accWeight;
	}
	
	/**
	 * 
	 */
	@Override
	public int compareTo(DecisionNode o) {
		if (get_H()+get_accWeight()>((AstarDecisionNode)o).get_H()+((AstarDecisionNode)o).get_accWeight()) return 1;
		if (get_H()+get_accWeight()<((AstarDecisionNode)o).get_H()+((AstarDecisionNode)o).get_accWeight()) return -1;
		return 0;
	}

}
