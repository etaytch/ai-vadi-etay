package simulator;

/**
 * Agent score chart
 * 
 */
public class Chart {
	int _actionsNumber;
	int _expend_steps;
	double _totalTime;
	
	
	public Chart() {
		super();
		_actionsNumber = 0;
		_totalTime = 0;
		_expend_steps = 0;
	}


	public Chart(int actionsNumber, int totalTime) {
		super();
		_actionsNumber = actionsNumber;
		_totalTime = totalTime;
	}

	public void set_expend_steps(int steps){
		this._expend_steps = steps;		
	}

	public int get_actionsNumber() {
		return _actionsNumber;
	}


	public void set_actionsNumber(int actionsNumber) {
		_actionsNumber = actionsNumber;
	}


	public double get_totalTime() {
		return _totalTime;
	}


	public void set_totalTime(double totalTime) {
		_totalTime = totalTime;
	}
	
	@Override
	public String toString() {
		return "Chart: Amount of Actions = " + _actionsNumber + ", Score = "
				+ _totalTime + ", Expansion Steps = "+_expend_steps;
	}


	public void addTime(double time) {
		_totalTime += time;
	}
	
	public void incrementAction() {
		_actionsNumber += 1;
	}
	
	

}
