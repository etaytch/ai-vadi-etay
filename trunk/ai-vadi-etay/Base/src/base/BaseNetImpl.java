package base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DO.Node;
import DO.NoisyOrNode;
import DO.NormalNode;

public class BaseNetImpl implements BaseNet {

	Map<String,Node> nodes;
	
	public BaseNetImpl(){
		nodes = new HashMap<String,Node>();
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
				nParent.addParent(nParent);
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
		for (int i=0;i<nodes.size();i++){
			nodes.get(i).print();			
		}
	}
}
