package agents;

import graph.searchGraph.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import searchAlgorithms.AtpDecisionNode;
import searchAlgorithms.DecisionNode;
import simulator.Car;
import simulator.MoveAction;
import simulator.Simulator;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import tools.ATPLogger;

public class SimpleGreedyAgent extends Agent {	

	public SimpleGreedyAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);	
		
	}

	@Override
	public void chooseBestAction(Simulator env) {				
		ArrayList<Node> result = new ArrayList<Node>();
		env.calcPath(get_vertex().get_number(),get_goalPosition().get_number(),result);		
		//ATPLogger.log("Dijkstra result from "+get_vertex().get_number()+" to "+get_goalPosition().get_number()+":\n"+result);		
		if(result.isEmpty()){
			set_state("stuck","Could not find a path to goal!");
			return;			
		}
		else{
			int nextVerNumber = result.get(1).getId();	// result.get(0) = from_node
			
			Set<Vertex> vertexs = get_vertex().get_neighbours().keySet();
			Vertex nextVer=null;			
			for(Vertex v:vertexs){
				if(v.get_number() == nextVerNumber){
					nextVer = v;
					break;
				}
			}		
			String maxCarName = this.get_car().get_name();
			if (get_vertex().get_cars().isEmpty()) {
				ATPLogger.log("Vertex has no cars!.. "+get_vertex()+"Keeping current car: "+maxCarName);						
			}
			else{
				Map<String,Car> cars = get_vertex().get_cars();
				Set<String> carsNames = cars.keySet();
				int maxSpeed = get_car().get_speed();
				
				for(String cName:carsNames){
					Car c = cars.get(cName);
					if(c.get_speed()>maxSpeed){
						maxSpeed = c.get_speed();
						maxCarName = cName;
					}						
				}			
			}
			if(maxCarName.equals(get_car().get_name())){				
				ATPLogger.log("Agent "+get_name()+" chose to keep original car. "+get_car());
				get_actions().offer(new MoveAction(this, nextVer));
			}
			else{
				get_actions().offer(new SwitchCarAndMoveAction(this, maxCarName, nextVer));
			}
		}			
	}
	@Override
	public AtpDecisionNode getInitNode() {
		return null;
	}

}
