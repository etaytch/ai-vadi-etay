package simulator;

import agents.Agent;

public class MoveAction implements Action {

	Agent _agent;
	Vertex _newVertex;
	
	
	public MoveAction(Agent agent, Vertex newVertex) {
		super();
		_agent = agent;
		_newVertex = newVertex;
	}

	public void performAction(Simulator sim) {
		sim.moveAgent(_agent, _newVertex);
	}

}
