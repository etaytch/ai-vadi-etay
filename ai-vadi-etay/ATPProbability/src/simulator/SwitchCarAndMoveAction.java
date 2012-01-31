package simulator;

import simulator.Interfaces.Action;
import agents.Agent;
import agents.BeliefStateNode;

public class SwitchCarAndMoveAction implements Action {

	public Agent _agent;
	public String _carName;
	public Vertex _newVertex;
	public double _reward;
	
	
	public SwitchCarAndMoveAction(Agent agent, String carName, Vertex newVertex ) {
		super();
		_reward = Double.MIN_VALUE;
		_agent = agent;
		_carName = carName;
		_newVertex = newVertex;
	}
	
	public SwitchCarAndMoveAction(Agent agent, String carName, Vertex newVertex, double reward) {
		super();
		_reward = reward;
		_agent = agent;
		_carName = carName;
		_newVertex = newVertex;
	}


	@Override
	public void performAction(Simulator sim) {
		sim.agentSwitchCar(_agent, _agent.get_car(), _carName);
		sim.moveAgent(_agent, _newVertex);
	}
	
	public double getReward() {
		return _reward;
	}

	@Override
	public double getReward(BeliefStateNode bn) {
		// TODO Auto-generated method stub
		return 0;
	}
}
