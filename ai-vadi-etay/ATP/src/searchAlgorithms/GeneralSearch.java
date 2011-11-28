package searchAlgorithms;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Vector;

import searchAlgorithms.Interfaces.DecisionNode;
import searchAlgorithms.Interfaces.Problem;

/**
 * 
 * General Search Algorithm implementation 
 *
 */
public class GeneralSearch {

	public static Vector<DecisionNode> search(Problem problem, boolean memoization, int[] expend_steps)
	{
		Vector<DecisionNode> lookUpTable = new Vector<DecisionNode>();
		PriorityQueue<DecisionNode> queue = new PriorityQueue<DecisionNode>();
		queue.add(problem.getInitNode());
		
		while(true){
			DecisionNode currentNode = queue.remove();
			expend_steps[0]++;
			System.out.println(expend_steps[0]);
			if (currentNode==null){
				return null;	
			}
			if (problem.goalTest(currentNode)){
				return generateSolution(currentNode); 
			}
			else{
				currentNode.expand(problem);
				
				if (memoization){
					if(currentNode.get_children()==null){
						System.out.println("nn");
					}
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

	/**
	 * transformation of goal DesicionNode to chain of DesicionNodes by collecting all the Nodes grade parents 
	 * @param dnode
	 * @return
	 */
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
