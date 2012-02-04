package simulator;

import simulator.Interfaces.Action;
import agents.Agent;

public class SwitchCarAction implements Action {

	public Agent _agent;
	public String _carName;	
	
	public SwitchCarAction(Agent agent, String carName) {
		super();
		_agent = agent;
		_carName = carName;
	}


	@Override
	public void performAction(Simulator sim) {
		sim.agentSwitchCar(_agent, _agent.get_car(), _carName);
	}

	@Override
	public double getReward(Vertex fromVertex, Car car) {
		return Defs.TSWITCH;
	}
	public String toString(){								
		return "SwitchAction: To:"+_carName+"\n";		
	}
}
