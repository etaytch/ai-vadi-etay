package DO;

import java.util.List;
import java.util.Map;

public class NormalNode extends Node{

	public NormalNode(String name, List<String> lables, List<Node> parents,
			Map<List<String>,List<Double>> table) {
		super(name, lables, parents, table);
	}

	public NormalNode(String name) {
		super(name);
	}
	

}
