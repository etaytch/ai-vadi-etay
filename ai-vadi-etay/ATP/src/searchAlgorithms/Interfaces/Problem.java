package searchAlgorithms.Interfaces;
import agents.Agent;


/**
 * 
 * problem interfaces
 *
 */
public interface Problem {

	boolean goalTest(DecisionNode d);
	DecisionNode getInitNode();
	Agent getAgent();

}
