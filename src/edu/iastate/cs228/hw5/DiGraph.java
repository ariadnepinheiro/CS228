package edu.iastate.cs228.hw5;

/**
 * @author Your name
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DiGraph<V> {

	// A map that stores an entry of a node and a set of outgoing edges (destination node, weight) from the node.
	private HashMap<V, HashSet<Edge<V, Integer>>> map;
	// Total number of edges in the graph
	private int numEdges;
	
	/**
	 * Create an empty graph.
	 */
	public DiGraph() {
		map = new HashMap<V, HashSet<Edge<V, Integer>>>();
		// TODO
	}

	/**
	 * Add a directed edge from the source node to the destination node.
	 * 
	 * @param src Source node
	 * @param dst Destination node
	 * @param c Weight of the edge
	 * @return false if src or dst is null or c <= 0 or src==dst or src.equals(dst)
	 *         true if a new edge from src to dst is added with the weight  
	 *              if there is already existing edge from src to dst, replace the existing weight with the new weight
	 */
	public boolean addEdge(V src, V dst, Integer c) {
		return true; // TODO
	}

	/**
	 * Add a vertex to the graph with an empty set of edges associated with it.
	 * 
	 * @param vertex Vertex to be added
	 * @return false if vertex is null or vertex is already in the graph
	 * 		   true otherwise
	 */
	public boolean addVertex(V vertex) {
		// TODO
		return true;
	}

	/**
	 * 
	 * @return Number of vertices (nodes) in the graph
	 */
	public int numVertices() {
		return 0; // TODO
	}

	/**
	 * 
	 * @return A set of vertices in this graph
	 * 			When there are no vertices in the graph, return an empty set.
	 */
	public Set<V> vertices() {
		return null; // TODO
	}

	/**
	 * 
	 * @param vertex
	 * @return A set of vertices in which there is an edge from the given vertex to each of these vertices. 
	 * 		   Return an empty set if there is no adjacent node or the vertex is not in the graph or vertex is null
	 */
	
	public Set<Edge<V, Integer>> adjacentTo(V vertex) {
		return null; // TODO
	}


	/**
	 * 
	 * @param vertex 
	 * @return true if the given vertex is in the graph 
	 *         false otherwise including the case of a null vertex
	 */
	public boolean hasVertex(V vertex) {
		return true; // TODO
	}
	
	/**
	 * 
	 * @return Total number of edges in this graph
	 */
	public int numEdges() {
		return 0; // TODO
	}

	/**
	 * Check whether an edge from src to dst exists
	 * @param src 
	 * @param dst
	 * @return true if there exists an edge from src to dst regardless of the weight
	 *         false otherwise (including when either src or dst is null  or src or dst is not 
	 *         in the map)
	 */
	public boolean hasEdge(V src, V dst) {
		// TODO
		return false; // TODO
	}
	
	/**
	 * Remove this vertex from the graph if possible and calculate the number of edges in 
	 * the graph accordingly. For instance, if the vertex has 4 outgoing edges and 2 incoming edges, 
	 * the total number of edges after the removal of this vertex is subtracted by 6. 
	 * 
	 * @param vertex Vertex to be removed
	 * @return false if the vertex is null or there is no such vertex in the graph
	 * 		   true if removal is successful
	 */
	public boolean removeVertex(V vertex) {
		// TODO
		return true; // TODO
	}
	
	/**
	 * Return a set of nodes with edges coming to this given vertex
	 * 
	 * @param vertex
	 * @return empty set if the vertex is null or the vertex is not in this graph
	 *         otherwise, return a non-empty set consists of nodes with edges coming to this vertex
	 */
	public Set<V> incomingEdges(V vertex) {
		// TODO
		return null; // TODO
	}
	
	/**
	 * Compute Dijkstra single source shortest path from the source node.
	 * Use the algorithm discussed in class
	 * 
	 * 
	 * @param source Source node
	 * @return Empty map if the source is null or it is not a vertex in the graph, or, it does not have any outgoing edges
	 *         Otherwise, return a map of entries, each having a vertex and smallest cost going from the source node to it
	 */
	public Map<V, Integer> Dijkstra(V source) {
		
		// TODO
		return null; // TODO

	}

}