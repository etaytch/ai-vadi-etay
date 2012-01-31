package simulator;

import simulator.Interfaces.Action;
import agents.Agent;
import agents.BeliefStateNode;

public class SwitchCarAction implements Action {

	public Agent _agent;
	public String _carName;	
	public double _reward;
	
	
	public SwitchCarAction(Agent agent, String carName) {
		super();
		_reward = Double.MIN_VALUE;
		_agent = agent;
		_carName = carName;
	}
	
	public SwitchCarAction(Agent agent, String carName, double reward) {
		super();
		_reward = reward;
		_agent = agent;
		_carName = carName;
	}


	@Override
	public void performAction(Simulator sim) {
		sim.agentSwitchCar(_agent, _agent.get_car(), _carName);
	}

	@Override
	public double getReward() {
		return _reward;
	}

	@Override
	public double getReward(BeliefStateNode bn) {
		// TODO Auto-generated method stub
		return 0;
	}
}
