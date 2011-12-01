package searchAlgorithms.DecisionNodes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import searchAlgorithms.AtpProblem;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Defs;
import simulator.Environment;
import simulator.Road;
import simulator.Vertex;

public class SpeedNutPredictionDecisionNode extends AstarDecisionNode {

	private Vertex _snaAgentVertex =  null;
	private Map<Car,Vertex> _aotomatonCarsPos;
	private Car _snaAgentCar = null;
	private Vector<Vertex> _route;	// stores agent's route
	
	public SpeedNutPredictionDecisionNode(Vertex vertex, Car car, Road road,
			AtpDecisionNode parent, int nestingLevel) {
		super(vertex, car, road, parent, nestingLevel);
		_snaAgentVertex = null;
		_snaAgentCar = null;
		_route = new Vector<Vertex>();
		_aotomatonCarsPos = new HashMap<Car,Vertex>();
	}
	
	public SpeedNutPredictionDecisionNode(Vertex vertex, Car car, Road road,
			AtpDecisionNode parent, int nestingLevel, Vertex snaAgentVertex, Car snaAgentCar,Map<Car,Vertex> aotomatonCarsPos ) {
		super(vertex, car, road, parent, nestingLevel);
		_snaAgentVertex = snaAgentVertex;
		_snaAgentCar = snaAgentCar;
		_route = new Vector<Vertex>();
		_aotomatonCarsPos = aotomatonCarsPos; 
		}
	
	
	@Override
	public void expand(Problem problem){
		//if (_parent!=null)
			PredictAotomatonBestAction(((AtpProblem)problem).get_env()); // Prediction of automaton move is done first thing! except for the root father decision!

		_children = new Vector<DecisionNode>();
		if (_nestingLevel==Defs.NESTING_LEVEL) return;
		
		for(Vertex v : _vertex.get_neighbours().keySet()){
			if((_parent!=null)&&(_parent._vertex.equals(v))) continue;							// don't calc parent
			if (_vertex.get_neighbours().get(v).is_flooded() && _car.get_coff()==0) continue;	// don't calc flooded road with regular car
			SpeedNutPredictionDecisionNode newNode = new SpeedNutPredictionDecisionNode(v, _car, _vertex.get_neighbours().get(v),this, _nestingLevel++,_snaAgentVertex,_snaAgentCar,_aotomatonCarsPos);
			newNode._H = clacHuristic(_car,((AtpProblem) problem).get_env(), 
										v, 
										((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
			_children.add(newNode);
		}
		for (Car c : _vertex.get_cars().values()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				if (c.get_name().equals(_snaAgentCar.get_name())) {					
					continue; // the automaton have this car!
				}
				if((_parent!=null)&&(_parent._vertex.equals(v))) continue;
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				SpeedNutPredictionDecisionNode newNode = new SpeedNutPredictionDecisionNode(v, c, _vertex.get_neighbours().get(v), this,_nestingLevel++,_snaAgentVertex,_snaAgentCar,_aotomatonCarsPos);
				newNode._H = clacHuristic(c,((AtpProblem) problem).get_env(), 
											v, 
											((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
				_children.add(newNode);
			}			
		}
		
		for (Car c : _aotomatonCarsPos.keySet()){
			for(Vertex v : _vertex.get_neighbours().keySet()){
				if (!_aotomatonCarsPos.get(c).equals(v)) continue;
				if (c.get_name().equals(_snaAgentCar.get_name())) {					
					continue; // the automaton have this car!
				}
				if((_parent!=null)&&(_parent._vertex.equals(v))) continue;
				if (_vertex.get_neighbours().get(v).is_flooded() && c.get_coff()==0) continue; 
				SpeedNutPredictionDecisionNode newNode = new SpeedNutPredictionDecisionNode(v, c, _vertex.get_neighbours().get(v), this,_nestingLevel++,_snaAgentVertex,_snaAgentCar,_aotomatonCarsPos);
				newNode._H = clacHuristic(c,((AtpProblem) problem).get_env(), 
											v, 
											((AtpProblem)problem).get_goal(),_vertex.get_neighbours().get(v));
				_children.add(newNode);
			}			
		}
	}
	
	
	
	public void PredictAotomatonBestAction(Environment env) {
		Vertex maxVer=null;		
		Car maxCar = null;
		Map<Vertex,Road> neib = _snaAgentVertex.get_neighbours();
		Set<Vertex> vertics = neib.keySet();
		Vector<Vertex> sortedVertics = new Vector<Vertex>();
				
		for(Vertex v:vertics){
			sortedVertics.add(v);
		}
		Collections.sort(sortedVertics);
		boolean found = false;		
		while((!sortedVertics.isEmpty()) && (!found)){
			maxVer = getHeavyRoad(sortedVertics,neib);		
			maxCar= _snaAgentCar;
			Car c=maxCar;
			
			if (_snaAgentVertex.get_cars().isEmpty()) {
			}
			else{
				Map<String,Car> cars = _snaAgentVertex.get_cars();
				Set<String> carsNames = cars.keySet();
				int maxSpeed = this.get_car().get_speed();
				
				for(String cName:carsNames){
					maxCar = cars.get(cName);
					if(maxCar.get_speed()>maxSpeed){
						c=maxCar;
						maxSpeed = maxCar.get_speed();
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
			if(maxCar.get_name().equals(_snaAgentCar.get_name())){						
				_snaAgentVertex= maxVer;
			}
			else{
				 
				_aotomatonCarsPos.put(_snaAgentCar,_snaAgentVertex);
				_aotomatonCarsPos.remove(maxCar);
				_snaAgentVertex = maxVer;
				_snaAgentCar = maxCar;
			}
		}
	}
	

	public Vertex getHeavyRoad(Vector<Vertex> vertics,Map<Vertex,Road> neib){
		Vertex maxVer=null;
		int maxWeight = 0;		
		Vector<Vertex> tobeRemoved = new Vector<Vertex>();
		
		for(Vertex v:vertics){							
			// if just arrived from the vertex - ignore
			if(!_route.isEmpty() && _route.get(_route.size()-1).equals(v)){
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

}
