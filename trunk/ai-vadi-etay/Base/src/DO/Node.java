package DO;

import java.util.List;
import java.util.Map;

public abstract class Node {
	private String name;
	private List<String> titles;
	private Map<List<String>,Double> table;
	private List<Node> parents;
	public double value;
	
	public Node(String name,List<String> titles,List<Node> parents,Map<List<String>,Double> table){
		this.name = name;
		this.setParents(parents);
		this.setTitles(titles);
		this.setTable(table);
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

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTable(Map<List<String>,Double> table) {
		this.table = table;
	}

	public Map<List<String>,Double> getTable() {
		return table;
	}

	public void setParents(List<Node> parents) {
		this.parents = parents;
	}

	public List<Node> getParents() {
		return parents;
	}
}
