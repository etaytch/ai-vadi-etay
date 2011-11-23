package searchAlgorithms;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;


public class GeneralSearch {

	public static Vector<DecisionNode> search(Problem problem, boolean memoization)
	{
		Vector<DecisionNode> lookUpTable = new Vector<DecisionNode>();
		PriorityQueue<DecisionNode> queue = new PriorityQueue<DecisionNode>();
		queue.add(problem.getInitNode());
		
		while(true){
			DecisionNode currentNode = queue.remove();
			if (currentNode==null){
				return null;	
			}
			if (problem.goalTest(currentNode)){
				return generateSolution(currentNode); 
			}
			else{
				currentNode.expand(problem);
				if (memoization){
					for(DecisionNode dn : currentNode.get_children())
					{
						if (!lookUpTable.contains(dn))
						{
							queue.add(dn);
						}
					}
				}
				else{
				queue.addAll(currentNode.get_children()); //no memoization
				}
			}
		}
	}

	private static  Vector<DecisionNode> generateSolution( DecisionNode dnode) {
		Vector<DecisionNode> solution = new Vector<DecisionNode>();
		
		while(dnode != null){
			
			solution.add(dnode);
			dnode = dnode.get_parent();
		}
		Collections.reverse(solution);
		return solution;
	}

}
