package searchAlgorithms.DecisionNodes;

import java.util.Vector;

import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Defs;
import simulator.Road;
import simulator.Vertex;

/**
 * 
 * the American traveler problem Decision node 
 *
 */
public class AtpDecisionNode implements  DecisionNode  {
	protected  Vertex _vertex;
	protected  Car _car;
	protected  Road _road;
	protected  AtpDecisionNode _parent;
	protected  Vector<DecisionNode> _children;
	protected  double _H;  //Heuristic calculation for this decision node
	protected  int _nestingLevel; 

	
	public AtpDecisionNode(Vertex vertex, Car car, Road road, AtpDecisionNode parent,int nestingLevel) {
		super();
		_vertex = vertex;
		_car = car;
		_road = road;
		_parent = parent;
		_children = null;
		_nestingLevel = nestingLevel;
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
	
	/**
	 * expansion method. the algorithm to expand this decision node to all possible decisions 
	 */
	public void expand(Problem problem){
		if (_nestingLevel==Defs.NESTING_LEVEL) return;
		_children = new Vector<DecisionNode>();
		for(Vertex v : _vertex.get_neighbours().keySet()){
			_children.add(new AtpDecisionNode(v, _car, _vertex.get_neighbours().get(v), this,_nestingLevel++));
		}
		for (Car c : _vertex.get_cars().values()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				_children.add(new AtpDecisionNode(v, c, _vertex.get_neighbours().get(v), this,_nestingLevel++));
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

	/**
	 * compare to method for the priority queue
	 */
	public int compareTo(DecisionNode o) {
		return 0;
	}

	@Override
	public int getNestingLevel() {
		
		return _nestingLevel;
	}

	/**
	 * equals method for the memoization mechanism
	 */
	@Override
	public boolean equals(DecisionNode dn) {
		if ((_vertex.equals(((AtpDecisionNode)dn)._vertex)) &&
			(_car.equals(((AtpDecisionNode)dn)._car))) 
		
			return true;
		else
			return false;
	}	
}
