package agents;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import simulator.Car;
import simulator.Environment;
import simulator.MoveAction;
import simulator.Road;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import tools.ATPLogger;

/**
 * Speed Nut agent, peek the fastest car and the heaviest road and go there! 
 * finish if it reaches the goal vertex or passes 3 times in the same vertex.
 * @author Vadi
 *
 */
public class SpeedNutAutomationAgent extends Agent {

	
	private Vector<Vertex> _route;	// stores agent's route
	private Map<Vertex,Integer> _amountOfVisits;
	
	
	public SpeedNutAutomationAgent(String name, Vertex initPosition,
			Vertex goalPosition, Car car) {
		super(name, initPosition, goalPosition, car);
		_route = new Vector<Vertex>();		
		_amountOfVisits = new HashMap<Vertex,Integer>(); 
	}

	public Vertex getHeavyRoad(Vector<Vertex> vertics,Map<Vertex,Road> neib){
		Vertex maxVer=null;
		int maxWeight = 0;		
		Vector<Vertex> tobeRemoved = new Vector<Vertex>();
		
		for(Vertex v:vertics){							
			// if just arrived from the vertex - ignore
			if(!_route.isEmpty() && _route.get(_route.size()-1).equals(v)){
				ATPLogger.log("ignoring Vertex #"+v.get_number()+". Agent just arrived from there!");
				tobeRemoved.add(v);
				continue;
			}
			Road e = neib.get(v);			
			if((e.get_weight()>=maxWeight)/*&&!(e.is_flooded() && get_car().get_coff()==0)*/){
				if((e.get_weight()==maxWeight)&&(maxVer!=null)&&(v.get_number()>maxVer.get_number())){
					continue;					
				}
				maxVer = v;
				maxWeight = e.get_weight();								
			}
		}
		if(maxVer!=null){
			tobeRemoved.add(maxVer);	
		}
		for(Vertex v:tobeRemoved){
			vertics.remove(v);
		}		
		return maxVer;
	}
	
	@Override
	public void chooseBestAction(Environment env) {
		Vertex maxVer=null;		
		String maxCarName=null;		
						
		if((_amountOfVisits.get(get_vertex())!=null)&&(_amountOfVisits.get(get_vertex())>=3)){
			set_state("stuck","got 3 times to the same vertex!");
			return;			
		}
		
		Map<Vertex,Road> neib = get_vertex().get_neighbours();
		Set<Vertex> vertics = neib.keySet();
		Vector<Vertex> sortedVertics = new Vector<Vertex>();
				
		for(Vertex v:vertics){
			sortedVertics.add(v);
		}
		Collections.sort(sortedVertics);
		boolean found = false;		
		while((!sortedVertics.isEmpty()) && (!found)){
			maxVer = getHeavyRoad(sortedVertics,neib);
			
			if (maxVer==null){
				ATPLogger.log("Unable to select max weigthed Edge.. ");
				set_state("stuck","Unable to select max weigthed Edge");
				return;
			}		
			Car maxCar=this.get_car();
			Car c=maxCar;
			maxCarName = maxCar.get_name();
			
			if (get_vertex().get_cars().isEmpty()) {
				ATPLogger.log("Vertex has no cars!.. "+get_vertex()+"Keeping current car: "+maxCarName);						
			}
			else{
				Map<String,Car> cars = get_vertex().get_cars();
				Set<String> carsNames = cars.keySet();
				int maxSpeed = this.get_car().get_speed();
				
				for(String cName:carsNames){
					maxCar = cars.get(cName);
					if(maxCar.get_speed()>maxSpeed){
						c=maxCar;
						maxSpeed = maxCar.get_speed();
						maxCarName = cName;
					}						
				}
			}
			Road r = neib.get(maxVer);
			if(!(r.is_flooded() && c.get_coff()==0)){
				found = true;					
			}
			
		}
		
		// add next vertex to route and increase it's counter
		if(found){
			updateRoute(maxVer);		
			if(maxCarName.equals(get_car().get_name())){				
				ATPLogger.log("Agent "+get_name()+" chose to keep original car. "+get_car());
				get_actions().offer(new MoveAction(this, maxVer));
			}
			else{
				get_actions().offer(new SwitchCarAndMoveAction(this, maxCarName, maxVer));
			}
		}
		else{
			set_state("stuck","Unable to select road");
		}
	}
	

	private void updateRoute(Vertex v) {
		_route.add(get_vertex());
		Integer i = _amountOfVisits.get(get_vertex());
		if(i==null){
			_amountOfVisits.put(get_vertex(),1);
		}
		else{
			_amountOfVisits.put(get_vertex(),(i+1));
		}		
	}

	@Override
	public AtpDecisionNode getInitNode() {
		return null;
	}
	

}
