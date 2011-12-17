package simulator.Interfaces;

import agents.Agent;
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
	public void performAction(Simulator sim);
	public String toString();
	public void setAgent(Agent a);
}
