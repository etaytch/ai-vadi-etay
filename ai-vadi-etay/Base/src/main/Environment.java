package main;
import base.BaseNet;


public class Environment {
	private BaseNet bn;
	private String evidence;
	private String query;
	
	public Environment(BaseNet bn){
		this.bn = bn;
	}
	
	public BaseNet getBn() {
		return bn;
	}
	public void setBn(BaseNet bn) {
		this.bn = bn;
	}
	public String getEvidence() {
		return evidence;
	}
	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
