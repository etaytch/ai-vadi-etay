package searchAlgorithms;

import agents.Agent;
import simulator.Car;
import simulator.Enviornment;
import simulator.Vertex;

public class AtpProblem implements Problem {
	
	private Enviornment _env;
	private Vertex _init;
	private Vertex _goal;
	private Agent _agent; 
	
	

	public AtpProblem(Enviornment env, Vertex init, Vertex goal, Car initCar, Agent agent) {
		super();
		_agent = agent;
		_env = env;
		_init = init;
		_goal = goal;
	}

	
	public Enviornment get_env() {
		return _env;
	}

	public void set_agent(Agent agent) {
		_agent = agent;
	}


	public void set_sim(Enviornment env) {
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


	@Override
	public boolean goalTest(DecisionNode d) {
		return d.get_goal_evaluation()==_goal.get_number();
	}


	@Override
	public AtpDecisionNode getInitNode() {
		return _agent.getInitNode();
	}


	@Override
	public Agent getAgent() {
		return _agent;
	}
}
