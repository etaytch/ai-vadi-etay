package DO;

import java.util.List;
import java.util.Map;

public abstract class Node {
	private String name;
	private List<String> titles;
	private Map<List<String>,Double> table;

	public double value;
	
	public Node(String name,List<String> titles,Map<List<String>,Double> table){
		this.name = name;
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
}
