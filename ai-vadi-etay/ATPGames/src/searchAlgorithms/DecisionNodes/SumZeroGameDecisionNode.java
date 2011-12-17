package searchAlgorithms.DecisionNodes;

import java.util.ArrayList;
import java.util.Vector;

import generalAlgorithms.Dijkstra;
import generalAlgorithms.Edge;
import generalAlgorithms.Graph;
import generalAlgorithms.Node;
import searchAlgorithms.GameAtpProblem;
import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;
import simulator.Car;
import simulator.DecisionNodeInfo;
import simulator.Defs;
import simulator.Environment;
import simulator.Road;
import simulator.Vertex;

/**
 * 
 * Greedy decision node
 *
 */
public class SumZeroGameDecisionNode extends GameDecisionNode {

	public SumZeroGameDecisionNode(DecisionNodeInfo dni){
		super(dni);		
	}
	
	@Override
	public int compareTo(DecisionNode o) {
		if (get_H()>((SumZeroGameDecisionNode)o).get_H()) return 1;	
		if (get_H()<((SumZeroGameDecisionNode)o).get_H()) return -1;  
		return 0;
	}
	
	
}
