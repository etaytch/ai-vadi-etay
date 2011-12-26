
package searchAlgorithms;


import generalAlgorithms.Dijkstra;
import generalAlgorithms.Graph;
import generalAlgorithms.Node;

import java.util.ArrayList;

import agents.Agent;
import searchAlgorithms.DecisionNodes.*;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.Defs;
import simulator.Environment;
import simulator.Road;
import simulator.Vertex;
import simulator.Interfaces.Action;
import tools.ATPLogger;

public class MaxiMax {
	public static Action MaxiMaxDecision(Environment env,Agent a, Agent human,Problem problem, MaxiMaxGameDecisionNode gdn){
		MaxiMaxGameDecisionNode root = gdn;
		root._id = Defs.MAX_ROOT_ID;
		Defs.print("Starting point:");
		Defs.print(root.toString());
		int steps=0;
		int turn=0;
		MaxiMaxGameDecisionNode ans = MaxValue(env,a,human,problem,root,steps,turn);		
		if (ans == null) {
			//System.out.println("ans is null!");
			return null;
		}
		MaxiMaxGameDecisionNode maxNode = ans.getMaxNode(root,ans);
		if(maxNode==null) {
			//System.out.println("maxNode is null!");
			return null;
		}
		ATPLogger.log("Selected score: "+ans._mutualValue[Defs.GTS-1]);
		
		Action action = maxNode._action;
		action.setAgent(a);
		ATPLogger.log(action.toString());
		return action;
	}
	public static MaxiMaxGameDecisionNode MaxValue(Environment env,Agent a,Agent human,Problem problem, MaxiMaxGameDecisionNode gdn, int steps,int turn){
		turn++;
		if(TerminalTest(env,a,gdn,turn)) {
			return gdn;
		}
		if(steps>Defs.CUTOFF) {					
			gdn._mutualValue[Defs.GTS-1] = -(gdn._compTime + gdn.clacHuristic(gdn._compCar , env, gdn._compVertex, gdn._compGoal));
			gdn._mutualValue[Defs.HUMAN-1] = -(gdn._humanTime + gdn.clacHuristic(gdn._humanCar , env, gdn._humanVertex, gdn._humanGoal));
			Defs.print("CUTTOFF: ["+gdn._mutualValue[Defs.GTS-1]+","+gdn._mutualValue[Defs.HUMAN-1]+"]");
			return gdn; 
		}
		
		steps++;
		double v=Double.NEGATIVE_INFINITY;
		gdn.expand(problem,a,human,turn);
		MaxiMaxGameDecisionNode maxDecisionNode=null;
//		if(gdn.get_children().isEmpty()){
//			gdn._mutualValue[Defs.GTS-1] = Double.NEGATIVE_INFINITY;
//			gdn._mutualValue[Defs.HUMAN-1] = Double.NEGATIVE_INFINITY;
//		}
		for(DecisionNode child : gdn.get_children()){
			MaxiMaxGameDecisionNode tmpDN = MaxValue(env,a,human,problem, (MaxiMaxGameDecisionNode)child,steps,turn);
			if(tmpDN == null) continue;
			if (tmpDN._mutualValue[turn%2]>v){
				Defs.print("MAX_VAL: Updateing max val from "+v+" to: "+tmpDN._mutualValue[turn%2]);
				v = tmpDN._mutualValue[turn%2];
				maxDecisionNode = tmpDN;
			}
		}
		return maxDecisionNode;
	} 
	

	private static boolean TerminalTest(Environment env,Agent a,MaxiMaxGameDecisionNode gdn,int turn) {
		if (gdn==null) return false;		

		// Max agent has not reached his goal
		if(!gdn._compGoal.equals(gdn._compVertex)){		
				return false;
		}
		// Min agent has not reached his goal
		if(!gdn._humanGoal.equals(gdn._humanVertex)){		
			return false;
		}
		
		double score1 = -gdn._compTime;
		gdn._mutualValue[Defs.GTS-1] = score1;
		Defs.print("reached a computr leaf with scores = "+score1+". Current values: ["+gdn._mutualValue[Defs.GTS-1]+","+gdn._mutualValue[Defs.HUMAN-1]+"]");
	
		double score2 = -gdn._humanTime;
		gdn._mutualValue[Defs.HUMAN-1] = score2;			
		Defs.print("reached a human leaf with scores = "+score2+". Current values: ["+gdn._mutualValue[Defs.GTS-1]+","+gdn._mutualValue[Defs.HUMAN-1]+"]");
	
			
		
		
		return true;
	}	
}
