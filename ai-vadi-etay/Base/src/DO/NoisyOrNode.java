package DO;

import java.util.List;
import java.util.Map;

public class NoisyOrNode extends Node {

	public NoisyOrNode(String name, List<String> lables, List<Node> parents,
			Map<List<String>,List<Double>> table) {
		super(name, lables, parents, table);
	}

	public NoisyOrNode(String name) {
		super(name);
	}

	public double getDistribution(List<String> dlables, String y) {
		double ans = 1.0;
		
		for(int i=0;i<dlables.size();i++){
			if(dlables.get(i).equals("f")){
				continue;
			} 
			for(List<String>  dlist : getTable().keySet()){
				if(dlables.get(i).equals(dlist.get(i))){
					ans = ans * getTable().get(dlist).get(getLables().indexOf("f"));
					break;
				}
			}			
		}
		if(y.equals("f")){
			return ans;			
		}
		else{
			return 1-ans;
		}
	}
}
