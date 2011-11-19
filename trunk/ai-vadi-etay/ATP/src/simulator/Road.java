package simulator;

public class Road {
	
	private int _weight;
	private boolean _flooded;
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
	
	public Road(Vertex from, Vertex to, int weight, boolean flooded) {
		super();
		_weight = weight;
		_flooded = flooded;
		_from = from;
		_to = to;
		
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

	public void set_flooded(boolean flooded) {
		_flooded = flooded;
	}

	
}
