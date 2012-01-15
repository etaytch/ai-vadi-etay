package generalAlgorithms;


public class Graph {
	private Node[] _nodes;
	private Edge[] _edges;
	public Graph(Node[] _nodes, Edge[] _edges) {		
		this._nodes = _nodes;
		this._edges = _edges;
	}
	public Node[] get_nodes() {
		return _nodes;
	}
	public void set_nodes(Node[] _nodes) {
		this._nodes = _nodes;
	}
	public Edge[] get_edges() {
		return _edges;
	}
	public void set_edges(Edge[] _edges) {
		this._edges = _edges;
	}
	
	public Node get_node_by_ID(int id){
		if(this._nodes[id].getId()==id){
			return this._nodes[id];			
		}
		else{
			for(Node n:_nodes){
				if(n.getId()==id){
					return n;					
				}				
			}
		}
		return null;
	}
	
}
