package generalAlgorithms;

public class Node {
	private int _id;

	public Node(int id) {		
		this._id = id;
	}

	public int getId() {
		return _id;
	}

	@Override
	public String toString() {
		return "Node [_id=" + _id + "]";
	}

	public void setId(int id) {
		this._id = id;
	}
	
}
