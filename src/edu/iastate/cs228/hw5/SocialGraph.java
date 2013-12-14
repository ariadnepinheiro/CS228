package edu.iastate.cs228.hw5;

import java.util.List;

/**
 * Create a graph to model a social network of friendships.
 * See the homework description.
 * 
 * @author Darren Hushak
 *
 */

public class SocialGraph {
	
	/** 
	 * Create an empty graph aGraph.
	 * Parse each line, print the entire line on the console, and call the corresponding method.
	 * 
	 * The command is case sensitive.
	 * Assume that the file format is correct.
	 * 
	 * add arg1 arg2 arg3
	 *  call aGraph.addEdge(arg1, arg2, Integer.parseInt(arg3))
	 *  
	 * showFriends arg1 
	 * 	call aGraph.adjacentTo(arg1)
	 * 
	 * remove arg1 
	 * 	call aGraph.remove(arg1)
	 * 
	 * recommendFriends arg1 arg2 arg3
	 *  call the recommendFriends(aGraph, arg1, arg2, Integer.parseInt(arg3)) 
	 *  where arg1 is the name of the person to recommend new friends for;
	 *        arg2 is either "dist" or "weightedDist" indicating the method 
	 *        to select people to recommend as new friends;
	 *        Integer.parseInt(arg3) is the maximum number of new friends to recommend
	 * 
	 * @param args args[0] Input filename with all the commands and arguments
	 */
	public static void main(String[] args) {
		
		DiGraph<Integer> graph = new DiGraph<Integer>();
		graph.print();
		graph.addEdge(17,5,1);
		graph.addEdge(3,5,1);
		graph.addEdge(3,2,1);
		graph.addEdge(2,5,1);

		graph.addEdge(5,16,1);
		graph.print();
		System.out.println(graph.numEdges());
		System.out.println("\n------------------------\n");
		//graph.removeVertex(5);
		graph.print();
		System.out.println(graph.numEdges());

		System.out.println("\n------------------------\n");
		System.out.println(graph.Dijkstra(5));
	}
	
	/**
	 * Recommend topK (e.g., 5) best friend candidates who are not already a friend of personOfInterest
	 * 
	 * If dist option is used, find the shortest path from personOfInterest to all the other nodes in the graph using 
	 * Dijkstra's single source shortest path algorithm and friendship distances. The smaller the distance means the 
	 * closer the relationship. 
	 * 
	 * If weightedDist option is used, after computing the shortest path like in the dist option to all the other nodes 
	 * in the graph, multiply each distance with the total number of edges in the graph less the number of incoming edges 
	 * to that node. 
	 * 
	 * For instance, suppose the graph has a total of 10 edges. 
	 * 
	 * Suppose the shortest distance from personOfInterest to node A is 5 and there are 4 incoming edges to A, 
	 * the weighted distance is 5*(10-4)=30. The lower the weighted distance, the better the candidate.
	 * 
	 * This method considers both distance and popularity. The person with a lot of incoming edges 
	 * means that the person is likely more well-liked by other people and should be recommended.
	 * 
	 * Sort the distance/weighted distance in increasing order.
	 * 
	 * If there are less than topK candidates, return only those candidates.
	 * If there are more than topK candidates, return only the topK candidates when there are no other candidates with 
	 * the same distance/weighted distance as the last candidate in the topK list.
	 * If there are other candidates with the same distance/weighted distance as the last candidate in the topK list, 
	 * return all the candidates with the same distance. In this case, more than topK candidates are included in 
	 * the list.
	 *  
	 * @param g Graph of friendships
	 * @param personOfInterest Name of the person to recommend new friend candidates for
	 * @param option Either dist or weightedDist, which indicates whether to use 
	 * 				 the friendship distance or the weighted friendship distance 
	 *          
	 * @param topK Desirable maximum number of candidate friends to recommend
	 * @return List of candidate friends
	 */
	public static List<Edge<String, Integer>> recommendFriends(DiGraph<String> g, String personOfInterest, String option, int topK) {
		// TODO
		return null; // TODO
	}

}
