package agents;

import java.util.Map;
import java.util.Vector;

import searchAlgorithms.GameAtpProblem;
import searchAlgorithms.GeneralSearch;
import searchAlgorithms.MiniMax;
import searchAlgorithms.MiniMaxAlphaPruning;
import searchAlgorithms.NewMiniMaxAlphaPruning;
import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import searchAlgorithms.DecisionNodes.GameDecisionNode;
import searchAlgorithms.DecisionNodes.SumZeroGameDecisionNode;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Chart;
import simulator.DecisionNodeInfo;
import simulator.Environment;
import simulator.Vertex;
import simulator.Interfaces.Action;

/**
 * this is the A* Agent it uses the greedy agent huristic,
 * The only change is the getInitNode() method witch returns A*Decision Node
 * 
 */
public class GTSAgent  extends Agent{
	private Environment _env;
	
	public GTSAgent(int typeGame,int typeAgent,String name, Vertex initPosition, Vertex goalPosition,
			Car car,Environment env) {
		super(typeGame,typeAgent,name, initPosition, goalPosition, car);
		_env = env;
		
	}
	
	
	public SumZeroGameDecisionNode getInitNode1() {
		Agent human = _env.getHumanAgent();
		DecisionNodeInfo dni = new DecisionNodeInfo(_name,
																_vertex,
																_car,
																null,
																null,
																this._goalPosition.get_number(),
																0.0,
																null,
																human._name,
																human._vertex,
																human._car,
																null,
																null,
																human._goalPosition.get_number(),
																0.0,
																null,
																0); 
		return new SumZeroGameDecisionNode(dni);
		//return new SumZeroGameDecisionNode(_initPosition, _car, null, null,0,human._vertex,human._car);
	}

	@Override
	public GameDecisionNode getInitNode() {
		Agent human = _env.getHumanAgent();		
		return new GameDecisionNode(_vertex,
										human._vertex,
										this._goalPosition,
										human._goalPosition,
										_car,
										human._car,
										null,
										null,
										0.0,
										0.0,
										null,																																															
										null);
		//return new SumZeroGameDecisionNode(_initPosition, _car, null, null,0,human._vertex,human._car);
	}
	
	@Override
	public void chooseBestAction(Environment env) {
		Problem problem = new GameAtpProblem(env, _vertex, _goalPosition, _car,this);
				
		//_actions.add(MiniMaxAlphaPruning.MiniMaxDecision(env,this, problem, getInitNode()));
		_actions.add(NewMiniMaxAlphaPruning.MiniMaxDecision(env,this,_env.getHumanAgent(), problem, getInitNode()));
	}
	
	@Override
	public void search(Environment env, Vertex initPos,Vertex goalPosition,Car initCar )
	{		
		Problem problem = new GameAtpProblem(env, _vertex, _goalPosition, _car,this);
		//Vector<DecisionNode> vec = GeneralSearch.search(problem,true,_expend_steps);
		
		_actions.add(MiniMaxAlphaPruning.MiniMaxDecision(env,this, problem, getInitNode()));
	}	
}
