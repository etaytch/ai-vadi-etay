package searchAlgorithms;

import agents.Agent;
import searchAlgorithms.DecisionNodes.*;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Defs;
import simulator.Environment;
import simulator.Interfaces.Action;

public class MiniMaxAlphaPruning {
	public static Action MiniMaxDecision(Environment env,Agent a, Agent human,Problem problem, GameDecisionNode gdn){
		GameDecisionNode root = gdn;
		root._id = Defs.MAX_ROOT_ID;
		System.out.println("Starting point:");
		System.out.println(root);
		int steps=0;
		int turn=0;
		GameDecisionNode ans = MaxValue(env,a,human,problem,root,steps,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,turn);		
		if (ans == null) {
			System.out.println("ans is null!");
			return null;
		}
		GameDecisionNode maxNode = ans.getMaxNode(root,ans);
		if(maxNode==null) {
			System.out.println("maxNode is null!");
			return null;
		}
		Action action = maxNode._action;
		action.setAgent(a);
		System.out.println(action);
		return action;
	}
	public static GameDecisionNode MaxValue(Environment env,Agent a,Agent human,Problem problem, GameDecisionNode gdn, int steps,double alpha,double beta,int turn){
		turn++;
		if(TerminalTest(env,a,gdn)) {
			return gdn;
		}
		if(steps>Defs.CUTOFF) {
			//gdn.setPenalty(Defs.F_UNITS);
			//TODO:
			gdn._value = calcHuristic(gdn); 
			return gdn; 
		}
		
		steps++;
		double v=Double.NEGATIVE_INFINITY;
		gdn.expand(problem,a,human,turn);
		GameDecisionNode maxDecisionNode=null;
		for(DecisionNode child : gdn.get_children()){
			GameDecisionNode tmpDN = MinValue(env,a,human,problem, (GameDecisionNode)child,steps,alpha,beta,turn);
			//System.out.println("tmpDN.getValue(): "+tmpDN.getValue()+", v: "+v);
			if (tmpDN.getValue()>v){
				System.out.println("MAX_VAL: Updateing max val from "+v+" to: "+tmpDN.getValue());
				v = tmpDN.getValue();
				maxDecisionNode = (GameDecisionNode)tmpDN;
			}
			if (v>=beta){
				System.out.println("beta pruning");
				return maxDecisionNode;				
			}
			alpha = Math.max(alpha, v);
		}
		return maxDecisionNode;
	} 

	public static double calcHuristic(GameDecisionNode gdn)
	{
		return (gdn._humanTime-gdn._compTime);
	}
	
	public static GameDecisionNode MinValue(Environment env,Agent a,Agent human,Problem problem, GameDecisionNode gdn, int steps,double alpha,double beta,int turn){
		turn++;
		if(TerminalTest(env,a,gdn)) {
			return gdn;
		}
		
		if(steps>Defs.CUTOFF) {
			//gdn.setPenalty(Defs.F_UNITS);
			//TODO:
			gdn._value = calcHuristic(gdn); 
			return gdn; 
		}
		
		steps++;
		double v=Double.POSITIVE_INFINITY;
		gdn.expand(problem,a,human,turn);
		GameDecisionNode maxDecisionNode=null;
		int childCounter=0;
		for(DecisionNode child : gdn.get_children()){
			childCounter++;
			GameDecisionNode tmpDN = MaxValue(env,a,human,problem, (GameDecisionNode)child, steps, alpha, beta,turn);
			if (tmpDN.getValue()<v){
				System.out.println("MIN_VAL: Updateing min val from "+v+" to: "+tmpDN.getValue());
				v = tmpDN.getValue();
				maxDecisionNode = tmpDN;
			}
			if(v<=alpha){				
				System.out.println("alpha pruning with v="+v+", alpha="+alpha+", children killed: "+(gdn.get_children().size()-childCounter));
				return maxDecisionNode;				
			}
			beta = Math.min(beta, v);		
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
		System.out.println("reached a leaf with score = "+score);
		gdn.setValue(score);					
		return true;
	}	
}
