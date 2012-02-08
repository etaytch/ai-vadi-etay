package simulator;

import simulator.Interfaces.Action;
import agents.Agent;
import agents.BeliefStateNode;

public class MoveAction implements Action {

	public Agent _agent;
	public Vertex _newVertex;
	
	public MoveAction(Agent agent, Vertex newVertex) {
		super();
		_agent = agent;
		_newVertex = newVertex;
	}
	
	public MoveAction(Agent agent, Vertex newVertex, double reward) {
		super();
		_agent = agent;
		_newVertex = newVertex;
	}

	public void performAction(Simulator sim) {
		sim.moveAgent(_agent, _newVertex);
	}

	public double getReward(Vertex fromVertex, BeliefStateNode bsn) {
		
		Road theRoad = fromVertex.get_neighbours().get(_newVertex);
		
		if (theRoad==null) return Defs.NEG_INFINITY;
		
		Road r1 = fromVertex.get_neighbours().get(_newVertex);
		Road r2 = _newVertex.get_neighbours().get(fromVertex);
		 
		
		Double flooded =  0.0;
		if (bsn._probRoads.containsKey(r1)){
			 flooded = bsn._probRoads.get(r1);
		}else if (bsn._probRoads.containsKey(r2)){
			 flooded = bsn._probRoads.get(r2);
		}else{
			flooded = theRoad.is_flooded()? 1.0 : 0.0;
		}
		
		double reward = (flooded>0.0)? 
							(bsn._agentCar.get_coff()>0 ? 
								((double)(theRoad.get_weight())/(bsn._agentCar.get_speed()*bsn._agentCar.get_coff()))
								:Double.NEGATIVE_INFINITY)  
						:((double)(theRoad.get_weight())/bsn._agentCar.get_speed());
		if (reward>0)
		{
			reward = (-1)*reward; 
		}
		return reward;
	}	
	
	public String toString(){								
		return "MoveAction: To:"+_newVertex.get_number()+"\n";
		
	}
}
