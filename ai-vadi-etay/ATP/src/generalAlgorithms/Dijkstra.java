package generalAlgorithms;



import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * This is Dijkstra find shortest path general algorithm
 *
 */
public class Dijkstra {
	
	/**
	 * 
	 * @param graph simple graph stracture
	 * @param start start Node
	 * @param target goal Node
	 * @param C  the shortest path future array
	 * @return the weight of the shortest path
	 */
	public static double findShortestPath(Graph graph, Node start,Node target,ArrayList<Node> C) {
		   Node[] nodes = graph.get_nodes();
		   Edge[] edges = graph.get_edges();
	       double[][] Weight = initializeWeight(nodes, edges);
	       double[] D = new double[nodes.length];
	       Node[] P = new Node[nodes.length];
	       
	       
	       if(start.equals(target)){
	    	   return 0;
	    	   
	       }
	       	       
	       // initialize:
	       // (C)andidate set,
	       // (D)yjkstra special path length, and
	       // (P)revious Node along shortest path
	       for(int i=0; i<nodes.length; i++){
	           C.add(nodes[i]);
	           D[i] = Weight[start.getId()][i];
	           if(D[i] != Double.MAX_VALUE){
	               P[i] = nodes[start.getId()];
	           }
	       }

	       // crawl the Graph
	       for(int i=0; i<nodes.length; i++){
	           // find the lightest Edge among the candidates
	           double l = Double.MAX_VALUE;
	           Node n = nodes[0];
	           for(Node j : C){
	               if(D[j.getId()] < l){
	                   n = j;
	                   l = D[j.getId()];
	               }
	           }
	           C.remove(n);

	           // see if any Edges from this Node yield a shorter path than from source->that Node
	           for(int j=0; j<nodes.length; j++){
	               if(D[n.getId()] != Double.MAX_VALUE && Weight[n.getId()][j] != Double.MAX_VALUE && D[n.getId()]+Weight[n.getId()][j] < D[j]){
	                   // found one, update the path
	                   D[j] = D[n.getId()] + Weight[n.getId()][j];
	                   P[j] = n;
	               }
	           }
	       }
	       // we have our path. reuse C as the result list
	       C.clear();
	       int loc = target.getId();
	       if (D[target.getId()]!=Double.MAX_VALUE){
	    	   C.add(target);	    	   
	       }
	       // backtrack from the target by P(revious), adding to the result list
	       while(P[loc] != nodes[start.getId()]){
	           if(P[loc] == null){
	               // looks like there's no path from source to target
	               return -1;
	           }
	           C.add(0, P[loc]);
	           loc = P[loc].getId();
	       }
	       C.add(0, nodes[start.getId()]);
	       return D[target.getId()];
	   }

		/**
		 * Init step in Dijkstra algorithm
		 * @param nodes
		 * @param edges
		 * @return weight matrix  initialized to infinity
		 */
	   private static double[][] initializeWeight(Node[] nodes, Edge[] edges){
	       double[][] Weight = new double[nodes.length][nodes.length];
	       for(int i=0; i<nodes.length; i++){
	           Arrays.fill(Weight[i], Double.MAX_VALUE);
	       }
	       for(Edge e : edges){
	           Weight[e.getFrom().getId()][e.getTo().getId()] = e.getWeigth();
	       }
	       return Weight;
	   }
}
