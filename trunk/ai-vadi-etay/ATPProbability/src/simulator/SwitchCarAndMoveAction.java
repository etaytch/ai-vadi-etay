package simulator;

import simulator.Interfaces.Action;
import agents.Agent;
import agents.BeliefStateNode;

public class SwitchCarAndMoveAction implements Action {

	public Agent _agent;
	public String _carName;
	public Vertex _newVertex;	
	
	
	public SwitchCarAndMoveAction(Agent agent, String carName, Vertex newVertex ) {
		super();		
		_agent = agent;
		_carName = carName;
		_newVertex = newVertex;
	}


	@Override
	public void performAction(Simulator sim) {
		sim.agentSwitchCar(_agent, _agent.get_car(), _carName);
		sim.moveAgent(_agent, _newVertex);
	}
		
	@Override
	public double getReward(Vertex fromVertex, BeliefStateNode bsn) {	
		return -6666666;
	}

}
