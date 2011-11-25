package simulator;

import simulator.Interfaces.Action;
import agents.Agent;

public class SwitchCarAndMoveAction implements Action {

	Agent _agent;
	String _carName;
	Vertex _newVertex;
	
	
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
}
