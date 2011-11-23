package searchAlgorithms;


import java.util.Vector;


public interface DecisionNode extends Comparable<DecisionNode> {
	public void expand(Problem problem);
	public DecisionNode get_parent();
	public Vector<DecisionNode> get_children();
	public int get_goal_evaluation();
	public int getNestingLevel();
	public boolean equals(DecisionNode dn); 
}
