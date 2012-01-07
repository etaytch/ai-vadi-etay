package DO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Node {
	private String name;
	private List<String> lables;
	private Map<List<String>,List<Double>> table;
	private List<Node> parents;
	public double value;
	
	public Node(String name,List<String> lables,List<Node> parents,Map<List<String>,List<Double>> table){
		this.name = name;
		this.setParents(parents);
		this.setLables(lables);
		this.setTable(table);
		value = 0.0;
	}
	
	public Node(String name){
		this.name = name;
		this.setParents(new ArrayList<Node>());
		this.setLables(new ArrayList<String>());
		this.setTable(new HashMap<List<String>,List<Double>>());
		value = 0.0;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	public void setLables(List<String> lables) {
		this.lables = lables;
	}

	public List<String> getLables() {
		return lables;
	}

	public void setTable(Map<List<String>,List<Double>> table) {
		this.table = table;
	}

	public Map<List<String>,List<Double>> getTable() {
		return table;
	}

	public void setParents(List<Node> parents) {
		this.parents = parents;
	}

	public List<Node> getParents() {
		return parents;
	}

	public void addParent(Node nParent) {
		parents.add(nParent);
		
	}

	public double getDistribution(List<String> dlables, String y) {
		for(List<String>  dlist : table.keySet()){
			
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
				return table.get(dlist).get(lables.indexOf(y)); 	
			}
		}
		System.out.println("SOMTHING HORRIBLE HAPPEND IN getDistribution METHOD IN node CLASS!!!!!!\n " +
						   "Couldn't find the row needed eow in the distribution table!\n" +
						   "will now exit!");	
		System.exit(-1);
		return 0.0;
	}	
}
