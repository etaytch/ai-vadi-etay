package simulator;

import simulator.Interfaces.Action;
import agents.Agent;
import agents.BeliefStateNode;

public class MoveAction implements Action {

	public Agent _agent;
	public Vertex _newVertex;
	//public double _reward;
	
	public MoveAction(Agent agent, Vertex newVertex) {
		super();
		//_reward = -1*Double.MAX_VALUE;
		_agent = agent;
		_newVertex = newVertex;
	}
	
	public MoveAction(Agent agent, Vertex newVertex, double reward) {
		super();
		//_reward = reward;
		_agent = agent;
		_newVertex = newVertex;
	}

	public void performAction(Simulator sim) {
		sim.moveAgent(_agent, _newVertex);
	}

	public double getReward(Vertex fromVertex, Car car) {
		Road theRoad = fromVertex.get_neighbours().get(_newVertex);
		if (theRoad==null) return Defs.NEG_INFINITY;
		double reward = theRoad.is_flooded() ? 
							(car.get_coff()>0 ? 
								((double)(theRoad.get_weight())/(car.get_speed()*car.get_coff()))
								:(Defs.NEG_INFINITY)) 
							:((double)(theRoad.get_weight())/car.get_speed());
		return reward;
	}	
	
	public String toString(){								
		return "MoveAction: To:"+_newVertex.get_number()+"\n";
		
	}
}
