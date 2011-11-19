package searchAlgorithms;

import simulator.Car;
import simulator.Road;
import simulator.Vertex;

public class AstarDecisionNode extends GreedyDecisionNode{

	private double _accWeight;

	public AstarDecisionNode(Vertex vertex, Car car, Road road,
			AtpDecisionNode parent) {
		super(vertex, car, road, parent);
		_accWeight = ((AstarDecisionNode) _parent).get_accWeight()+calcWeight(road,car);
	}

	private double calcWeight(Road e, Car car){
		double coff = car.get_coff();
		int speed = car.get_speed();
		double weight = e.get_weight();
		boolean flooded = e.is_flooded();
		
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


	public int compareTo(AtpDecisionNode o) {
		if (get_H()+get_accWeight()>o.get_H()+get_accWeight()) return 1;
		if (get_H()+get_accWeight()<o.get_H()+get_accWeight()) return -1;
		return 0;
	}

}
