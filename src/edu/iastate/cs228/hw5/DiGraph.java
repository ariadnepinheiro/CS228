package edu.iastate.cs228.hw5;

/**
 * @author Darren Hushak
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class DiGraph<V> {

	// A map that stores an entry of a node and a set of outgoing edges
	// (destination node, weight) from the node.
	protected HashMap<V, HashSet<Edge<V, Integer>>> map;

	// Total number of edges in the graph
	private int numEdges;

	/**
	 * Create an empty graph.
	 */
	public DiGraph() {
		map = new HashMap<V, HashSet<Edge<V, Integer>>>();
		numEdges = 0;
	}

	/**
	 * Add a directed edge from the source node to the destination node.
	 * 
	 * @param src
	 *            Source node
	 * @param dst
	 *            Destination node
	 * @param c
	 *            Weight of the edge
	 * @return false if src or dst is null or c <= 0 or src==dst or
	 *         src.equals(dst) true if a new edge from src to dst is added with
	 *         the weight if there is already existing edge from src to dst,
	 *         replace the existing weight with the new weight
	 */
	public boolean addEdge(V src, V dst, Integer c) {
		if (src == null || dst == null || c <= 0 || src == dst
				|| src.equals(dst)) {
			return false;
		}

		// Add new vertices if not already existent
		if (!this.map.containsKey(src)) {
			this.addVertex(src);
		}
		if (!this.map.containsKey(dst)) {
			this.addVertex(dst);
		}

		// Add new Edge
		Edge<V, Integer> newEdge = new Edge<V, Integer>(dst, c);

		if (this.map.get(src).contains(newEdge)) {
			this.map.get(src).remove(newEdge);
			this.numEdges--;
		}

		// Check if edge was added or just updated
		this.map.get(src).add(newEdge);
		this.numEdges++;

		return true;
	}

	/**
	 * Add a vertex to the graph with an empty set of edges associated with it.
	 * 
	 * @param vertex
	 *            Vertex to be added
	 * @return false if vertex is null or vertex is already in the graph true
	 *         otherwise
	 */
	public boolean addVertex(V vertex) {
		if (vertex == null || this.map.containsKey(vertex)) {
			return false;
		}
		HashSet<Edge<V, Integer>> emptyHashSet = new HashSet<Edge<V, Integer>>();
		this.map.put(vertex, emptyHashSet);
		return true;
	}

	/**
	 * 
	 * @return Number of vertices (nodes) in the graph
	 */
	public int numVertices() {
		return this.map.size();
	}

	/**
	 * 
	 * @return A set of vertices in this graph When there are no vertices in the
	 *         graph, return an empty set.
	 */
	public Set<V> vertices() {
		return this.map.keySet();
	}

	/**
	 * 
	 * @param vertex
	 * @return A set of vertices in which there is an edge from the given vertex
	 *         to each of these vertices. Return an empty set if there is no
	 *         adjacent node or the vertex is not in the graph or vertex is null
	 */
	public Set<Edge<V, Integer>> adjacentTo(V vertex) {
		return this.map.get(vertex);
	}

	/**
	 * 
	 * @param vertex
	 * @return true if the given vertex is in the graph false otherwise
	 *         including the case of a null vertex
	 */
	public boolean hasVertex(V vertex) {
		if (vertex == null) {
			return false;
		}
		return this.map.containsKey(vertex);
	}

	/**
	 * 
	 * @return Total number of edges in this graph
	 */
	public int numEdges() {
		return this.numEdges;
	}

	/**
	 * Check whether an edge from src to dst exists
	 * 
	 * @param src
	 * @param dst
	 * @return true if there exists an edge from src to dst regardless of the
	 *         weight false otherwise (including when either src or dst is null
	 *         or src or dst is not in the map)
	 */
	public boolean hasEdge(V src, V dst) {
		if (src == null || dst == null || !this.map.containsKey(src)
				|| !this.map.containsKey(dst)) {
			return false;
		}
		Edge<V, Integer> falseEdge = new Edge<V, Integer>(dst, 0);
		return this.map.get(src).contains(falseEdge);
	}

	/**
	 * Remove this vertex from the graph if possible and calculate the number of
	 * edges in the graph accordingly. For instance, if the vertex has 4
	 * outgoing edges and 2 incoming edges, the total number of edges after the
	 * removal of this vertex is subtracted by 6.
	 * 
	 * @param vertex
	 *            Vertex to be removed
	 * @return false if the vertex is null or there is no such vertex in the
	 *         graph true if removal is successful
	 */
	public boolean removeVertex(V vertex) {
		if (vertex == null || !(this.map.containsKey(vertex))) {
			return false;
		}
		for (V key : this.incomingEdges(vertex)) {
			Edge<V, Integer> incomingEdge = new Edge<V, Integer>(vertex, 0);
			this.map.get(key).remove(incomingEdge);
			numEdges--;
		}
		// Get rid of the vertex
		numEdges -= this.map.get(vertex).size();
		// numEdges--;
		this.map.remove(vertex);
		return true;
	}

	/**
	 * Return a set of nodes with edges coming to this given vertex
	 * 
	 * @param vertex
	 * @return empty set if the vertex is null or the vertex is not in this
	 *         graph otherwise, return a non-empty set consists of nodes with
	 *         edges coming to this vertex
	 */
	public Set<V> incomingEdges(V vertex) {
		Set<V> ret = new HashSet<V>();
		// For every key in this map..
		for (V key : this.map.keySet()) {
			// For every Edge in from that key...
			for (Edge<V, Integer> inBoundEdge : this.map.get(key)) {
				// Check if inbound edge points at this vertex
				if (inBoundEdge.getVertex().equals(vertex)) {
					ret.add(key);
				}
			}
		}

		return ret;
	}

	/**
	 * Return a set of nodes with edges going out of this given vertex
	 * 
	 * @param vertex
	 * @return empty set if the vertex is null or the vertex is not in this
	 *         graph otherwise, return a non-empty set consists of nodes with
	 *         edges going out of this vertex
	 */
	public Set<V> outgoingEdges(V vertex) {
		Set<V> ret = new HashSet<V>();
			for (Edge<V, Integer> outBoundEdge : this.map.get(vertex)) {
				ret.add(outBoundEdge.getVertex());
			}
		

		return ret;
	}
	
	/**
	 * Compute Dijkstra single source shortest path from the source node. Use
	 * the algorithm discussed in class
	 * 
	 * 
	 * @param source
	 *            Source node
	 * @return Empty map if the source is null or it is not a vertex in the
	 *         graph, or, it does not have any outgoing edges Otherwise, return
	 *         a map of entries, each having a vertex and smallest cost going
	 *         from the source node to it
	 */
	public Map<V, Integer> Dijkstra(V source) {
		HashMap<V, Integer> ret = new HashMap<V, Integer>();
		if (source == null || !this.map.containsKey(source)
				|| this.map.get(source).isEmpty()) {
			return ret;
		}

		// Put every key with a distance of -1 into ret
		for (V key : this.map.keySet()) {
			ret.put(key, -1);
		}
		ret.put(source, 0);

		PriorityQueue<V> vertexQueue = new PriorityQueue<V>();
		vertexQueue.add(source);
		while (!vertexQueue.isEmpty()) {
			V current = vertexQueue.poll();

			// Visit each edge exiting current
			for (Edge<V, Integer> edge : this.map.get(current)) {
				V adjVertex = edge.getVertex();
				int cost = edge.getCost();
				int distanceThroughU = ret.get(current) + cost;
				if (distanceThroughU < ret.get(adjVertex)
						|| ret.get(adjVertex) == -1) {
					vertexQueue.remove(adjVertex);
					ret.put(adjVertex, distanceThroughU);
					vertexQueue.add(adjVertex);
				}
			}
		}

		// Clean up ret by removing all infinite (untouched) nodes
		for (V key : this.map.keySet()) {
			if (ret.get(key) == -1) {
				ret.remove(key);
			}
		}

		return ret;

	}

	/**
	 * Prints a textual visualization of the current map
	 */
	public void print() {
		String line;
		// For every HashSet in this map..
		for (V key : this.map.keySet()) {
			line = key.toString() + " => ";
			// For every Edge in said Hash Set...
			for (Edge<V, Integer> dst : this.map.get(key)) {
				line += dst.getVertex().toString() + "(" + dst.getCost()
						+ "), ";
			}
			System.out.println(line);
		}
	}
}