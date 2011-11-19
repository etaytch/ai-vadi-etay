package agents;


import java.util.Vector;
import searchAlgorithms.AtpDecisionNode;
import searchAlgorithms.DecisionNode;
import searchAlgorithms.GeneralSearch;
import searchAlgorithms.GreedyDecisionNode;
import searchAlgorithms.Problem;
import searchAlgorithms.atpProblem;
import simulator.Action;
import simulator.Car;
import simulator.Simulator;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;

public class GreedyAgent extends Agent {

	private Vector<Action> _actions;
	
	public GreedyAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
		// TODO Auto-generated construct
	}
	
	@Override
	public void search(Simulator env)
	{		
		Problem problem = new atpProblem(env, _initPosition, _initPosition, _goalPosition,_car,this);
		Vector<DecisionNode> vec = GeneralSearch.search(problem);
		_actions = translateNodeToAction(vec);
	}

	private Vector<Action> translateNodeToAction(Vector<DecisionNode> vec) {
		Vector<Action> res = new Vector<Action>(); 
		for(DecisionNode atpdn : vec){
			res.add(new SwitchCarAndMoveAction(this,((AtpDecisionNode)atpdn).get_car().get_name(),((AtpDecisionNode)atpdn).get_vertex()));
		}
		if(res.size()==0){
			set_state("stuck","Unable to find path to goal");	
		}
		return res;	
	}

	@Override
	public void chooseBestAction(Simulator env) {
		if(!_actions.isEmpty()){
			Action action = _actions.remove(0);
			get_actions().offer(action);
		}		
	}

	@Override
	public AtpDecisionNode getInitNode() {
		return new GreedyDecisionNode(_initPosition, _car, null, null);
	}

}
