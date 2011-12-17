package searchAlgorithms.Interfaces;


import java.util.Vector;


/**
 * 
 * Decision node interface
 *
 */
public interface DecisionNode extends Comparable<DecisionNode> {
	public void expand(Problem problem);
	public DecisionNode get_parent();
	public Vector<DecisionNode> get_children();
	public int get_goal_evaluation();
	public int getNestingLevel();
	public boolean equals(DecisionNode dn); 
	public int getPenalty();
	public void setPenalty(int penalty);
	public double getValue();
	public void setValue(double value);
}
