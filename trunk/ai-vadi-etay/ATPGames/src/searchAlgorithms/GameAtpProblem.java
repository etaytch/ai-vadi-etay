package searchAlgorithms;

import agents.Agent;
import searchAlgorithms.DecisionNodes.GameDecisionNode;
import searchAlgorithms.DecisionNodes.SumZeroGameDecisionNode;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Environment;
import simulator.Vertex;

/**
 * The American traveler problems Problem parameters
 * 
 *
 */
public class GameAtpProblem implements Problem {
	
	private Environment _env; //the ATP world abstraction  
	private Vertex _init; //initial vertex
	private Vertex _goal; //goal vertex
	private Agent _agent; //the agent who is trying to move in env
	
	

	public GameAtpProblem(Environment env, Vertex init, Vertex goal, Car initCar, Agent agent) {
		super();
		_agent = agent;
		_env = env;
		_init = init;
		_goal = goal;
	}

	
	public Environment get_env() {
		return _env;
	}

	public void set_agent(Agent agent) {
		_agent = agent;
	}


	public void set_sim(Environment env) {
		_env = env;
	}


	public Vertex get_init() {
		return _init;
	}

	public void set_init(Vertex init) {
		_init = init;
	}

	public Vertex get_goal() {
		return _goal;
	}


	public void set_goal(Vertex goal) {
		_goal = goal;
	}


	/**
	 * return if the DecisionNode is a Goal Node
	 */
	@Override
	public boolean goalTest(DecisionNode d) {
		return d.get_goal_evaluation()==_goal.get_number();
	}

	/**
	 * return the first node to expand
	 */
	@Override
	public GameDecisionNode getInitNode() {
		return _agent.getInitNode();
	}

	/**
	 * return the agent of this problem
	 */
	@Override
	public Agent getAgent() {
		return _agent;
	}
}
