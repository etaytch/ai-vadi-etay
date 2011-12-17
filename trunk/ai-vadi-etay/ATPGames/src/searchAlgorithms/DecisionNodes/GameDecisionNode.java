package searchAlgorithms.DecisionNodes;

import java.util.ArrayList;
import java.util.Vector;

import agents.Agent;

import generalAlgorithms.Dijkstra;
import generalAlgorithms.Edge;
import generalAlgorithms.Graph;
import generalAlgorithms.Node;
import searchAlgorithms.GameAtpProblem;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.DecisionNodeInfo;
import simulator.Defs;
import simulator.Environment;
import simulator.MoveAction;
import simulator.Road;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import simulator.Interfaces.Action;

/**
 * 
 * Greedy decision node
 *
 */
public class GameDecisionNode implements DecisionNode {

	private DecisionNodeInfo _dni;
	public  Vector<DecisionNode> _children;
	public double _value;
	public Vertex _compVertex;
	public Vertex _humanVertex;
	public Vertex _compGoal;
	public Vertex _humanGoal;
	public Car _compCar;
	public Car _humanCar;
	public Road _compRoad;
	public Road _humanRoad;	
	public double _compTime;
	public double _humanTime;
	public Action _action;
	public GameDecisionNode _parent;

	public GameDecisionNode(Vertex myVertex, Car myCar, Road road,
			AtpDecisionNode parent, int nestingLevel,Vertex opponentVertex, Car opponentCar) {
		_children = new Vector<DecisionNode>();
		_value=0.0;
		//super(myVertex, myCar, road, parent, nestingLevel);
		//_opponentVertex=opponentVertex;
		//_opponentCar=opponentCar;		
	}

	public GameDecisionNode(Vertex compVertex,
								Vertex humanVertex,
								Vertex compGoal,
								Vertex humanGoal,
								Car compCar,
								Car humanCar,
								Road compRoad,
								Road humanRoad,
								double compTime,
								double humanTime,
								Action action,
								GameDecisionNode parent) {
		_children = new Vector<DecisionNode>();
		_value=0.0;
		_compVertex=compVertex;
		_humanVertex=humanVertex;
		_compGoal=compGoal;
		_humanGoal=humanGoal;
		_compCar=compCar;
		_humanCar=humanCar;
		_compRoad=compRoad;
		_humanRoad=humanRoad;	
		_compTime=compTime;
		_humanTime=humanTime;
		_action=action;
		_parent=parent;				
	}		
	
	public GameDecisionNode(GameDecisionNode other){
		_children = new Vector<DecisionNode>();
		_value=0.0;
		_compVertex=other._compVertex;
		_humanVertex=other._humanVertex;
		_compGoal=other._compGoal;
		_humanGoal=other._humanGoal;
		_compCar=other._compCar;
		_humanCar=other._humanCar;
		_compRoad=other._compRoad;
		_humanRoad=other._humanRoad;	
		_compTime=other._compTime;
		_humanTime=other._humanTime;
		_action=other._action;
		_parent=other._parent;				
	}
	
	public DecisionNodeInfo get_dni() {
		return _dni;
	}

	public void set_dni(DecisionNodeInfo _dni) {
		this._dni = _dni;
	}

	public GameDecisionNode(DecisionNodeInfo dni){
		//super(dni.getMyVertex(),dni.getMyCar(),dni.getMyRoad(),(AtpDecisionNode)dni.getMyParent(),dni.getNestingLevel());
		_dni=dni;
	}
	

	@Override
	public int compareTo(DecisionNode o) {
		if (get_H()>((GameDecisionNode)o).get_H()) return 1;	
		if (get_H()<((GameDecisionNode)o).get_H()) return -1;  
		return 0;
	}
	
	protected int get_H() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void expand(Problem problem, Agent a, Agent human,int turn){
		_children = new Vector<DecisionNode>();
		// computer's turn
		if(turn%2==0){
			if(_compGoal.equals(_compVertex)){
				System.out.println("Computer has reached goal");
				return;
			}
			for(Vertex v : _compVertex.get_neighbours().keySet()){
				GameDecisionNode _grandParent=null;
				if(_parent!=null){
					_grandParent = _parent._parent; 
				} 
				if((_grandParent !=null) && (_grandParent._compVertex.equals(v)) && (_grandParent._compCar.equals(_compCar))){
					continue;							// don't calc parent
				}
				if (_compVertex.get_neighbours().get(v).is_flooded() && _compCar.get_coff()==0) continue;	// don't calc flooded road with regular car
				
				Road r = _compVertex.get_neighbours().get(v);
				Car c = _compCar;
				// create a GameDecisionNode with each my_ parameter is replaced with opponent_
				GameDecisionNode newNode = new GameDecisionNode(this);
				newNode._compVertex=v;					
				newNode._compRoad=r;
				newNode._parent=this;			
				newNode._compTime = _compTime+calcWeight(r, c);
				newNode._action = new MoveAction(null,v);			
				System.out.println("ComputerNode:\n"+newNode);
				//System.out.println(newNode.get_dni());
				_children.add(newNode);		
			}			
		}
		else{
			if(_humanGoal.equals(_humanVertex)){
				System.out.println("Human has reached goal");
				return;
			}
			for(Vertex v : _humanVertex.get_neighbours().keySet()){
				GameDecisionNode _grandParent=null;
				if(_parent!=null){
					_grandParent = _parent._parent; 
				} 
				if((_grandParent !=null) && (_grandParent._humanVertex.equals(v)) && (_grandParent._humanCar.equals(_humanCar))){
					continue;							// don't calc parent
				}
				if (_humanVertex.get_neighbours().get(v).is_flooded() && _humanCar.get_coff()==0) continue;	// don't calc flooded road with regular car
				
				Road r = _humanVertex.get_neighbours().get(v);
				Car c = _humanCar;
				// create a GameDecisionNode with each my_ parameter is replaced with opponent_
				GameDecisionNode newNode = new GameDecisionNode(this);
				newNode._humanVertex=v;					
				newNode._humanRoad=r;
				newNode._parent=this;			
				newNode._humanTime = _humanTime+calcWeight(r, c);
				newNode._action = new MoveAction(null,v);			
				System.out.println("HumanNode:\n"+newNode);			
				//System.out.println(newNode.get_dni());
				_children.add(newNode);		
			}			
			
		}						
		
		
	}
	
	@Override
	public void expand(Problem problem){
		_children = new Vector<DecisionNode>();
		//if (_nestingLevel==Defs.NESTING_LEVEL) return;	
		if(_dni.getMyVertex().get_number()==_dni.getMyGoal()){			
			GameDecisionNode newNode = new GameDecisionNode(new DecisionNodeInfo(_dni));
			newNode.get_dni().setOpponentParent(this);			
			_children.add(newNode);
			System.out.println(newNode.get_dni());
			return;
		}
				
		for(Vertex v : _dni.getMyVertex().get_neighbours().keySet()){			
			GameDecisionNode par = (GameDecisionNode)_dni.getMyParent();			
			if((par!=null) && (par.get_dni().getMyVertex().equals(v)) && (par.get_dni().getMyCar().equals(_dni.getMyCar()))){
				continue;							// don't calc parent
			}
			
			if (_dni.getMyVertex().get_neighbours().get(v).is_flooded() && _dni.getMyCar().get_coff()==0) continue;	// don't calc flooded road with regular car
			Road r = _dni.getMyVertex().get_neighbours().get(v);
			Car c = _dni.getMyCar();
			// create a GameDecisionNode with each my_ parameter is replaced with opponent_
			GameDecisionNode newNode = new GameDecisionNode(new DecisionNodeInfo(_dni));
			newNode.get_dni().setOpponentVertex(v);					
			newNode.get_dni().setOpponentRoad(r);
			newNode.get_dni().setOpponentParent(this);			
			newNode.get_dni().setOpponentTime(get_dni().getMyTime()+calcWeight(r, c));
			get_dni().setMyAction(new MoveAction(null,v));			
			
			/*						
			newNode._H = clacHuristic(_dni.getMyCar(),((GameAtpProblem) problem).get_env(), 
										v, 
										((GameAtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
			 */
			System.out.println(newNode.get_dni());
			_children.add(newNode);
		}
		for (Car c : _dni.getMyVertex().get_cars().values()){
			for(Vertex v : _dni.getMyVertex().get_neighbours().keySet()){
//				if((_parent!=null)&&(_parent._vertex.equals(v))&&(_car.equals(_parent.get_car()))) continue;							// don't calc parent				
				if (_dni.getMyVertex().get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue;	// don't calc flooded road with regular car

				Road r = _dni.getMyVertex().get_neighbours().get(v);
				
				// create a GameDecisionNode with each my_ parameter is replaced with opponent_
				GameDecisionNode newNode = new GameDecisionNode(_dni);				
				newNode.get_dni().setOpponentVertex(v);
				newNode.get_dni().setOpponentCar(c);
				newNode.get_dni().setOpponentRoad(r);
				newNode.get_dni().setOpponentParent(this);
				newNode.get_dni().setOpponentTime(Defs.TSWITCH+calcWeight(r, c));
				newNode.get_dni().setOpponentAction(new SwitchCarAndMoveAction(null,c.get_name(),v));				
				//newNode.get_dni().getOpponentVertex().ge
				
				/*
				newNode._H = clacHuristic(c,((GameAtpProblem) problem).get_env(), 
											v, 
											((GameAtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
				*/										
				_children.add(newNode);
			}
		}	
	}

	public GameDecisionNode getRootParent(String agent){
		
		if(_parent!=null){
			return _parent.getRootParent(agent);
		}			
		else return this;		
	}
	
	public GameDecisionNode getRootParent2(String agent){
		if(get_dni().getMyName().equals(agent)){
			if(get_dni().getMyParent()!=null){
				return ((GameDecisionNode)(get_dni().getMyParent())).getRootParent(agent);
			}			
			else return this;
		}
		if(get_dni().getOpponentName().equals(agent)){
			if(get_dni().getOpponentParent()!=null){
				return ((GameDecisionNode)(get_dni().getOpponentParent())).getRootParent(agent);
			}			
			else return this;
		}
		return null;
	}
	
	public double clacHuristic(Car c , Environment env, Vertex vFrom, Vertex vTo, Road road) {
		Graph g = getDijkstraHuristicGraph(constractBestCar(env),env);
		ArrayList<Node> result = new ArrayList<Node>();
		Node from = g.get_node_by_ID(vFrom.get_number());
		Node to = g.get_node_by_ID(vTo.get_number());
		double ans = Dijkstra.findShortestPath(g,from, to, result );
		return ans;
	} 

	private Car constractBestCar(Environment env) {
		double bcoff = 0;
		int bspeed = 0;
		for(Car c: env.get_cars())
		{
			if (c.get_coff()>bcoff)
			{
				bcoff = c.get_coff();
			}
			if (c.get_speed()>bspeed)
			{
				bspeed =c.get_speed(); 
			}
		}		
		return new Car("superCar",bspeed,bcoff);
	}

	
	/**
	 * return Simple graph with calculated edges for the greedy agent heuristic calculation    
	 * @param car
	 * @param env
	 * @return
	 */
	public Graph getDijkstraHuristicGraph(Car car,Environment env){
		Node[] nodes = new Node[env.get_vertexes().size()];
		int i=0;
		for(Integer v:env.get_vertexes().keySet()){
			nodes[i++]=new Node(v.intValue());			
		}		
		
		Vector <Edge> edges = new Vector<Edge>();
		i=0;
		for(Road e:env.get_edges()){
			int from = e.get_from().get_number();
			int target = e.get_to().get_number();
			Double weight = calcWeight(e,car);
			if (weight!=null){
				edges.add(new Edge(nodes[from],nodes[target],weight));
			}
		}	
		Edge[] edgesArr = new Edge[edges.size()];
		for(int k=0;k<edges.size();k++){
			edgesArr[k] = edges.get(k);			
		}
		return new Graph(nodes,edgesArr);	
	}
	
	public Double calcWeight(Road e, Car car) {
		if(e==null){
			// ????
			return Double.MAX_VALUE;
		}
		double coff = car.get_coff();
		int speed = car.get_speed();
		double weight = e.get_weight();
		boolean flooded = e.is_flooded();
		
		if ((flooded) && (coff==0)){
			return null;  
		}
		if ((flooded) && (coff>0)){
			return (weight/(speed*coff));
		}
		
		return (weight/speed);
	}
	/*
	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return ;
	}
	*/

	@Override
	public DecisionNode get_parent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<DecisionNode> get_children() {
		// TODO Auto-generated method stub		
		return this._children;
	}

	@Override
	public int get_goal_evaluation() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNestingLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(DecisionNode dn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPenalty() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPenalty(int penalty) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getValue() {
		// TODO Auto-generated method stub
		return _value ;
	}

	@Override
	public void setValue(double value) {
		// TODO Auto-generated method stub
		_value = value;
	}
	
	public String toString(){
		return "==============================================\n"+
				"Computer:		Human:"+
				"\nv="+_compVertex.get_number()+"			"+"v="+_humanVertex.get_number()+
				"\ng="+_compGoal.get_number()+"			"+"g="+_humanGoal.get_number()+
				"\nc="+_compCar.get_name()+"			"+"c="+_humanCar.get_name()+
				"\nt="+_compTime+"			"+"t="+_humanTime+
				"\naction="+_action+
				"\nvalue="+_value+
				"\n==============================================\n";
		
		
	}
}

