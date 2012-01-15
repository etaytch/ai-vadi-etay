package agents;

import java.util.LinkedHashMap;
import java.util.Vector;
import simulator.Car;
import simulator.Environment;
import simulator.Vertex;
import simulator.Interfaces.Action;

public class PropabilityAgent extends Agent {

	protected Vector<Action> _actions;
	LinkedHashMap<BeliefStateNode,Double> _beliefStates;
	
	public PropabilityAgent(String name, Vertex initPosition,
			Vertex goalPosition, Car car) {
		super(name, initPosition, goalPosition, car);
		// TODO Auto-generated constructor stub
	}

	/**
	 * this the general search method, it creates a problem and run the general search algorithm
	 * on it, then translate the resulted chain of DecisionNodes to Actions
	 */
	@Override
	public void search(Environment env, Vertex initPos,Vertex goalPosition,Car initCar ){		
		 _beliefStates = uncertentySearch(env,this);
	}

	
	private LinkedHashMap<BeliefStateNode, Double> uncertentySearch(
			Environment env, PropabilityAgent propabilityAgent) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * pop the first action From the list of periodic actions calculated by the search algorithm 
	 * and push it to the "next action to perform queue" 
	 */
	@Override
	public void chooseBestAction(Environment env) {
		if(!_actions.isEmpty()){
			Action action = _actions.remove(0);  //get next predicted action
			get_actions().offer(action);		//offer it to be the next performed action
		}		
	}
}
