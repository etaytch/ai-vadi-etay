package base;

import java.util.List;

public interface BaseNet {

	void addNode(String nodeName, String nodeType); //nodeType = "nois"/"norm" 

	void setParents(String nodeName, List<String> parents); //parents = List of nodes names

	void setLables(String nodeName, List<String> labels); //labels = List of nodes labels (TRUE,FALSE mostly )

	void addNodeDistTableRow(String nodeName, List<String> pdist, double dist); //pdist = List of node's parents labels, dist = distribution over set of labels   

	
}
