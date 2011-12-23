package searchAlgorithms;

import java.util.Vector;

import agents.Agent;
import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import searchAlgorithms.DecisionNodes.GameDecisionNode;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Defs;
import simulator.MoveAction;
import simulator.Road;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import simulator.Interfaces.Action;

public class MaxiMaxGameDecisionNode extends GameDecisionNode {
	
	public double _mutualValue[];
	
	public MaxiMaxGameDecisionNode(Vertex myVertex, Car myCar, Road road,
			AtpDecisionNode parent, int nestingLevel, Vertex opponentVertex,
			Car opponentCar) {
		super(myVertex, myCar, road, parent, nestingLevel, opponentVertex, opponentCar);
		_mutualValue = new double[2];
	}
	public MaxiMaxGameDecisionNode(Vertex compVertex,
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
		super(compVertex,humanVertex,compGoal,humanGoal,compCar,humanCar,compRoad,humanRoad,compTime,humanTime,action,parent);
		_mutualValue = new double[2];
	}
	
	public MaxiMaxGameDecisionNode(
			GameDecisionNode gameDecisionNode) {
		super(gameDecisionNode);
		_mutualValue = new double[2];
		_mutualValue[0] = ((MaxiMaxGameDecisionNode)gameDecisionNode)._mutualValue[0];
		_mutualValue[1] = ((MaxiMaxGameDecisionNode)gameDecisionNode)._mutualValue[1];
		
	}
	public MaxiMaxGameDecisionNode getMaxNode(MaxiMaxGameDecisionNode root, MaxiMaxGameDecisionNode ans) {
		if(ans._parent==null) return null;	// impossible..
		if(ans._parent._id==Defs.MAX_ROOT_ID){	// found the one!
			return ans;			
		}
		else return getMaxNode(root,(MaxiMaxGameDecisionNode)ans._parent);		
	}
	
	public void expand(Problem problem, Agent a, Agent human,int turn){
		_children = new Vector<DecisionNode>();
		// computer's turn
		if(turn%2==1){
			if(_compGoal.equals(_compVertex)){
				System.out.println("Computer has reached goal");				
				MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);
				newNode._parent = this;
				System.out.println(newNode);
				_children.add(newNode);
				return;
			}
			for(Vertex v : _compVertex.get_neighbours().keySet()){
				MaxiMaxGameDecisionNode _grandParent=null;
				if(_parent!=null){
					_grandParent = (MaxiMaxGameDecisionNode)(_parent._parent); 
				} 
				if((_grandParent !=null) && (_grandParent._compVertex.equals(v)) && (_grandParent._compCar.equals(_compCar))){
					continue;							// don't calc parent
				}
				if (_compVertex.get_neighbours().get(v).is_flooded() && _compCar.get_coff()==0) continue;	// don't calc flooded road with regular car
				
				Road r = _compVertex.get_neighbours().get(v);
				Car c = _compCar;
				// create a MaxiMaxGameDecisionNode with each my_ parameter is replaced with opponent_
				MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);
				newNode._compVertex=v;					
				newNode._compRoad=r;
				newNode._parent=this;			
				newNode._compTime = _compTime+calcWeight(r, c);
				newNode._action = new MoveAction(null,v);			
				System.out.println("ComputerNode:\n"+newNode);
				//System.out.println(newNode.get_dni());
				_children.add(newNode);		
			}
			
			for (Car c : _compVertex.get_cars().values()){
				for(Vertex v : _compVertex.get_neighbours().keySet()){					
					MaxiMaxGameDecisionNode _grandParent=null;
					if(_parent!=null){
						_grandParent = (MaxiMaxGameDecisionNode)_parent._parent; 
					} 
					if((_grandParent !=null) && (_grandParent._compVertex.equals(v)) && (_grandParent._compCar.equals(c))){
						continue;							// don't calc parent
					}
					Road r = _compVertex.get_neighbours().get(v);
					if (r.is_flooded() && c.get_coff()==0) continue;	// don't calc flooded road with regular car					
					
					// create a MaxiMaxGameDecisionNode with each my_ parameter is replaced with opponent_
					MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);				
					newNode._compVertex=v;
					newNode._compCar=c;					
					newNode._compRoad=r;
					newNode._parent=this;
					newNode._compTime = _compTime+calcWeight(r, c);
					newNode._action = new SwitchCarAndMoveAction(null,c.get_name(),v);													
					_children.add(newNode);
				}
			}
		}
		else{
			if(_humanGoal.equals(_humanVertex)){
				System.out.println("Human has reached goal");
				MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);
				newNode._parent = this;
				System.out.println(newNode);
				_children.add(newNode);				
				return;
			}
			for(Vertex v : _humanVertex.get_neighbours().keySet()){
				MaxiMaxGameDecisionNode _grandParent=null;
				if(_parent!=null){
					_grandParent = (MaxiMaxGameDecisionNode)_parent._parent; 
				} 
				if((_grandParent !=null) && (_grandParent._humanVertex.equals(v)) && (_grandParent._humanCar.equals(_humanCar))){
					continue;							// don't calc parent
				}
				if (_humanVertex.get_neighbours().get(v).is_flooded() && _humanCar.get_coff()==0) continue;	// don't calc flooded road with regular car
				
				Road r = _humanVertex.get_neighbours().get(v);
				Car c = _humanCar;
				// create a MaxiMaxGameDecisionNode with each my_ parameter is replaced with opponent_
				MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);
				newNode._humanVertex=v;					
				newNode._humanRoad=r;
				newNode._parent=this;			
				newNode._humanTime = _humanTime+calcWeight(r, c);
				newNode._action = new MoveAction(null,v);			
				System.out.println("HumanNode:\n"+newNode);			
				//System.out.println(newNode.get_dni());
				_children.add(newNode);		
			}			
			for (Car c : _humanVertex.get_cars().values()){
				for(Vertex v : _humanVertex.get_neighbours().keySet()){					
					MaxiMaxGameDecisionNode _grandParent=null;
					if(_parent!=null){
						_grandParent = (MaxiMaxGameDecisionNode)_parent._parent; 
					} 
					if((_grandParent !=null) && (_grandParent._humanVertex.equals(v)) && (_grandParent._humanCar.equals(c))){
						continue;							// don't calc parent
					}
					Road r = _humanVertex.get_neighbours().get(v);
					if (r.is_flooded() && c.get_coff()==0) continue;	// don't calc flooded road with regular car					
					
					// create a MaxiMaxGameDecisionNode with each my_ parameter is replaced with opponent_
					MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);				
					newNode._humanVertex=v;
					newNode._humanCar=c;					
					newNode._humanRoad=r;
					newNode._parent=this;
					newNode._humanTime = _humanTime+calcWeight(r, c);
					newNode._action = new SwitchCarAndMoveAction(null,c.get_name(),v);													
					_children.add(newNode);
				}
			}
		}										
	}
}
