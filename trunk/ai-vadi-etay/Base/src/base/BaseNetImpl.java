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
		Node n = _nodes.get(nodeName);
		if (n!=null){
			//n.getTable().
		}
		
	}

	@Override
	public void setLables(String nodeName, List<String> labels) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParents(String nodeName, List<String> parents) {
		// TODO Auto-generated method stub

	}

}
