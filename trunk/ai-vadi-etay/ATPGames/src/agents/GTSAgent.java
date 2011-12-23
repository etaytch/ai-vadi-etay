package agents;



import searchAlgorithms.GameAtpProblem;
import searchAlgorithms.MaxiMax;
import searchAlgorithms.MaxiMaxGameDecisionNode;
import searchAlgorithms.MutualMax;
import searchAlgorithms.MiniMaxAlphaPruning;
import searchAlgorithms.DecisionNodes.GameDecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Environment;
import simulator.Vertex;


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
	}
	
	public MaxiMaxGameDecisionNode getMaxiMaxInitNode() {
		Agent human = _env.getHumanAgent();		
		return new MaxiMaxGameDecisionNode(_vertex,
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
	}
	
	@Override
	public void chooseBestAction(Environment env) {
		Problem problem = new GameAtpProblem(env, _vertex, _goalPosition, _car,this);
				
		//_actions.add(MiniMaxAlphaPruning.MiniMaxDecision(env,this, problem, getInitNode()));
		if(_typeGame==1){
			_actions.add(MiniMaxAlphaPruning.MiniMaxDecision(env,this,_env.getHumanAgent(), problem, getInitNode()));
		}
		
		if(_typeGame==2){
			_actions.add(MaxiMax.MaxiMaxDecision(env,this,_env.getHumanAgent(), problem, getMaxiMaxInitNode()));
		}
		
		if(_typeGame==3){
			_actions.add(MutualMax.MutualDecision(env,this,_env.getHumanAgent(), problem, getInitNode()));
		}
	}
	
	@Override
	public void search(Environment env, Vertex initPos,Vertex goalPosition,Car initCar )
	{		
	
	}	
}
