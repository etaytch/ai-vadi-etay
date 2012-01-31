package simulator;

import simulator.Interfaces.Action;
import agents.Agent;
import agents.BeliefStateNode;

public class MoveAction implements Action {

	public Agent _agent;
	public Vertex _newVertex;
	public double _reward;
	
	public MoveAction(Agent agent, Vertex newVertex) {
		super();
		_reward = Double.MIN_VALUE;
		_agent = agent;
		_newVertex = newVertex;
	}
	
	public MoveAction(Agent agent, Vertex newVertex, double reward) {
		super();
		_reward = reward;
		_agent = agent;
		_newVertex = newVertex;
	}

	public void performAction(Simulator sim) {
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
