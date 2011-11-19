package graph.searchGraph;

public class Edge {
	private Node _from;
	private Node _to;
	private int _weigth;
	
	
	public Edge(Node _from, Node _to, int _weigth) {		
		this._from = _from;
		this._to = _to;
		this._weigth = _weigth;
	}
	@Override
	public String toString() {
		return "Edge [_from=" + _from + ", _to=" + _to + ", _weigth=" + _weigth
				+ "]";
	}
	public Node getFrom() {
		return _from;
	}
	public void setFrom(Node _from) {
		this._from = _from;
	}
	public Node getTo() {
		return _to;
	}
	public void setTo(Node _to) {
		this._to = _to;
	}
	public int getWeigth() {
		return _weigth;
	}
	public void setWeigth(int _weigth) {
		this._weigth = _weigth;
	}
}
