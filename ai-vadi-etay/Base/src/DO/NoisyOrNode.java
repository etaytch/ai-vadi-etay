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
		
		/*
		for(List<String>  dlist : getTable().keySet()){
			
			if (dlables.size()!=dlist.size()){
				System.out.println("Unmatched parameter count!");
				continue;
			}
			
			Boolean found = true;
			for(int i=0;i<dlables.size();i++){
				if(dlables.get(i).equals(dlist.get(i))){
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
		*/
	}
	

	
	/*
	private List<String> getParentsNames(){
		List<String> ans = new ArrayList<String>();
		for(int i=0;i<getParents().size();i++){
			ans.add(getParents().get(i).getName());			
		}
		return ans;
	}
	 
	 public void print(){
		printTitle();
		//printTable();
	}
	private void printTitle() {
		String parentsDivideByTab="";
		String parentsDivideByComma="";
		List<String> parentsNames = getParentsNames();
		String Ps = "";
		
		if(parentsNames.size()>0){
			for(int i=0;i<parentsNames.size()-1;i++){
				parentsDivideByTab+=parentsNames.get(i) + "	";
				parentsDivideByComma+=parentsNames.get(i) + ",";
			}
			parentsDivideByTab+=parentsNames.get(parentsNames.size()-1);
			parentsDivideByTab+=parentsNames.get(parentsNames.size()-1);
			
			
			for(int i=0;i<getLables().size()-1;i++){
				Ps+= "P("+getName()+"="+getLables().get(i)+"|"+parentsDivideByComma+")	|";				
			}
			Ps+= "P("+getName()+"="+getLables().get(getLables().size()-1)+"|"+parentsDivideByComma+")";				
		}
		System.out.println(parentsDivideByTab + "	|	"+Ps);
	}
	*/
}
