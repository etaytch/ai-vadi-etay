package agents;

import java.util.Vector;

import javax.management.AttributeValueExp;
import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import searchAlgorithms.AtpDecisionNode;
import searchAlgorithms.GeneralSearch;
import searchAlgorithms.Problem;
import simulator.Action;
import simulator.Car;
import simulator.Simulator;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import simulator.atpProblem;

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
		_actions =  new Vector<Action>();
		Problem problem = new atpProblem(env, _initPosition, _initPosition, _goalPosition,_car);
		Vector<AtpDecisionNode> vec = GeneralSearch.search(problem);
		_actions = translateNodeToAction(vec);
	}

	private Vector<Action> translateNodeToAction(Vector<AtpDecisionNode> vec) {
		Vector<Action> res = new Vector<Action>(); 
		for(AtpDecisionNode atpdn : vec){
			res.add(new SwitchCarAndMoveAction(this,atpdn.get_car().get_name(),atpdn.get_vertex()));
		}
		if(res==null){
			System.out.println("STUCK");			
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

}
