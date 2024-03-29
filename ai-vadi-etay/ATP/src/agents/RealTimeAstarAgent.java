package agents;

import searchAlgorithms.DecisionNodes.AstarDecisionNode;
import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import simulator.Car;
import simulator.Environment;
import simulator.Vertex;
import simulator.Interfaces.Action;

/**
 * Real Time A* Agent  - acts like A* but perform search all over again every move
 * 
 *
 */
public class RealTimeAstarAgent extends AstarAgent {

	public RealTimeAstarAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
	}

	@Override
	public void chooseBestAction(Environment env) {
		//search from the current node.
		this.search(env,this.get_vertex(),this.get_goalPosition(),this.get_car());
		if(!_actions.isEmpty()){
			Action action = _actions.remove(0);
			get_actions().offer(action);
		}		
	}
	
	@Override
	public AtpDecisionNode getInitNode() {
		return new AstarDecisionNode(_vertex, _car, null, null,0);
	}
}
