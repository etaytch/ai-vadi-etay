package agents;

import searchAlgorithms.AstarDecisionNode;
import searchAlgorithms.AtpDecisionNode;
import simulator.Car;
import simulator.Vertex;

public class AstarAgent  extends GreedyAgent{
	
	public AstarAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
		// TODO Auto-generated constructor stub
	}
	@Override
	public AtpDecisionNode getInitNode() {
		return new AstarDecisionNode(_initPosition, _car, null, null,0);
	}
}
