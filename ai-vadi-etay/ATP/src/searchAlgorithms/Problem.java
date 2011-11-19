package searchAlgorithms;
import agents.Agent;


public interface Problem {

	boolean goalTest(DecisionNode d);
	DecisionNode getInitNode();
	Agent getAgent();

}
