package Algorithms;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import DO.Node;
import Tools.StringUtil;
import base.BaseNet;


public class Algorithms {

	public static Map<String,Double> EnumerationAsk(Node X, Map<Node,String> E, BaseNet bn ){			
		Map<String,Double> Q = new LinkedHashMap<String,Double>();
		for(String label: X.getLables()){
			Map<Node,String> EE = new LinkedHashMap<Node, String>(E);
			EE.put(X, label);
			Map<String,Node> vars = new LinkedHashMap<String, Node>(bn.getNodes());
			Q.put(label,EnumerateAll(vars,EE));
		}
		Q = normalize(Q);
		return Q;
	}


	private static Map<String, Double> normalize(Map<String, Double> q) {
		double acc = 0;
		for(String lable:q.keySet()){
			acc+=q.get(lable);
		}
		acc = 1/acc;
		
		for(String lable:q.keySet()){
			q.put(lable,q.get(lable)*acc);
		}
		return q;
	}

	private static double EnumerateAll(Map<String,Node> vars, Map<Node,String> E){
				
		if (vars.isEmpty()){ 
			StringUtil.println("++++++++++++++++++++++++++++++++++");
			return 1.0;
		}
		
		String Ykey = vars.keySet().iterator().next();
		
		Map<String,Node> restVars = new LinkedHashMap<String,Node>(vars);
		Node Y = restVars.remove(Ykey);
		Boolean contains = false;
		String y = null;
		
		for (Node node: E.keySet()){
			
			if (node.getName().equals(Y.getName())){
				contains  = true;
				y = E.get(node);
				break;
			}
			
			if (contains) break;	
		}
		
		List<String>  dlables;
		dlables = getDistributionRow(E, Y);
		double ans = 0;
		if (contains){
			dlables = getDistributionRow(E, Y);
			if ((dlables.size()==0)&&(Y.getParents().size()!=0)){
				StringUtil.println("++++++++++++++++++++++++++++++++++");
				return 1.0;
			}
			
			ans = Y.getDistribution(dlables, y);
			StringUtil.println("calculating: P("+Y.getName()+"="+y+"|"+printLables(Y,dlables)+") = "+ans);
			return ans*EnumerateAll(restVars,E);
		}else{
			
			for (String lable : Y.getLables())
			{
				y = lable ;
				
				if ((dlables.size()==0)&&(Y.getParents().size()!=0)){
					StringUtil.println("++++++++++++++++++++++++++++++++++");
					return 1.0;
				}
				
				Map<Node,String> EE = new LinkedHashMap<Node, String>(E);
				EE.put(Y, y);
				List<String> distRow = getDistributionRow(E, Y);
				double sans = Y.getDistribution( distRow , y);
				StringUtil.println("calculating: P("+Y.getName()+"="+y+"|"+printLables(Y,dlables)+") = "+sans);
				
				ans= ans+(sans*EnumerateAll(restVars,EE));
			}
			return ans;
		}
	}


	private static String printLables(Node y, List<String> dlables) {
		String ret = "";
		List<Node> parents = y.getParents();
		for(int i=0; i<dlables.size();i++){
			ret+=parents.get(i).toString()+"="+ dlables.get(i)+",";
		}
		return ret;
	}

	private static List<String> getDistributionRow(Map<Node, String> E, Node Y) {
		List<String>  dlables = new ArrayList<String>();
		for(Node parent : Y.getParents()){
			for(String pLable : parent.getLables()){
				for (Node enode : E.keySet()){
					if (enode.getName().equals(parent.getName()))
					if (pLable.equals(E.get(enode))){
						dlables.add(pLable);
					}//if
				}//for	
			}//for
		}//for
		return dlables;
	}
	
	
}
