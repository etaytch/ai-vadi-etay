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
			Q.put(label,EnumerateAll(bn.getNodes(),EE));
		}
	
		
		return null;
	}


	private static double EnumerateAll(Map<String,Node> vars, Map<Node,String> EE){
				
		return 0;
		
	}
	
	
}
