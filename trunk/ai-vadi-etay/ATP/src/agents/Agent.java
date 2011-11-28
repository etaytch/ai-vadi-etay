package agents;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import simulator.Car;
import simulator.Environment;
import simulator.Vertex;
import simulator.Interfaces.Action;
/**
 * this is ABSTRACT  ATP agent class
 */
public abstract class Agent {
	
	protected String  _name;
	protected Car _car;
	private Queue<Action> _actions;		
	protected Vertex _vertex;  				//current agent position
	protected Vertex _initPosition;
	protected Vertex _goalPosition;
	protected Map<String,String> _state;	// "finished", "stuck"s
	protected int[] _expend_steps;
		
	public String get_state(String key) {
		return _state.get(key);
	}

	public void set_state(String key,String val) {
		this._state.put(key, val);
	}

	public int get_steps(){
		return _expend_steps[0];
	}
	
	public Agent(String name, Vertex initPosition, Vertex goalPosition, Car car) {
		super();
		_name = name;
		_vertex = initPosition;
		set_initPosition(initPosition);
		set_goalPosition(goalPosition);
		_actions = new LinkedList<Action>();
		_car = car;
		_state=new HashMap<String, String>();
		_expend_steps = new int[1];
		_expend_steps[0]=0;
	}
	
	public Queue<Action> get_actions() {
		return _actions;
	}

	public void set_actions(Queue<Action> actions) {
		_actions = actions;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String name) {
		_name = name;
	}

	public Car get_car() {
		return _car;
	}

	public void set_car(Car car) {
		_car = car;
	}

	public Vertex get_vertex() {
		return _vertex;
	}

	public void set_vertex(Vertex vertex) {
		_vertex = vertex;
	}

	@Override
	public String toString() {
		return "Agent: _name=" + _name + ", _car=" + _car+ ", _vertex=" + _vertex.get_number();
	}

	public void set_initPosition(Vertex _initPosition) {
		this._initPosition = _initPosition;
	}

	public Vertex get_initPosition() {
		return _initPosition;
	}

	public void set_goalPosition(Vertex _goalPosition) {
		this._goalPosition = _goalPosition;
	}

	public Vertex get_goalPosition() {
		return _goalPosition;
	} 
	
	public Action getAction(){
		return get_actions().poll();
	}
	
	/**
	 * this method should be overridden by  all the child agents
	 */
	public abstract void chooseBestAction(Environment env);	// place an action in the queue 
	

	/**
	 * this method  is mainly for creating the first DecisionNode of this agent - used by "AtpProblem"
	 */
	public abstract AtpDecisionNode getInitNode();
	

	
	/**
	 * 
	 * this is the Search algorithm of this agent, it is empty method in simple agents  
	 *
	 * @param env
	 * @param initPos
	 * @param goalPosition
	 * @param initCar
	 */
	public void search(Environment env, Vertex initPos, Vertex goalPosition,
			Car initCar) {
	}
}



	