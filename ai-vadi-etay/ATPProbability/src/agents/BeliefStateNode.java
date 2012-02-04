package agents;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import simulator.Car;
import simulator.Defs;
import simulator.Environment;
import simulator.MoveAction;
import simulator.Road;
import simulator.SwitchCarAction;
import simulator.Vertex;
import simulator.Interfaces.Action;

public class BeliefStateNode {

	public LinkedHashMap<Road,Double> _probRoads;
	public LinkedHashMap<Action,Vector<BeliefStateNode>> _childeren;
	public Vertex _srcVertex;
	public Vertex _otherCarVertex;
	public Car _otherCar;
	public Car _agentCar;
	public double _utility;
	public Action _action;
	public double _reward;
	
	
	
	
	public BeliefStateNode(Vertex srcVertex,Car agentCar,Car otherCar,Vertex carVertex){		
		_probRoads = new LinkedHashMap<Road,Double>() ;
		_agentCar = agentCar;
		_utility = Defs.NEG_INFINITY;
		_otherCar = otherCar;
		_otherCarVertex = carVertex;
		_srcVertex = srcVertex;	
		_action = null;
		_childeren = new LinkedHashMap<Action,Vector<BeliefStateNode>>();
	}	
	
	public BeliefStateNode(BeliefStateNode bn, LinkedHashMap<Road, Double> d) {
		_probRoads = d;
		_srcVertex = bn._srcVertex;
		_utility = Defs.NEG_INFINITY;
		_otherCarVertex = bn._otherCarVertex;
		_agentCar = bn._agentCar;
		_otherCar = bn._otherCar;
		_action = null;
		_childeren = new LinkedHashMap<Action,Vector<BeliefStateNode>>();
	}

	public void calcChildren(Vector<BeliefStateNode> beliefNodes, Environment env){
		// switch actions
		if(_srcVertex.equals(_otherCarVertex)){				
			for(BeliefStateNode bsn : beliefNodes){
				if(this.equals(bsn)){
					continue;
				}
				
				if(!((_srcVertex.equals(bsn._srcVertex))
						&& (bsn._srcVertex.equals(bsn._otherCarVertex))
						&& (_agentCar.equals(bsn._otherCar))
						&& (_otherCar.equals(bsn._agentCar)))){
							continue;
				}
				
				boolean flag = false;
				for(Road r : _probRoads.keySet()){
					if(!_probRoads.get(r).equals(bsn._probRoads.get(r))){
						flag = true;
						break;						
					}	
				}			
				if (flag) continue;
				
				boolean found = false;
				for(Action a : _childeren.keySet()){
					if(a instanceof SwitchCarAction){
						if(((SwitchCarAction)a)._carName.equals(_otherCar.get_name())){
							_childeren.get(a).add(bsn);
							found = true;
						}
					}					
				}
				if(!found){
					Vector<BeliefStateNode> v = new Vector<BeliefStateNode>();
					v.add(bsn);
					_childeren.put(new SwitchCarAction(env.getFirstAgent(), _otherCar.get_name()),v);
				}								
			}//for bsn
		}
		
		Map<Vertex,Road> neibs = env.get_vertexes().get(_srcVertex.get_number()).get_neighbours(); 
		for(Vertex v : neibs.keySet()){
			for(BeliefStateNode bsn : beliefNodes){
				if(bsn._srcVertex.equals(v)){
					if(this.equals(bsn)){
						continue;
					}
					if(!_otherCar.equals(bsn._otherCar)){
						continue;
					}
					if(!_agentCar.equals(bsn._agentCar)){
						continue;
					}
					if(!_otherCarVertex.equals(bsn._otherCarVertex)){
						continue;
					}			
					
					LinkedHashMap<Road,Boolean> predRoadsExpt = new LinkedHashMap<Road,Boolean>() ;
					
					for(Road r : _probRoads.keySet()){
						Map<Vertex,Road> otherNeibs = env.get_vertexes().get(v.get_number()).get_neighbours();
												
						for(Road tmpRoad: otherNeibs.values()){
							if(r.get_from().equals(tmpRoad.get_from())&&
									r.get_to().equals(tmpRoad.get_to())){
								predRoadsExpt.put(r,true);
							}
							
							else if(r.get_from().equals(tmpRoad.get_to())&&
									r.get_to().equals(tmpRoad.get_from())){
								predRoadsExpt.put(r, true);
							}
							else {
								predRoadsExpt.put(r,false);
							}							
						}						
						// check if the belief node expectation compliat with the desired one
						
					}	
					boolean flag = true;
					for (Road r:bsn._probRoads.keySet()){			
						if(predRoadsExpt.get(r).equals(false)){
							// child and parent should see the same world (the child is not connected to an unknown edge)
							if(!bsn._probRoads.get(r).equals(_probRoads.get(r))){
								flag=false;
								break;								
							}
						}
						else{
							// if this node knows the world, then so is the child
							if((_probRoads.get(r).equals(0.0))||(_probRoads.get(r).equals(1.0))){								
								if(_probRoads.get(r).equals(bsn._probRoads.get(r))){
									// Suitable child. 
								}
								else{
									flag=false;
									break;
								}
							}			
							else{
								if((!bsn._probRoads.get(r).equals(0.0))&&(!_probRoads.get(r).equals(1.0))){
									flag=false;
									break;
								}
								// Suitable child.								
							}
							
						}														
					}					
					
					if(!flag){
						continue;
					}
					Road theRoad = _srcVertex.get_neighbours().get(v);
					double reward = theRoad.is_flooded() ? 
										(_agentCar.get_coff()>0 ? 
											((double)(theRoad.get_weight())/(_agentCar.get_speed()*_agentCar.get_coff()))
											:(Defs.NEG_INFINITY)) 
										:((double)(theRoad.get_weight())/_agentCar.get_speed());

																
					boolean found = false;
					for(Action a : _childeren.keySet()){
						if(a instanceof MoveAction){
							if(((MoveAction)a)._newVertex.equals(v)){
								_childeren.get(a).add(bsn);
								found = true;
							}
						}					
					}
				
					if(!found){
						Vector<BeliefStateNode> vec = new Vector<BeliefStateNode>();
						vec.add(bsn);
						_childeren.put(new MoveAction(env.getFirstAgent(),v,reward),vec);
					}																							
				}	
			}			
		}
		
		
		return;
	}
	
	public void AddProbRoad(Road r, double p){
		_probRoads.put(r,p);
	}


	

	public boolean isLegal(Environment env) {
		for(Road r: _probRoads.keySet()){
			if (((r.get_from().equals(_srcVertex))&&((_probRoads.get(r)<1) &&(_probRoads.get(r)>0)))||
			    ((r.get_to().equals(_srcVertex))&&((_probRoads.get(r)<1) &&(_probRoads.get(r)>0))))
				return false;
		}
		return true;
	}	
	
	public String toString(){
		String ans="";
		ans+="************************************************************\n";
		ans += "agentV: "+_srcVertex.get_number() +", otherV: "+_otherCarVertex.get_number()+"\n";
		ans += "agentC: "+_agentCar.get_name() +", otherCar: "+_otherCar.get_name()+"\n";
		int i=1;
		ans+="-------\n";
		for(Road r : _probRoads.keySet()){
			ans+="R"+(i++)+": "+r+", value: "+_probRoads.get(r)+"\n";			
		}
		ans+="-------\n";
		for(Action a : _childeren.keySet()){
			ans+="+++++\n";
			ans+="Action:"+a+"\n";
			i=1;
			for(BeliefStateNode child : _childeren.get(a)){			
				ans+="Child"+(i++)+": Vertex: "+child._srcVertex.get_number()+", Car: "+child._agentCar.get_name()+"\n";
				ans+="Chilld utility: "+child._utility+"\n";
				int j=1;
				for(Road r : child._probRoads.keySet()){
					ans+="ChildR"+(j++)+": "+r+", value: "+child._probRoads.get(r)+"\n";			
				}
			}
		}			
		ans += "\nmy utility: "+_utility +", action: "+(_action==null ? "null" : _action)+"\n";
		ans+="************************************************************\n";
		return ans;
	}
}
