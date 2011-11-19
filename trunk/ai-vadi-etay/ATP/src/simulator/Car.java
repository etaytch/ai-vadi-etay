package simulator;

public class Car {
	
	private double _coff;
	private int _speed;
	private String _name;
	
	public Car(String name, int speed, double coff) {
		super();
		_coff = coff;
		_speed = speed;
		_name = name;
	}

	@Override
	public String toString() {
		return "Car: _name=" + _name + ", _speed=" + _speed + ", _coff="+ _coff;
	}

	public double get_coff() {
		return _coff;
	}

	public void set_coff(double coff) {
		_coff = coff;
	}

	public int get_speed() {
		return _speed;
	}

	public void set_speed(int speed) {
		_speed = speed;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_name() {
		return _name;
	}
	
	
}
