package DO;

import java.util.List;
import java.util.Map;

public class NormalNode extends Node{

	public NormalNode(String name, List<String> lables, List<Node> parents,
			Map<List<String>,List<Double>> table) {
		super(name, lables, parents, table);
	}

	public NormalNode(String name) {
		super(name);
	}
	
	public double getDistribution(List<String> dlables, String y) {
		
		for(List<String>  dlist : getTable().keySet()){
			
			if (dlables.size()!=dlist.size()){
				continue;
			}
			
			Boolean found = true;
			for(int i=0;i<dlables.size();i++){
				if(!dlables.get(i).equals(dlist.get(i))){
					found = false;
				}
			}
			
			if (found){
				return getTable().get(dlist).get(getLables().indexOf(y)); 	
			}
		}
		System.out.println("SOMTHING HORRIBLE HAPPEND IN getDistribution METHOD IN node CLASS!!!!!!\n " +
						   "Couldn't find the row needed in the distribution table!");	
		return 1.0;
	}
}
