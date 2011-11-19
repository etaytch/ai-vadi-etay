package searchAlgorithms;

import java.util.Vector;
import simulator.Car;
import simulator.Road;
import simulator.Vertex;

public class AtpDecisionNode implements  DecisionNode  {
	protected  Vertex _vertex;
	protected  Car _car;
	protected  Road _road;
	protected  AtpDecisionNode _parent;
	protected  Vector<DecisionNode> _children;
	protected  double _H;
	
	public AtpDecisionNode(Vertex vertex, Car car, Road road, AtpDecisionNode parent) {
		super();
		_vertex = vertex;
		_car = car;
		_road = road;
		_parent = parent;
		_children = null;
		set_H(0);
	}
	
	public void set_H(double H) {
		this._H = H;
	}
	
	
	public double get_H() {
		return _H;
	}
	
	public AtpDecisionNode get_parent() {
		return _parent;
	}

	
	public void add_Child(AtpDecisionNode node) {
		_children.add(node);
	}
	
	
	public Vector<DecisionNode> get_children() {
		return _children;
	}
	
	
	public void expand(Problem problem){
		_children = new Vector<DecisionNode>();
		for(Vertex v : _vertex.get_neighbours().keySet()){
			_children.add(new AtpDecisionNode(v, _car, _vertex.get_neighbours().get(v), this));
		}
		for (Car c : _vertex.get_cars().values()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				_children.add(new AtpDecisionNode(v, c, _vertex.get_neighbours().get(v), this));
			}
		}	
	}

	public Vertex get_vertex() {
		return _vertex;
	}

	public void set_vertex(Vertex vertex) {
		_vertex = vertex;
	}

	public Car get_car() {
		return _car;
	}

	public void set_car(Car car) {
		_car = car;
	}

	@Override
	public int get_goal_evaluation() {
		return _vertex.get_number();
	}

	public int compareTo(DecisionNode o) {
		return 0;
	}
	
}
