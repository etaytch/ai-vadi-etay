package simulator;

import searchAlgorithms.DecisionNodes.AtpDecisionNode;
import searchAlgorithms.Interfaces.DecisionNode;
import simulator.Interfaces.Action;

public class DecisionNodeInfo {
	private String myName;
	private Vertex myVertex; 
	private Car myCar; 
	private Road myRoad;	
	private DecisionNode myParent;
	private int myGoal;
	private double myTime;
	private Action myAction;
	private String opponentName;
	private Vertex opponentVertex; 
	private Car opponentCar; 
	private Road opponentRoad;
	private DecisionNode opponentParent;
	private int opponentGoal;
	private double opponentTime;
	private Action opponentAction;
	private int nestingLevel;
	
	
	
	public DecisionNodeInfo(String myName,Vertex myVertex, Car myCar, Road myRoad,
			DecisionNode myParent, int myGoal, double myTime, Action myAction,String opponentName,Vertex opponentVertex, Car opponentCar,
			Road opponentRoad, DecisionNode opponentParent,int opponentGoal, double opponentTime, Action opponentAction,int nestingLevel) {
		super();
		this.myName=myName;
		this.myVertex = myVertex;
		this.myCar = myCar;
		this.myRoad = myRoad;
		this.myParent = myParent;
		this.myGoal = myGoal;
		this.myTime = myTime;
		this.myAction = myAction;
		this.opponentName=opponentName;
		this.opponentVertex = opponentVertex;
		this.opponentCar = opponentCar;
		this.opponentRoad = opponentRoad;
		this.opponentParent = opponentParent;
		this.opponentGoal = opponentGoal;
		this.opponentTime = opponentTime;
		this.opponentAction = opponentAction;
		this.nestingLevel = nestingLevel;		
	}
	
	public DecisionNodeInfo(DecisionNodeInfo other) {
			this(other.opponentName,
					other.opponentVertex,
					other.opponentCar,
					other.opponentRoad,
					other.opponentParent,
					other.opponentGoal,
					other.opponentTime,
					other.opponentAction,
					other.myName,
					other.myVertex,
					other.myCar,
					other.myRoad,
					other.myParent,
					other.myGoal,
					other.myTime,
					other.myAction,
					other.nestingLevel);		
	}
	
	public Vertex getMyVertex() {
		return myVertex;
	}
	public void setMyVertex(Vertex myVertex) {
		this.myVertex = myVertex;
	}
	public Car getMyCar() {
		return myCar;
	}
	public void setMyCar(Car myCar) {
		this.myCar = myCar;
	}
	public Road getMyRoad() {
		return myRoad;
	}
	public void setMyRoad(Road myRoad) {
		this.myRoad = myRoad;
	}
	public DecisionNode getMyParent() {
		return myParent;
	}
	public void setMyParent(DecisionNode myParent) {
		this.myParent = myParent;
	}
	public Vertex getOpponentVertex() {
		return opponentVertex;
	}
	public void setOpponentVertex(Vertex opponentVertex) {
		this.opponentVertex = opponentVertex;
	}
	public Car getOpponentCar() {
		return opponentCar;
	}
	public void setOpponentCar(Car opponentCar) {
		this.opponentCar = opponentCar;
	}
	public Road getOpponentRoad() {
		return opponentRoad;
	}
	public void setOpponentRoad(Road opponentRoad) {
		this.opponentRoad = opponentRoad;
	}
	public DecisionNode getOpponentParent() {
		return opponentParent;
	}
	public void setOpponentParent(DecisionNode opponentParent) {
		this.opponentParent = opponentParent;
	}
	public int getNestingLevel() {
		return nestingLevel;
	}
	public void setNestingLevel(int nestingLevel) {
		this.nestingLevel = nestingLevel;
	}

	public void setOpponentGoal(int opponentGoal) {
		this.opponentGoal = opponentGoal;
	}

	public int getOpponentGoal() {
		return opponentGoal;
	}

	public void setMyGoal(int myGoal) {
		this.myGoal = myGoal;
	}

	public int getMyGoal() {
		return myGoal;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getMyName() {
		return myName;
	}

	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}

	public String getOpponentName() {
		return opponentName;
	}
	
	public String toString(){
		
		return "==============================================" +"\n"
		+"   My Name: "+this.myName+"		Other Name: "+this.opponentName+"\n"
		+"   My Vertex: "+this.myVertex.get_number()+"			Other Vertex: "+this.opponentVertex.get_number()+"\n"
		+"   My Car: "+this.myCar.get_name()+"		Other Car: "+this.opponentCar.get_name()+"\n"
		+"   My Goal: "+this.myGoal+"			Other Goal: "+this.opponentGoal+"\n"
		+"   My Time: "+this.myTime+"			Other Time: "+this.opponentTime+"\n"
		+"==============================================" ;
		//+"My Name: "+this.myName+"	|	Other Name: "+this.opponentName+"\n"							
	}

	public void setMyTime(double myTime) {
		this.myTime = myTime;
	}

	public double getMyTime() {
		return myTime;
	}

	public void setOpponentTime(double opponentTime) {
		this.opponentTime = opponentTime;
	}

	public double getOpponentTime() {
		return opponentTime;
	}

	public double getResult(String name) {
		if(myName.equals(name)){
			return (opponentTime-myTime);
			
		}
		return (myTime-opponentTime);
	}

	public void setMyAction(Action myAction) {
		this.myAction = myAction;
	}

	public Action getMyAction() {
		return myAction;
	}

	public void setOpponentAction(Action opponentAction) {
		this.opponentAction = opponentAction;
	}

	public Action getOpponentAction() {
		return opponentAction;
	}
}
