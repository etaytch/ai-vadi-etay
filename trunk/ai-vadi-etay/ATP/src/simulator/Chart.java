package simulator;

public class Chart {
	int _actionsNumber;
	double _totalTime;
	
	
	public Chart() {
		super();
		_actionsNumber = 0;
		_totalTime = 0;
	}


	public Chart(int actionsNumber, int totalTime) {
		super();
		_actionsNumber = actionsNumber;
		_totalTime = totalTime;
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
		return "Chart: _actionsNumber=" + _actionsNumber + ", _totalTime="
				+ _totalTime;
	}


	public void addTime(double time) {
		_totalTime += time;
	}
	
	public void incrementAction() {
		_actionsNumber += 1;
	}
	
	

}
