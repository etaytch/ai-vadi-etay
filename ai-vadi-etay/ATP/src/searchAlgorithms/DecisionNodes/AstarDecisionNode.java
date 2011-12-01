package searchAlgorithms.DecisionNodes;


import searchAlgorithms.Interfaces.DecisionNode;
import simulator.Car;
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
		if (get_H()+get_accWeight()>((AstarDecisionNode)o).get_H()+get_accWeight()) return 1;
		if (get_H()+get_accWeight()<((AstarDecisionNode)o).get_H()+get_accWeight()) return -1;
		return 0;
	}

}
