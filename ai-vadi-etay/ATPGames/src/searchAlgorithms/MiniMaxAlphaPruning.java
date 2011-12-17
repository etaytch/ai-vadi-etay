package searchAlgorithms;

import agents.Agent;
import searchAlgorithms.DecisionNodes.*;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Defs;
import simulator.Environment;
import simulator.MoveAction;
import simulator.SwitchCarAndMoveAction;
import simulator.Interfaces.Action;

public class MiniMaxAlphaPruning {
	public static Action MiniMaxDecision(Environment env,Agent a,Problem problem, GameDecisionNode gdn){
		int steps=0;		
		GameDecisionNode ans = MaxValue(env,a,problem,gdn,steps,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
		
		if (ans == null) return null;
		GameDecisionNode root = ans.getRootParent(a.get_name());
		if(root.get_dni().getMyName().equals(a.get_name())){
			Action action = root.get_dni().getMyAction();
			action.setAgent(a);
			System.out.println(action);
			return action;
		}
		else{			
			Action action = root.get_dni().getOpponentAction();
			action.setAgent(a);
			System.out.println(action);
			return action;
		}		
		/*
		if (((GameDecisionNode)ans).get_car().get_name().equals(((GameDecisionNode)gdn).get_car().get_name())){
			return (new MoveAction(a,((GameDecisionNode)ans).get_vertex()));
		}
		else{
			return (new SwitchCarAndMoveAction(a,((GameDecisionNode)ans).get_car().get_name(),((GameDecisionNode)ans).get_vertex()));
		}
		*/	
		
	}
	public static GameDecisionNode MaxValue(Environment env,Agent a,Problem problem, GameDecisionNode gdn, int steps,double alpha,double beta){
		if(TerminalTest(env,a,gdn)) {
			return gdn;
		}
		if(steps>Defs.HORIZON) {
			gdn.setPenalty(Defs.F_UNITS);
			return gdn; 
		}
		
		steps++;
		double v=Double.NEGATIVE_INFINITY;
		gdn.expand(problem);
		GameDecisionNode maxDecisionNode=null;
		for(DecisionNode child : gdn.get_children()){
			GameDecisionNode tmpDN = MinValue(env,a,problem, (GameDecisionNode)child,steps,alpha,beta);
			//System.out.println("tmpDN.getValue(): "+tmpDN.getValue()+", v: "+v);
			if (tmpDN.getValue()>v){
				System.out.println("MAX_VAL: Updateing max val from "+v+" to: "+tmpDN.getValue());
				v = tmpDN.getValue();
				maxDecisionNode = (GameDecisionNode)tmpDN;
			}
			/*
			if (v>=beta){
				System.out.println("beta pruning");
				return maxDecisionNode;				
			}
			alpha = Math.max(alpha, v);
			*/
		}
		return maxDecisionNode;
	} 

	public static GameDecisionNode MinValue(Environment env,Agent a,Problem problem, GameDecisionNode gdn, int steps,double alpha,double beta){
		if(TerminalTest(env,a,gdn)) {
			return gdn;
		}
		if(steps>Defs.HORIZON) {
			gdn.setPenalty(Defs.F_UNITS);
			return gdn; 
		}
		steps++;
		double v=Double.POSITIVE_INFINITY;
		gdn.expand(problem);
		GameDecisionNode maxDecisionNode=null;
		int childCounter=0;
		for(DecisionNode child : gdn.get_children()){
			childCounter++;
			GameDecisionNode tmpDN = MaxValue(env,a,problem, (GameDecisionNode)child, steps, alpha, beta);
			if (tmpDN.getValue()<v){
				System.out.println("MIN_VAL: Updateing min val from "+v+" to: "+tmpDN.getValue());
				v = tmpDN.getValue();
				maxDecisionNode = tmpDN;
			}
			/*
			if(v<=alpha){				
				System.out.println("alpha pruning with v="+v+", alpha="+alpha+", children killed: "+(gdn.get_children().size()-childCounter));
				return maxDecisionNode;				
			}
			beta = Math.min(beta, v);
			*/			
		} 
		return maxDecisionNode;
	} 

	private static boolean TerminalTest(Environment env,Agent a,GameDecisionNode gdn) {
		if (gdn==null) return false;

		// Max agent has not reached his goal
		if(gdn.get_dni().getMyGoal() != gdn.get_dni().getMyVertex().get_number() ){		
				return false;
		}
		// Min agent has not reached his goal
		if(gdn.get_dni().getOpponentGoal() != gdn.get_dni().getOpponentVertex().get_number() ){		
			return false;
		}
		double score = gdn.get_dni().getResult(a.get_name());
		System.out.println("reached a leaf with score = "+score);
		gdn.setValue(score);					
		return true;
	}	
}
