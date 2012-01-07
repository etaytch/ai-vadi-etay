package base;

import java.util.List;

import DO.Node;
import DO.NoisyOrNode;
import DO.NormalNode;

public class BaseNetImpl implements BaseNet {

	List<Node> _nodes;
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
			throw new Exception("Wrong Node type!!");
		}
		
		_nodes.add(node);

	}

	@Override
	public void addNodeDistTableRow(String string, List<String> pdist,
			double dist) {
		// TODO Auto-generated method stub

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
