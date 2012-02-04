package simulator.Interfaces;

import agents.BeliefStateNode;
import simulator.Car;
import simulator.Road;
import simulator.Simulator;
import simulator.Vertex;
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
    double getReward(Vertex fromVertex, Car car);   
}
