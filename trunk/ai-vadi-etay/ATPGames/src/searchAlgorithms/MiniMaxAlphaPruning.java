package searchAlgorithms;

import java.util.Vector;

import agents.Agent;
import searchAlgorithms.DecisionNodes.*;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Defs;
import simulator.Environment;
import simulator.Interfaces.Action;
import tools.ATPLogger;

public class MiniMaxAlphaPruning {
	//public static Vector<GameDecisionNode> lookUpTable = new Vector<GameDecisionNode>();
	
	public static Action MiniMaxDecision(Environment env,Agent a, Agent human,Problem problem, GameDecisionNode gdn){
		GameDecisionNode root = gdn;
		root._id = Defs.MAX_ROOT_ID;
		Defs.print("Starting point:");
		Defs.print(root.toString());
		int steps=0;
		int turn=0;
		//MiniMaxAlphaPruning.lookUpTable.add(gdn);
		GameDecisionNode ans = MaxValue(env,a,human,problem,root,steps,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,turn);		
		if (ans == null) {
			//Defs.print("ans is null!");
			return null;
		}
		GameDecisionNode maxNode = ans.getMaxNode(root,ans);
		if(maxNode==null) {
			//Defs.print("maxNode is null!");
			return null;
		}
		ATPLogger.log("Selected score: "+ans._value);
		
		Action action = maxNode._action;
		action.setAgent(a);
		ATPLogger.log(action.toString());
		return action;
	}
	public static GameDecisionNode MaxValue(Environment env,Agent a,Agent human,Problem problem, GameDecisionNode gdn, int steps,double alpha,double beta,int turn){
		turn++;
		if(TerminalTest(env,a,gdn)) {
			return gdn;
		}
		if(steps>Defs.CUTOFF) {			
			double humanHuristic = gdn.clacHuristic(gdn._compCar , env, gdn._compVertex, gdn._compGoal);
			double compHuristic = gdn.clacHuristic(gdn._humanCar , env, gdn._humanVertex, gdn._humanGoal);
			gdn._value = (gdn._humanTime-gdn._compTime) + (humanHuristic - compHuristic); 			
			return gdn; 
		}
		
		steps++;
		double v=Double.NEGATIVE_INFINITY;
		
		gdn.expand(problem,a,human,turn);
		GameDecisionNode maxDecisionNode=null;
		for(DecisionNode child : gdn.get_children()){
			GameDecisionNode childGDN = (GameDecisionNode)child;
		
			GameDecisionNode tmpDN = MinValue(env,a,human,problem, (GameDecisionNode)child,steps,alpha,beta,turn);
			//Defs.print("tmpDN.getValue(): "+tmpDN.getValue()+", v: "+v);
			if(tmpDN!=null){
				if (tmpDN.getValue()>v){
					Defs.print("MAX_VAL: Updateing max val from "+v+" to: "+tmpDN.getValue());
					v = tmpDN.getValue();
					maxDecisionNode = (GameDecisionNode)tmpDN;
				}
				
				if (v>=beta){
					Defs.print("beta pruning");
					return maxDecisionNode;				
				}
				alpha = Math.max(alpha, v);
				
			}
		}
		return maxDecisionNode;
	} 
	

	
	public static GameDecisionNode MinValue(Environment env,Agent a,Agent human,Problem problem, GameDecisionNode gdn, int steps,double alpha,double beta,int turn){
		turn++;
		if(TerminalTest(env,a,gdn)) {
			return gdn;
		}
		
		if(steps>Defs.CUTOFF) {			
			double humanHuristic = gdn.clacHuristic(gdn._compCar , env, gdn._compVertex, gdn._compGoal);
			double compHuristic = gdn.clacHuristic(gdn._humanCar , env, gdn._humanVertex, gdn._humanGoal);
			gdn._value = (gdn._humanTime-gdn._compTime) + (humanHuristic - compHuristic); 			
			return gdn;  
		}
		
		steps++;
		double v=Double.POSITIVE_INFINITY;
		
		gdn.expand(problem,a,human,turn);
		GameDecisionNode maxDecisionNode=null;
		int childCounter=0;
		for(DecisionNode child : gdn.get_children()){
			GameDecisionNode childGDN = (GameDecisionNode)child;
			childCounter++;
			
			
			GameDecisionNode tmpDN = MaxValue(env,a,human,problem, (GameDecisionNode)child, steps, alpha, beta,turn);
			if(tmpDN!=null){
				if (tmpDN.getValue()<v){
					Defs.print("MIN_VAL: Updateing min val from "+v+" to: "+tmpDN.getValue());
					v = tmpDN.getValue();
					maxDecisionNode = tmpDN;
				}
				
				if(v<=alpha){				
					Defs.print("alpha pruning with v="+v+", alpha="+alpha+", children killed: "+(gdn.get_children().size()-childCounter));
					return maxDecisionNode;				
				}
				beta = Math.min(beta, v);
				
			}
		} 
		return maxDecisionNode;
	} 

	private static boolean TerminalTest(Environment env,Agent a,GameDecisionNode gdn) {
		if (gdn==null) return false;		

		// Max agent has not reached his goal
		if(!gdn._compGoal.equals(gdn._compVertex)){		
				return false;
		}
		// Min agent has not reached his goal
		if(!gdn._humanGoal.equals(gdn._humanVertex)){		
			return false;
		}
		double score = gdn._humanTime - gdn._compTime;
		Defs.print("reached a leaf with score = "+score);
		gdn.setValue(score);					
		return true;
	}	
	
		
	
}
