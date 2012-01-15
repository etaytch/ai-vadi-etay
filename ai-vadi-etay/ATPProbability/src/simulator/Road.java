package simulator;

/**
 * 
 * road (edge) in ATP Environment
 *
 */
public class Road {
	
	private int _weight;
	private boolean _flooded;
	private double  _floodedProb;	
	private Vertex _from;
	public Vertex get_from() {
		return _from;
	}

	public void set_from(Vertex _from) {
		this._from = _from;
	}

	public Vertex get_to() {
		return _to;
	}

	public void set_to(Vertex _to) {
		this._to = _to;
	}

	private Vertex _to;
	
	public Road(Vertex from, Vertex to, int weight, double floodedProb) {
		super();
		_weight = weight;
		_floodedProb = floodedProb;
		_from = from;
		_to = to;
		
		if (Math.random()<=_floodedProb)
			_flooded = true;
		else
			_flooded = false;
		
	}

	public int get_weight() {
		return _weight;
	}

	public void set_weight(int weight) {
		_weight = weight;
	}
	

	@Override
	public String toString() {
		return "Edge _from=" + _from.get_number() +", _to=" + _to.get_number() +", _flooded=" + _flooded + ", _weight=" + _weight;
	}

	public boolean is_flooded() {
		return _flooded;
	}

	public void set_floodedProb(double flooded) {
		_floodedProb = flooded;
	}

	
}
