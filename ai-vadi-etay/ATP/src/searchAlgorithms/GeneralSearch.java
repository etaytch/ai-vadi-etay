package searchAlgorithms;


import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;


public class GeneralSearch {

	public static Vector<AtpDecisionNode> search(Problem problem)
	{
		PriorityQueue<AtpDecisionNode> queue = new PriorityQueue<AtpDecisionNode>();
		queue.add(problem.getInitNode());
		
		while(true){
			AtpDecisionNode currentNode = queue.poll();
			if (currentNode==null){
				return null;	
			}
			if (problem.goalTest()){
				return generateSolution(currentNode); 
			}
			else{
				currentNode.expand(problem);
				queue.addAll(currentNode.get_children());
			}
		}
	}

	private static  Vector<AtpDecisionNode> generateSolution( AtpDecisionNode dnode) {
		Vector<AtpDecisionNode> solution = new Vector<AtpDecisionNode>();
		
		while(dnode != null){
			
			solution.add(dnode);
			dnode = dnode.get_parent();
		}
		Collections.reverse(solution);
		return solution;
	}

}
