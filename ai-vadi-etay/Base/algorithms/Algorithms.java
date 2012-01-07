import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DO.Node;
import base.BaseNet;


public class Algorithms {

	public static Map<String,Double> EnumerationAsk(Node X, Map<Node,String> E, BaseNet bn ){
				
		Map<String,Double> Q = new HashMap<String,Double>();
		for(String label: X.getLables()){
			Map<Node,String> EE = new HashMap<Node, String>(E);
			EE.put(X, label);
			Map<String,Node> vars = new HashMap<String, Node>(bn.getNodes());
			Q.put(label,EnumerateAll(vars,EE));
		}
		return Q;
	}


	private static double EnumerateAll(Map<String,Node> vars, Map<Node,String> E){
				
		if (vars.isEmpty()) 
			return 1.0;
		
		String Ykey = vars.keySet().iterator().next();
		Node Y = vars.remove(Ykey);
		Map<String,Node> restVars = new HashMap<String,Node>(vars);
		Boolean contains = false;
		String y = null;
		for (Node node: E.keySet()){
			for (String lable: Y.getLables()){
				if (E.get(node).equals(lable)){
					contains  = true;
					y = lable;
					break;
				}
			}
			if (contains) break;	
		}
		
		List<String>  dlables;
		double ans = 0;
		if (contains){
			dlables = getDistributionRow(E, Y);
			ans = Y.getDistribution(dlables, y);
			return ans*EnumerateAll(restVars,E);
		}else{
			
			for (String lable : Y.getLables())
			{
				Map<Node,String> EE = new HashMap<Node, String>(E);
				EE.put(Y, lable);
				ans+= Y.getDistribution( getDistributionRow(E, Y), lable)*
					  EnumerateAll(restVars,EE);
			}
			return ans;
		}
	}


	private static List<String> getDistributionRow(Map<Node, String> E, Node Y) {
		List<String>  dlables = new ArrayList<String>();
		for(Node parent : Y.getParents()){
			for(String pLable:parent.getLables()){
				for (Node enode : E.keySet()){
					if (pLable.equals(E.get(enode))){
						dlables.add(pLable);
					}//if
				}//for	
			}//for
		}//for
		return dlables;
	}
	
	
}
