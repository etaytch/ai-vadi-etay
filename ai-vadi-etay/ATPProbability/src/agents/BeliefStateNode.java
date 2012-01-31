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
	public double _utility = Double.MIN_VALUE;
	public Action _action;
	
	
	
	public BeliefStateNode(Vertex srcVertex,Car agentCar,Car otherCar,Vertex carVertex){		
		_probRoads = new LinkedHashMap<Road,Double>() ;
		_agentCar = agentCar;
		_otherCar = otherCar;
		_otherCarVertex = carVertex;
		_srcVertex = srcVertex;	
		_action = null;
		_childeren = new LinkedHashMap<Action,Vector<BeliefStateNode>>();
	}	
	
	public BeliefStateNode(BeliefStateNode bn, LinkedHashMap<Road, Double> d) {
		_probRoads = d;
		_srcVertex = bn._srcVertex;
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
					_childeren.put(new SwitchCarAction(env.getFirstAgent(), _otherCar.get_name(), Defs.TSWITCH),v);
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
//						if((bsn._probRoads.get(r).equals(0.0) && ! _probRoads.get(r).equals(0.0))){							
//							flag=false;
//							break;
//						}
//						if((bsn._probRoads.get(r).equals(1.0)&& ! _probRoads.get(r).equals(1.0))){
//							flag=false;
//							break;
//						}
//						
						
						if(!(bsn._probRoads.get(r).equals(_probRoads.get(r))&& (_probRoads.get(r).equals(1.0)||_probRoads.get(r).equals(0.0)))){
							flag=false;
							break;
						}
						
						if(((_probRoads.get(r)).doubleValue()>0) && ((_probRoads.get(r)).doubleValue()<1)){
							if((bsn._probRoads.get(r).equals(0.0) || bsn._probRoads.get(r).equals(1.0))){
								if(predRoadsExpt.get(r).equals(false)){
									flag=false;
									break;									
								}								
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
											:Double.MIN_VALUE) 
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


	public void getReward(Action a){
		a.getReward(this);
	}

	public boolean isLegal(Environment env) {
		for(Road r: _probRoads.keySet()){
			if (((r.get_from().equals(_srcVertex))&&((_probRoads.get(r)<1) &&(_probRoads.get(r)>0)))||
			    ((r.get_to().equals(_srcVertex))&&((_probRoads.get(r)<1) &&(_probRoads.get(r)>0))))
				return false;
		}
		return true;
	}	
}
