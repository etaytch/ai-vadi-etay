package DO;

import java.util.ArrayList;
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
