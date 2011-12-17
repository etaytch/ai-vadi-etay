package searchAlgorithms;

import agents.Agent;
import searchAlgorithms.DecisionNodes.*;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Defs;
import simulator.MoveAction;
import simulator.SwitchCarAndMoveAction;
import simulator.Interfaces.Action;

public class MiniMax {
	/*
	public static Action MiniMaxDecision(Agent a,Problem problem, DecisionNode gdn){
		int steps=0;
		DecisionNode ans = MaxValue(problem,gdn,steps);		
		if (ans == null) return null;
		if (((SumZeroGameDecisionNode)ans).get_car().get_name().equals(((SumZeroGameDecisionNode)gdn).get_car().get_name())){
			return (new MoveAction(a,((SumZeroGameDecisionNode)ans).get_vertex()));
		}
		else{
			return (new SwitchCarAndMoveAction(a,((SumZeroGameDecisionNode)ans).get_car().get_name(),((SumZeroGameDecisionNode)ans).get_vertex()));
		}
		
	}
	public static DecisionNode MaxValue(Problem problem, DecisionNode gdn, int steps){
		if(TerminalTest(gdn)) return gdn;
		if(steps>Defs.HORIZON) {
			//
		}
		
		steps++;
		double v=Double.MIN_VALUE;
		gdn.expand(problem);
		DecisionNode maxDecisionNode=null;
		for(DecisionNode child : gdn.get_children()){
			DecisionNode tmpDN = MinValue(problem, child,steps);
			if (tmpDN.getValue()>v){
				v = tmpDN.getValue();
				maxDecisionNode = tmpDN;
			}
		}
		return maxDecisionNode;
	} 
	private static boolean TerminalTest(DecisionNode gdn) {
		if (gdn==null) return false;
		return false;
	}
	public static DecisionNode MinValue(Problem problem, DecisionNode gdn, int steps){
		if(TerminalTest(gdn)) return gdn;
		if(steps>Defs.HORIZON) {
			//
		}
		steps++;
		double v=Double.MAX_VALUE;
		gdn.expand(problem);
		DecisionNode maxDecisionNode=null;
		for(DecisionNode child : gdn.get_children()){
			DecisionNode tmpDN = MaxValue(problem, child,steps);
			if (tmpDN.getValue()<v){
				v = tmpDN.getValue();
				maxDecisionNode = tmpDN;
			}
		}
		return maxDecisionNode;
	} 
	 */
	
}
