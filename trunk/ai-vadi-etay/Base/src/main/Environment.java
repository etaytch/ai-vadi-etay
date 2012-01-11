package main;
import java.util.Map;

import DO.Node;
import base.BaseNet;


public class Environment {
	
	private BaseNet bn;
	
	public Environment(BaseNet bn){
		this.bn = bn;
	}
	
	public BaseNet getBn() {
		return bn;
	}
	public void setBn(BaseNet bn) {
		this.bn = bn;
	}
	public Map<Node, String> getEvidence() {
		return bn.getEvidance();
	}
	public void addEvidence(String nodeName,String lable) {
		bn.addEvidance(nodeName, lable);
	}
	public Node getQuery() {
		return bn.getQuery();
	}
	public void setQuery(String nodeName) {
		bn.addQuery(nodeName);
	}

	public void cleanEvidence() {
		bn.cleanEvidence();
		
	}
	
}
