package searchAlgorithms;

import java.util.List;
import java.util.Map;
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
import tools.ATPLogger;

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
			GameDecisionNode parent,
			Map<Vertex,List<Car>> removedCars,
			Map<Vertex,List<Car>> addedCars) {
		super(compVertex,humanVertex,compGoal,humanGoal,compCar,humanCar,compRoad,humanRoad,compTime,humanTime,action,parent,removedCars,addedCars);
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

	public boolean equals(MaxiMaxGameDecisionNode other){
		boolean ans = true;
		
		ans = ans && ((this._turn%2)==(other._turn%2));
		
		if(this._turn%2==1){
			ans = ans && (this._compVertex.get_number()==other._compVertex.get_number());					
			ans = ans && (this._compGoal.get_number()==other._compGoal.get_number());
			ans = ans && ((this._compCar.get_name()).equals(other._compCar.get_name()));			
		}
		else{
			ans = ans && (this._humanVertex.get_number()==other._humanVertex.get_number());
			ans = ans && (this._humanGoal.get_number()==other._humanGoal.get_number());
			ans = ans && ((this._humanCar.get_name()).equals(other._humanCar.get_name()));	
		}
		
		/*
		if((this._compRoad==null) && (other._compRoad!=null)){
			ans = false;
		}
		el8se if((this._compRoad!=null) && (other._compRoad==null)){
			ans = false;
		}
		else ans = ans && ((this._compRoad).equals(other._compRoad));
		

		if((this._humanRoad==null) && (other._humanRoad!=null)){
			ans = false;
		}
		else if((this._humanRoad!=null) && (other._humanRoad==null)){
			ans = false;
		}
		else ans = ans && ((this._humanRoad).equals(other._humanRoad));
		*/
		
		//ans = ans && ((this._compTime)==(other._compTime));
		//ans = ans && ((this._humanTime)==(other._humanTime));
		
				
		
		return ans;
	} 	
	
	public String toString(){
		return "==============================================\n"+
				"Computer:		Human:"+
				"\nv="+_compVertex.get_number()+"			"+"v="+_humanVertex.get_number()+
				"\ng="+_compGoal.get_number()+"			"+"g="+_humanGoal.get_number()+
				"\nc="+_compCar.get_name()+"			"+"c="+_humanCar.get_name()+
				"\nt="+_compTime+"			"+"t="+_humanTime+
				"\naction="+_action+
				"\nvalues= ["+_mutualValue[0]+","+_mutualValue[0]+"]"+
				"\n==============================================\n";				
	}
		
	public void addNodeToChildren(MaxiMaxGameDecisionNode newNode){
		MaxiMaxGameDecisionNode tmpNode = (MaxiMaxGameDecisionNode)_parent;
		if ((newNode._turn%2==1) && (newNode._compVertex.equals(newNode._compGoal))){
			_children.add(newNode);
			return;
		}
		if ((newNode._turn%2==0) && (newNode._humanVertex.equals(newNode._humanGoal))){
			_children.add(newNode);
			return;
		}
		while(tmpNode!=null){
			if(newNode.equals(tmpNode)){
				
				Defs.print("child killed memozation: new is "+newNode._id+", old is "+tmpNode._id);
				return;
			}			
			tmpNode = (MaxiMaxGameDecisionNode)tmpNode._parent;			
		}
		_children.add(newNode);
	}
	public void expand(Problem problem, Agent a, Agent human,int turn){
		_children = new Vector<DecisionNode>();
		// computer's turn
		if(turn%2==1){
			if(_compGoal.equals(_compVertex)){
				Defs.print("Computer has reached goal");				
				MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);
				newNode._parent = this;
				Defs.print(newNode.toString());
				addNodeToChildren(newNode);
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
				Defs.print("ComputerNode:\n"+newNode);
				addNodeToChildren(newNode);
			}
			
			for (Car c : _compVertex.get_cars().values()){
				if((_removedCars!=null) && (_removedCars.get(_compVertex)!=null)){
					if(_removedCars.get(_compVertex).contains(c)) continue;
				}
				
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
					newNode._addedCars.get(_compVertex).add(_compCar);	// add to the added cars list
					newNode._removedCars.get(_compVertex).add(c);	// add to the removed cars list
					newNode._compVertex=v;
					newNode._compCar=c;					
					newNode._compRoad=r;
					newNode._parent=this;
					newNode._compTime = _compTime+calcWeight(r, c);
					newNode._action = new SwitchCarAndMoveAction(null,c.get_name(),v);													
					Defs.print("ComputerNode:\n"+newNode);
					addNodeToChildren(newNode);
				}
			}
			if((_addedCars!=null) && (_addedCars.get(_compVertex)!=null)){
				for (Car c : _addedCars.get(_compVertex)){
					if(_compVertex.get_cars().values().contains(c)) continue;
					
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
						newNode._addedCars.get(_compVertex).add(_compCar);	// add to the added cars list
						newNode._addedCars.get(_compVertex).remove(c);	// add to the removed cars list
						newNode._compVertex=v;
						newNode._compCar=c;					
						newNode._compRoad=r;
						newNode._parent=this;
						newNode._compTime = _compTime+calcWeight(r, c);
						newNode._action = new SwitchCarAndMoveAction(null,c.get_name(),v);													
						Defs.print("ComputerNode:\n"+newNode);
						addNodeToChildren(newNode);
					}
				}
			}
			
		}
		else{
			if(_humanGoal.equals(_humanVertex)){
				Defs.print("Human has reached goal");
				MaxiMaxGameDecisionNode newNode = new MaxiMaxGameDecisionNode(this);
				newNode._parent = this;
				Defs.print(newNode.toString());
				addNodeToChildren(newNode);		
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
				Defs.print("HumanNode:\n"+newNode);			
				addNodeToChildren(newNode);
			}			
			for (Car c : _humanVertex.get_cars().values()){
				if((_removedCars!=null) && (_removedCars.get(_humanVertex)!=null)){
					if(_removedCars.get(_humanVertex).contains(c)) continue;
				}
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
					newNode._addedCars.get(_humanVertex).add(_humanCar);	// add to the added cars list
					newNode._removedCars.get(_humanVertex).add(c);	// add to the removed cars list
					newNode._humanVertex=v;
					newNode._humanCar=c;					
					newNode._humanRoad=r;
					newNode._parent=this;
					newNode._humanTime = _humanTime+calcWeight(r, c);
					newNode._action = new SwitchCarAndMoveAction(null,c.get_name(),v);													
					Defs.print("HumanNode:\n"+newNode);
					addNodeToChildren(newNode);
				}
			}
			
			if((_addedCars!=null) && (_addedCars.get(_humanVertex)!=null)){
				for (Car c : _addedCars.get(_humanVertex)){
					if(_humanVertex.get_cars().values().contains(c)) continue;
					
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
						newNode._addedCars.get(_humanVertex).add(_humanCar);	// add to the added cars list
						newNode._addedCars.get(_humanVertex).remove(c);	// add to the removed cars list
						newNode._humanVertex=v;
						newNode._humanCar=c;					
						newNode._humanRoad=r;
						newNode._parent=this;
						newNode._humanTime = _humanTime+calcWeight(r, c);
						newNode._action = new SwitchCarAndMoveAction(null,c.get_name(),v);													
						Defs.print("HumanNode:\n"+newNode);
						addNodeToChildren(newNode);
					}
				}				
				
				
			}
			
			
		}										
	}
}
