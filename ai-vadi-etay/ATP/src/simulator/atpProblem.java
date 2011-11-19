package simulator;

import searchAlgorithms.AtpDecisionNode;
import searchAlgorithms.Problem;

public class atpProblem implements Problem {
	
	private Simulator _sim;
	private Vertex _init;
	private Vertex _current;
	private Vertex _goal;
	private Car _initCar;
	
	

	public atpProblem(Simulator sim, Vertex init, Vertex current, Vertex goal, Car initCar) {
		super();
		_sim = sim;
		_init = init;
		_current = current;
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


	public Vertex get_current() {
		return _current;
	}


	public void set_current(Vertex current) {
		_current = current;
	}


	public Vertex get_goal() {
		return _goal;
	}


	public void set_goal(Vertex goal) {
		_goal = goal;
	}


	@Override
	public boolean goalTest() {
		return _current.get_number()==_goal.get_number();
	}


	@Override
	public AtpDecisionNode getInitNode() {
		return new AtpDecisionNode(_current, _initCar, null, null);
	}
}
