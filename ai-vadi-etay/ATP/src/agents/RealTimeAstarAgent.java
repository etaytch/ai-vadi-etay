package agents;

import searchAlgorithms.AstarDecisionNode;
import searchAlgorithms.AtpDecisionNode;
import simulator.Action;
import simulator.Car;
import simulator.Enviornment;
import simulator.Vertex;

public class RealTimeAstarAgent extends AstarAgent {

	public RealTimeAstarAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
	}

	@Override
	public void chooseBestAction(Enviornment env) {
		
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
