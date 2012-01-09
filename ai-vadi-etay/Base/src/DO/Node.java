package DO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Tools.StringUtil;

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
		this.setTable(new LinkedHashMap<List<String>,List<Double>>());
		value = 0.0;
	}
	
	public String getName() {
		return name;
	}
	
//	public List<List<String>> producePermutations(){
//		List<List<String>> ans = new ArrayList<List<String>>();
//		
//		
//		
//	}
	
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
	
	public abstract double getDistribution(List<String> dlables, String y);

	public String getBorder(int weigth, String str){
		String ans="";
		for(int i=0;i<weigth;i++)
			ans+=str;
		return ans;
	}
	
	public void print(){
		String title = getTitle();
		String border = getBorder(title.length(),StringUtil.STAR);
		String seperator = StringUtil.STAR+getBorder(title.length()-2,"-")+StringUtil.STAR;
		System.out.println(border);			
		System.out.println(title);
		System.out.println(seperator);	
		System.out.println(getTableStr());
		System.out.println(border);
		//printTable();
	}
	
	public String getTableStr(){
		
		Set<List<String>> keys = getTable().keySet();
		Iterator<List<String>> itr = keys.iterator(); 
		String rows="";
		while(itr.hasNext()) {
			String assigns = "";
			String dbls="";
			List<String> pars = itr.next();
			if(pars.isEmpty()){
				List<Double> dists = getTable().get(pars);
				for(int i=0;i<dists.size()-1;i++){
					dbls+= StringUtil.Pad(""+dists.get(i))+"|";				
				}
				dbls+= StringUtil.Pad(""+dists.get(dists.size()-1));			
				return StringUtil.STAR +dbls+StringUtil.STAR;
			}	
			else{
				for(int i=0;i<pars.size()-1;i++){
					assigns+= StringUtil.Pad(""+pars.get(i))+"|";				
				}
				assigns+= StringUtil.Pad(""+pars.get(pars.size()-1));
				
				List<Double> dists = getTable().get(pars);
				for(int i=0;i<dists.size()-1;i++){
					dbls+= StringUtil.Pad(""+dists.get(i))+"|";				
				}
				dbls+= StringUtil.Pad(""+dists.get(dists.size()-1));
				
				rows +=StringUtil.STAR + assigns +"||" + dbls+StringUtil.STAR+"\n";				
			} 				
		} 					
		return rows;
	}

	private List<String> getParentsNames(){
		List<String> ans = new ArrayList<String>();
		for(int i=0;i<getParents().size();i++){
			ans.add(getParents().get(i).getName());			
		}
		return ans;
	}
	public String toString(){
		return name;
	}
	
	private String getTitle() {
		String parentsDivideByTab="";
		String parentsDivideByComma="";
		List<String> parentsNames = getParentsNames();
		String Ps = "";
		
		if(parentsNames.size()>0){
			for(int i=0;i<parentsNames.size()-1;i++){
				parentsDivideByTab+=StringUtil.Pad(parentsNames.get(i)) + "|";
				parentsDivideByComma+=parentsNames.get(i) + ",";
			}
			parentsDivideByTab+=StringUtil.Pad(parentsNames.get(parentsNames.size()-1));
			parentsDivideByComma+=parentsNames.get(parentsNames.size()-1);
			
			
			for(int i=0;i<getLables().size()-1;i++){
				Ps+= StringUtil.Pad("P("+getName()+"="+getLables().get(i)+"|"+parentsDivideByComma+")")+"|";				
			}
			Ps+= StringUtil.Pad("P("+getName()+"="+getLables().get(getLables().size()-1)+"|"+parentsDivideByComma+")");
			return StringUtil.STAR +parentsDivideByTab + "||"+Ps+ StringUtil.STAR;
		}
		else{
			for(int i=0;i<getLables().size()-1;i++){
				Ps+= StringUtil.Pad("P("+getName()+"="+getLables().get(i)+")")+ "|";				
			}
			Ps+= StringUtil.Pad("P("+getName()+"="+getLables().get(getLables().size()-1)+")");			
			return StringUtil.STAR +Ps+ StringUtil.STAR;
		}				
	}
	
}
