package agents;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	@Override
	public GameDecisionNode getInitNode(Map<Vertex,List<Car>> removedCars, Map<Vertex,List<Car>> addedCars) {
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
										null,
										removedCars,
										addedCars);
	}
	
	public MaxiMaxGameDecisionNode getMaxiMaxInitNode(Map<Vertex,List<Car>> removedCars, Map<Vertex,List<Car>> addedCars) {
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
										null,
										removedCars,
										addedCars);
	}
	
	public Map<Vertex,List<Car>> generateVertexCarsList(Environment env){
		Map<Vertex,List<Car>> ans = new HashMap<Vertex,List<Car>>();
		for(Vertex v : env.get_vertexes().values()){
			ans.put(v, new ArrayList<Car>());			
		}		
		return ans;		
	}
	
	@Override
	public void chooseBestAction(Environment env) {
		Problem problem = new GameAtpProblem(env, _vertex, _goalPosition, _car,this);
		
		Map<Vertex,List<Car>> _removedCars = generateVertexCarsList(env);
		Map<Vertex,List<Car>> _addedCars = generateVertexCarsList(env);
		
		
		//_actions.add(MiniMaxAlphaPruning.MiniMaxDecision(env,this, problem, getInitNode()));
		if(_typeGame==1){
			Action a = MiniMaxAlphaPruning.MiniMaxDecision(env,this,_env.getHumanAgent(), problem, getInitNode(_removedCars,_addedCars));
			if(a==null){
				set_state("stuck", "empty tree search");
			}
			_actions.add(a);
		}
		
		if(_typeGame==2){
			Action a = MaxiMax.MaxiMaxDecision(env,this,_env.getHumanAgent(), problem, getMaxiMaxInitNode(_removedCars,_addedCars));
			if(a==null){
				set_state("stuck", "empty tree search");
			}
			_actions.add(a);
		}
		
		if(_typeGame==3){
			Action a = MutualMax.MutualDecision(env,this,_env.getHumanAgent(), problem, getInitNode(_removedCars,_addedCars));
			if(a==null){
				set_state("stuck", "empty tree search");
			}
			_actions.add(a);
		}
	}
	
	@Override
	public void search(Environment env, Vertex initPos,Vertex goalPosition,Car initCar )
	{		
	
	}


	@Override
	public GameDecisionNode getInitNode() {
		// TODO Auto-generated method stub
		return null;
	}	
}
