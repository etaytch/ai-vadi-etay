package agents;

import java.util.HashMap;

import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import searchAlgorithms.DecisionNodes.SpeedNutPredictionDecisionNode;
import simulator.Car;
import simulator.Vertex;

public class SpeedNutPredictionAgent extends AstarAgent {

	SpeedNutAutomationAgent _SNAagent; 
	public SpeedNutPredictionAgent(String name, Vertex initPosition,
			Vertex goalPosition, Car car , SpeedNutAutomationAgent a) {
		super(name, initPosition, goalPosition, car);
		_SNAagent = a;
		
	}
	
	@Override
	public AtpDecisionNode getInitNode() {
		return new SpeedNutPredictionDecisionNode(_initPosition, _car, null, null,0,_SNAagent.get_vertex(),_SNAagent.get_car(),new HashMap<Car,Vertex>());
	}
}
