package agents;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import simulator.Action;
import simulator.Car;
import simulator.Simulator;
import simulator.Vertex;

public abstract class Agent {
	
	private String  _name;
	private Car _car;
	protected Queue<Action> _actions;
	private Vertex _vertex;
	private Vertex _initPosition;
	private Vertex _goalPosition;
	private Map<String,String> _state;				// "finished", "stuck"s
	
		
	public String get_state(String key) {
		return _state.get(key);
	}

	public void set_state(String key,String val) {
		this._state.put(key, val);
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
	
	public abstract void chooseBestAction(Simulator env);	// place an action in the queue 
	
	public void search(Simulator env) {
		// TODO Auto-generated method stub
	}
	
	
}



	