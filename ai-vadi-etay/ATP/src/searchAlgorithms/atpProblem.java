package searchAlgorithms;

import agents.Agent;
import simulator.Car;
import simulator.Simulator;
import simulator.Vertex;

public class atpProblem implements Problem {
	
	private Simulator _sim;
	private Vertex _init;
	private Vertex _goal;
	private Car _initCar;
	private Agent _agent; 
	
	

	public atpProblem(Simulator sim, Vertex init, Vertex current, Vertex goal, Car initCar, Agent agent) {
		super();
		_agent = agent;
		_sim = sim;
		_init = init;
		_goal = goal;
		_initCar = initCar;
	}

	
	public Simulator get_sim() {
		return _sim;
	}


	public void set_sim(Simulator sim) {
		_sim = sim;
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
