package agents;

import searchAlgorithms.DecisionNodes.AstarDecisionNode;
import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import simulator.Car;
import simulator.Vertex;

/**
 * this is the A* Agent it uses the greedy agent huristic,
 * The only change is the getInitNode() method witch returns A*Decision Node
 * 
 */
public class AstarAgent  extends GreedyAgent{
	
	public AstarAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
	}
	
	@Override
	public AtpDecisionNode getInitNode() {
		return new AstarDecisionNode(_initPosition, _car, null, null,0);
	}
}
