package agents;

import java.util.LinkedHashMap;
import simulator.Car;
import simulator.Road;
import simulator.Vertex;

public class BeliefStateNode {

	public LinkedHashMap<Road,Double> _probRoads;
	public Vertex _srcVertex;
	public Vertex _dstVertex;
	public Car _agentCar;
	public Car _otherCar;
	
	
	public BeliefStateNode(Vertex srcVertex,Vertex dstVertex,Car agentCar,Car otherCar){
		
		_probRoads = new LinkedHashMap<Road,Double>() ;
		_agentCar = agentCar;
		_otherCar = otherCar;
		_srcVertex = srcVertex;
		_dstVertex = dstVertex;
		
	}
	
	public void AddProbRoad(Road r, double p)
	{
		_probRoads.put(r,p);
	}
	
	
}
