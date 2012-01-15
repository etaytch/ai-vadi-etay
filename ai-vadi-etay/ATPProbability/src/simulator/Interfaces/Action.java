package simulator.Interfaces;

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

}
