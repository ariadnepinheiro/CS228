package edu.iastate.cs228.hw5;
/**
 * 
 * @param <V> node/vertex
 * @param <C> weight or cost associated with the edge
 */
public class Edge<V, C extends Comparable<? super C> > implements Comparable<Edge<V, C>> {
	  
	private V node;
	private C cost;

	public Edge(V n, C c) {
		node = n;
		cost = c;
	}

	public V getVertex() {
		return node;
	}

	public C getCost() {
		return cost;
	}

	/**
	 * Compare two edges based on the cost only, not on the vertex
	 */
	@Override
	public int compareTo(Edge<V, C> other) {

		return cost.compareTo(other.getCost());
	}

	@Override
	public String toString() {
		return "<" + node.toString() + ", " + cost.toString() + ">";
	}

	@Override
	public int hashCode() {
		return node.hashCode();
	}

	/**
	 * Only compare the nodes but not the cost of the nodes
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (obj.getClass() != this.getClass()))
			return false;
		// object must be Edge at this point
		Edge<?, ?> tmp = (Edge<?, ?>) obj;

		return (node == tmp.node || (node != null && node.equals(tmp.node)));
	}

}
