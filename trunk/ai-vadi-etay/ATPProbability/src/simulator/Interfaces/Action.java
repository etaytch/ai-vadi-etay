package simulator.Interfaces;

import agents.BeliefStateNode;
import simulator.Simulator;
/**
 * 
 * Action interface
 *
 */
public interface Action {
	/**
	 * perform action in the simulated world
	 * @param sim the simulator
	 */
	void performAction(Simulator sim);
    double getReward();
    double getReward(BeliefStateNode bn);
}
