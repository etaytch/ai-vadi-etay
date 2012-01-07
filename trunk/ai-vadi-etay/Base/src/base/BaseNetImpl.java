package base;

import java.util.HashMap;
import java.util.List;

import DO.Node;
import DO.NoisyOrNode;
import DO.NormalNode;

public class BaseNetImpl implements BaseNet {

	HashMap<String,Node> _nodes;
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
			_nodes.put(nodeName,node);	
		}
	}

	@Override
	public void addNodeDistTableRow(String nodeName, List<String> pdist,
			double dist) {
		
		Node node = _nodes.get(nodeName);
		if (node!=null){
			node.getTable().put(pdist, dist);
		}
	}

	@Override
	public void setLables(String nodeName, List<String> lables) {

		Node node = _nodes.get(nodeName);
		if (node!=null){
			node.setLables(lables);
		}
	}

	@Override
	public void setParents(String nodeName, List<String> parents) {
		
		Node node = _nodes.get(nodeName);
		if (node!=null){
			for(String parent : parents){
				Node nParent = _nodes.get(parent);
				nParent.addParent(nParent);
			}
		}

	}

}