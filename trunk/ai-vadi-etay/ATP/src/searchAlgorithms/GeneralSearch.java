package searchAlgorithms;


import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;


public class GeneralSearch {

	public static Vector<DecisionNode> search(Problem problem)
	{
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
				queue.addAll(currentNode.get_children());
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
