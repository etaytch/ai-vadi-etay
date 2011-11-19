package graph;

import java.util.ArrayList;

import graph.searchAlgorithms.Dijkstra;
import graph.searchGraph.Edge;
import graph.searchGraph.Graph;
import graph.searchGraph.Node;

public class MainDijakstra {

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		Node n0 = new Node(0);
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		Node n6 = new Node(6);
							
		Node[] nodes = new Node[7];
		nodes[0] = n0;
		nodes[1] = n1;
		nodes[2] = n2;
		nodes[3] = n3;
		nodes[4] = n4;
		nodes[5] = n5;
		nodes[6] = n6;
		
		Edge[] edges = new Edge[8];
		Edge e0 = new Edge(n0, n2, 1);
		Edge e1 = new Edge(n0, n3, 1);
		Edge e2 = new Edge(n1, n4, 1);
		Edge e3 = new Edge(n2, n5, 1);
		Edge e4 = new Edge(n3, n4, 1);
		Edge e5 = new Edge(n4, n6, 1);
		Edge e6 = new Edge(n2, n4, 2);
		Edge e7 = new Edge(n5, n6, 1);
		
		edges[0] = e0;
		edges[1] = e1;
		edges[2] = e2;
		edges[3] = e3;
		edges[4] = e4;
		edges[5] = e5;
		edges[6] = e6;
		edges[7] = e7;
		
		Graph graph = new Graph(nodes, edges);
		
		ArrayList<Node> result  = new ArrayList<Node>();
		double  res = Dijkstra.findShortestPath(graph,n2, n6,result);
		
		System.out.println("the Minimal path weight is: "+res);		
		for(Node n :result){
			System.out.println(n);			
		}
		
	}

}
