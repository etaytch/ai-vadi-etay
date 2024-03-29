package agents;


import java.util.Vector;
import searchAlgorithms.GeneralSearch;
import searchAlgorithms.AtpProblem;
import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import searchAlgorithms.DecisionNodes.GreedyDecisionNode;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Environment;
import simulator.MoveAction;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import simulator.Interfaces.Action;

/**
 * Greedy agent implementation, the Expand algorithm is implemented in the GreedyDecisionNode class
 * @author Vadi
 *
 */
public class GreedyAgent extends Agent {

	protected Vector<Action> _actions;
	
	public GreedyAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
	}
	
	/**
	 * this the general search method, it creates a problem and run the general search algorithm
	 * on it, then translate the resulted chain of DecisionNodes to Actions
	 */
	@Override
	public void search(Environment env, Vertex initPos,Vertex goalPosition,Car initCar )
	{		
		Problem problem = new AtpProblem(env, initPos, goalPosition, initCar ,this);
		Vector<DecisionNode> vec = GeneralSearch.search(problem,true,_expend_steps);
		_actions = translateNodeToAction(vec);
	}

	/**
	 * 
	 * @param Vector vector of DecisionNodes
	 * @return Vector  of actions
	 */
	private Vector<Action> translateNodeToAction(Vector<DecisionNode> vec) {
		
		Vector<Action> res = new Vector<Action>(); 
		if (vec==null)
		{
		   return res;
		}
		//remove the root - start position
		vec.removeElementAt(0);
		for(DecisionNode atpdn : vec){
			if (((AtpDecisionNode)atpdn).get_car().get_name().equals(_car.get_name())){
				res.add(new MoveAction(this,((AtpDecisionNode)atpdn).get_vertex()));
			}
			else{
			res.add(new SwitchCarAndMoveAction(this,((AtpDecisionNode)atpdn).get_car().get_name(),((AtpDecisionNode)atpdn).get_vertex()));
			}
		}
		if(res.size()==0){
			set_state("stuck","Unable to find path to goal");	
		}
		return res;	
	}

	/**
	 * pop the first action From the list of periodic actions calculated by the search algorithm 
	 * and push it to the "next action to perform queue"
	 * 
	 */
	@Override
	public void chooseBestAction(Environment env) {
		if(!_actions.isEmpty()){
			Action action = _actions.remove(0);  //get next predicted action
			get_actions().offer(action);		//offer it to be the next performed action
		}		
	}

	@Override
	public AtpDecisionNode getInitNode() {
		return new GreedyDecisionNode(_initPosition, _car, null, null,0);
	}

}
