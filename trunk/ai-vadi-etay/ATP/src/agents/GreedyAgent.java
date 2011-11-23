package agents;


import java.util.Vector;
import searchAlgorithms.AtpDecisionNode;
import searchAlgorithms.DecisionNode;
import searchAlgorithms.GeneralSearch;
import searchAlgorithms.GreedyDecisionNode;
import searchAlgorithms.Problem;
import searchAlgorithms.AtpProblem;
import simulator.Action;
import simulator.Car;
import simulator.Enviornment;
import simulator.MoveAction;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;

public class GreedyAgent extends Agent {

	protected Vector<Action> _actions;
	
	public GreedyAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
	}
	
	@Override
	public void search(Enviornment env, Vertex initPos,Vertex goalPosition,Car initCar )
	{		
		Problem problem = new AtpProblem(env, initPos, goalPosition, initCar ,this);
		Vector<DecisionNode> vec = GeneralSearch.search(problem,true);
		_actions = translateNodeToAction(vec);
	}

	private Vector<Action> translateNodeToAction(Vector<DecisionNode> vec) {
		Vector<Action> res = new Vector<Action>(); 
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

	@Override
	public void chooseBestAction(Enviornment env) {
		if(!_actions.isEmpty()){
			Action action = _actions.remove(0);
			get_actions().offer(action);
		}		
	}

	@Override
	public AtpDecisionNode getInitNode() {
		return new GreedyDecisionNode(_initPosition, _car, null, null,0);
	}

}
