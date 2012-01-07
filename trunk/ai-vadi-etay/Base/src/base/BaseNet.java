package base;

import java.util.List;
import java.util.Map;

import DO.Node;

public interface BaseNet {

	void addNode(String nodeName, String nodeType); //nodeType = "nois"/"norm" 

	void setParents(String nodeName, List<String> parents); //parents = List of nodes names

	void setLables(String nodeName, List<String> labels); //labels = List of nodes labels (TRUE,FALSE mostly )

	void addNodeDistTableRow(String nodeName, List<String> pdist, List<Double> ndist); //pdist = List of node's parents labels, ndist = distribution over set of labels   

	int getParentsCount(String nodeName);

	int getLablesCount(String nodeName);

	void print();

	Map<String,Node> getNodes();
	
}
