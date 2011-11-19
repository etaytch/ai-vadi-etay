package agents;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

import searchAlgorithms.AtpDecisionNode;
import searchAlgorithms.DecisionNode;
import simulator.Car;
import simulator.MoveAction;
import simulator.Simulator;
import simulator.SwitchCarAndMoveAction;
import simulator.Vertex;
import tools.ATPLogger;

public class HumanAgent extends Agent {



	public HumanAgent(String name, Vertex initPosition, Vertex goalPosition,
			Car car) {
		super(name, initPosition, goalPosition, car);
	}

	@Override
	public void chooseBestAction(Simulator sim) {
		 Vertex futurePosition = null;
		 String carName = null;
		 
		 futurePosition = whereToMove(sim);
		 carName = switchCar();
		 
		 if (carName!=null){
			 get_actions().offer(new SwitchCarAndMoveAction(this, carName, futurePosition));
		 }
		 else{
			 get_actions().offer(new MoveAction(this, futurePosition));
		 }
	}

	private String switchCar() {
		String futureCar = null;
		if (get_vertex().get_cars().isEmpty()) return null;
		
		System.out.print("Do you want to switch your car?\n"+
		 				  "now you have: "+get_car()+
		 				  "\nyou can switch to: "+get_vertex().carsToString()+
		 				  "\npress the name of the car you want to choose or Enter to continue: ") ;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 try {
			 futureCar = br.readLine();
	         if (futureCar==null || futureCar.equals("")) return null; 
	         
		 } catch (IOException ioe) {
	         ATPLogger.log("IO error!");
	         System.exit(1);
	      } catch (Exception ioe) {
		         ATPLogger.log("error accured!");
		         System.exit(1);
	      }
	      
	      return futureCar;
	}

	private Vertex whereToMove(Simulator sim) {
		Vertex futurePosition = null;
		System.out.print("\n"+get_name()+", it is your turn!\n" +
		 					"you are at Vertex "+get_vertex().get_number()+
		 				    ",\nyou driving: "+get_car()+
		 				    "\nto witch Vertex do you want to move? ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		 try {
	         int newVertexNumber = Integer.parseInt(br.readLine());
	         futurePosition = sim.getVertex(newVertexNumber);
	      
		 } catch (IOException ioe) {
	         ATPLogger.log("IO error!");
	         System.exit(1);
	      } catch (Exception ioe) {
		         ATPLogger.log("error accured!");
		         System.exit(1);
	      }
	      
	      return futurePosition;
	}

	@Override
	public AtpDecisionNode getInitNode() {
		// TODO Auto-generated method stub
		return null;
	}


}
