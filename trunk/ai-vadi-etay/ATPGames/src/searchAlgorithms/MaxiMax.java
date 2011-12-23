
package searchAlgorithms;


import agents.Agent;
import searchAlgorithms.DecisionNodes.*;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Defs;
import simulator.Environment;
import simulator.Interfaces.Action;

public class MaxiMax {
	public static Action MaxiMaxDecision(Environment env,Agent a, Agent human,Problem problem, MaxiMaxGameDecisionNode gdn){
		MaxiMaxGameDecisionNode root = gdn;
		root._id = Defs.MAX_ROOT_ID;
		System.out.println("Starting point:");
		System.out.println(root);
		int steps=0;
		int turn=0;
		MaxiMaxGameDecisionNode ans = MaxValue(env,a,human,problem,root,steps,turn);		
		if (ans == null) {
			System.out.println("ans is null!");
			return null;
		}
		MaxiMaxGameDecisionNode maxNode = ans.getMaxNode(root,ans);
		if(maxNode==null) {
			System.out.println("maxNode is null!");
			return null;
		}
		Action action = maxNode._action;
		action.setAgent(a);
		System.out.println(action);
		return action;
	}
	public static MaxiMaxGameDecisionNode MaxValue(Environment env,Agent a,Agent human,Problem problem, MaxiMaxGameDecisionNode gdn, int steps,int turn){
		turn++;
		if(TerminalTest(env,a,gdn,turn)) {
			return gdn;
		}
		if(steps>Defs.CUTOFF) {
			gdn._value = calcHuristic(gdn,turn); 
			return gdn; 
		}
		
		steps++;
		double v=Double.NEGATIVE_INFINITY;
		gdn.expand(problem,a,human,turn);
		MaxiMaxGameDecisionNode maxDecisionNode=null;
		for(DecisionNode child : gdn.get_children()){
			MaxiMaxGameDecisionNode tmpDN = MaxValue(env,a,human,problem, (MaxiMaxGameDecisionNode)child,steps,turn);
			if (tmpDN._mutualValue[turn%2]>v){
				System.out.println("MAX_VAL: Updateing max val from "+v+" to: "+tmpDN.getValue());
				v = tmpDN._mutualValue[turn%2];
				maxDecisionNode = tmpDN;
			}
		}
		return maxDecisionNode;
	} 

	public static double calcHuristic(GameDecisionNode gdn, int turn )
	{
 
		if (turn%2==1) //comp
		{
			return gdn._humanTime - gdn._compTime;
		}
		else //human
		{
			return gdn._compTime - gdn._humanTime;

		}
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
		double score = 0; 
		if (turn%2==1) //comp
		{
			score = gdn._humanTime - gdn._compTime;
			gdn._mutualValue[Defs.GTS-1] = score;
			System.out.println("reached a computr leaf with scores = "+score);
		}
		else //human
		{
			score = gdn._compTime - gdn._humanTime;
			gdn._mutualValue[Defs.HUMAN-1] = score;
			System.out.println("reached a human leaf with scores = "+score);
		}
		
		
		return true;
	}	
}
