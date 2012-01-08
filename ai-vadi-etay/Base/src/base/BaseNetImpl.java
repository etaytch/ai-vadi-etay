package base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.standard.QueuedJobCount;

import DO.Node;
import DO.NoisyOrNode;
import DO.NormalNode;

public class BaseNetImpl implements BaseNet {

	Map<String,Node> nodes;
	Node query; 
	Map<Node,String> evidance;
	
	public BaseNetImpl(){
		nodes = new HashMap<String,Node>();
		Node query = null; 
		Map<Node,String> evidance = new HashMap<Node, String>();
		
	}
	
	
	
	@Override
	public Map<String,Node> getNodes(){
		return nodes;
	}
	
	@Override
	public void addNode(String nodeName, String nodeType) {
		Node node = null;
		if (nodeType.equals("nois")){
			node = new NoisyOrNode(nodeName);
		}else if (nodeType.equals("norm")){
			node = new NormalNode(nodeName);
		}
		
		if (node==null)
		{
			System.out.println("Wrong Node type!!  (Wrong input- "+"\'"+nodeType+"\')");
		}else{
			nodes.put(nodeName,node);	
		}
	}

	@Override
	public void addNodeDistTableRow(String nodeName, List<String> pdist,
			List<Double> ndist) {
		
		Node node = nodes.get(nodeName);
		if (node!=null){
			node.getTable().put(pdist, ndist);
		}
	}

	@Override
	public void setLables(String nodeName, List<String> lables) {

		Node node = nodes.get(nodeName);
		if (node!=null){
			node.setLables(lables);
		}
	}

	@Override
	public void setParents(String nodeName, List<String> parents) {
		
		Node node = nodes.get(nodeName);
		if (node!=null){
			for(String parent : parents){
				Node nParent = nodes.get(parent);
				node.addParent(nParent);
			}
		}

	}

	@Override
	public int getLablesCount(String nodeName) {
		int ret = 0;
		Node node = nodes.get(nodeName);
		if (node!=null){
			ret = node.getLables().size();
		}
		return ret;
	}

	@Override
	public int getParentsCount(String nodeName) {
		int ret = 0;
		Node node = nodes.get(nodeName);
		if (node!=null){
			ret = node.getParents().size();
		}
		return ret;
	}
	
	public void print(){
		Set<String> keys = nodes.keySet();
		Iterator<String> itr = keys.iterator(); 
		while(itr.hasNext()) { 
		    Node tmpNode = nodes.get(itr.next()); 
			tmpNode.print();		
		} 
	}



	@Override
	public void addEvidance(String nodeName, String lable) {
		Node eNode = getNodes().get(nodeName);
		if (eNode  == null){
			System.out.println("ILLEGAL QUERY!! ("+nodeName+") will now exit");
			System.exit(-1);
		}
		
		if (!eNode.getLables().contains(lable)){
			System.out.println("ILLEGAL EVIDANCE!! ("+lable+") will now exit");
			System.exit(-1);
		}
		evidance.put(eNode, lable);
	}



	@Override
	public void addQuery(String nodeName) {
		query = getNodes().get(nodeName);
		if (query == null){
			System.out.println("ILLEGAL QUERY!! ("+nodeName+") will now exit");
			System.exit(-1);
		}
	}
}