package edu.iastate.cs228.hw5;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Create a graph to model a social network of friendships. See the homework
 * description.
 * 
 * @author Darren Hushak
 * 
 */

public class SocialGraph {

	/**
	 * Create an empty graph aGraph. Parse each line, print the entire line on
	 * the console, and call the corresponding method.
	 * 
	 * The command is case sensitive. Assume that the file format is correct.
	 * 
	 * add arg1 arg2 arg3 call aGraph.addEdge(arg1, arg2,
	 * Integer.parseInt(arg3))
	 * 
	 * showFriends arg1 call aGraph.adjacentTo(arg1)
	 * 
	 * remove arg1 call aGraph.remove(arg1)
	 * 
	 * recommendFriends arg1 arg2 arg3 call the recommendFriends(aGraph, arg1,
	 * arg2, Integer.parseInt(arg3)) where arg1 is the name of the person to
	 * recommend new friends for; arg2 is either "dist" or "weightedDist"
	 * indicating the method to select people to recommend as new friends;
	 * Integer.parseInt(arg3) is the maximum number of new friends to recommend
	 * 
	 * @param args
	 *            args[0] Input filename with all the commands and arguments
	 */
	public static void main(String[] args) {

		// Create graph
		DiGraph<String> social = new DiGraph<String>();
		
		
		FileReader inputFile = null;
		try {
			// Get input file from args
			inputFile = new FileReader(args[0]);
		} catch (FileNotFoundException e) {
			// If file not found, print the directory that the user should place
			// the file in, then terminate
			String current;
			try {
				current = new java.io.File(".").getCanonicalPath();
				System.out.println("Place file in this directory:" + current);
			} catch (IOException e1) {
			}
			System.out.println("Terminating the program.");
			System.exit(-1);
		}

		
		
		
		// Start scanning
		Scanner sc = new Scanner(inputFile);
		while (sc.hasNextLine()) {
			String line;
			line = sc.nextLine(); // read the next line into a String object
			System.out.println(line);//Print command
			
			String[] sArr = line.split("\\s+"); // split the string by spaces
			
			if (sArr[0].equals("add")) {
						social.addEdge(sArr[1], sArr[2], Integer.parseInt(sArr[3]));
			}
			if (sArr[0].equals("remove")) {
				social.removeVertex(sArr[1]);
			}
			if (sArr[0].equals("showFriends")) {
				System.out.println(social.adjacentTo(sArr[1]));
			}
			if (sArr[0].equals("recommendFriends")) {
				System.out.println(recommendFriends(social, sArr[1], sArr[2], Integer.parseInt(sArr[3])));
			}
		}
	}

	/**
	 * Recommend topK (e.g., 5) best friend candidates who are not already a
	 * friend of personOfInterest
	 * 
	 * If dist option is used, find the shortest path from personOfInterest to
	 * all the other nodes in the graph using Dijkstra's single source shortest
	 * path algorithm and friendship distances. The smaller the distance means
	 * the closer the relationship.
	 * 
	 * If weightedDist option is used, after computing the shortest path like in
	 * the dist option to all the other nodes in the graph, multiply each
	 * distance with the total number of edges in the graph less the number of
	 * incoming edges to that node.
	 * 
	 * For instance, suppose the graph has a total of 10 edges.
	 * 
	 * Suppose the shortest distance from personOfInterest to node A is 5 and
	 * there are 4 incoming edges to A, the weighted distance is 5*(10-4)=30.
	 * The lower the weighted distance, the better the candidate.
	 * 
	 * This method considers both distance and popularity. The person with a lot
	 * of incoming edges means that the person is likely more well-liked by
	 * other people and should be recommended.
	 * 
	 * Sort the distance/weighted distance in increasing order.
	 * 
	 * If there are less than topK candidates, return only those candidates. If
	 * there are more than topK candidates, return only the topK candidates when
	 * there are no other candidates with the same distance/weighted distance as
	 * the last candidate in the topK list. If there are other candidates with
	 * the same distance/weighted distance as the last candidate in the topK
	 * list, return all the candidates with the same distance. In this case,
	 * more than topK candidates are included in the list.
	 * 
	 * @param g
	 *            Graph of friendships
	 * @param personOfInterest
	 *            Name of the person to recommend new friend candidates for
	 * @param option
	 *            Either dist or weightedDist, which indicates whether to use
	 *            the friendship distance or the weighted friendship distance
	 * 
	 * @param topK
	 *            Desirable maximum number of candidate friends to recommend
	 * @return List of candidate friends
	 */
	public static List<Edge<String, Integer>> recommendFriends(
			DiGraph<String> g, String personOfInterest, String option, int topK) {
		ArrayList<Edge<String, Integer>> ret = new ArrayList<Edge<String, Integer>>();
		
		
		return ret; // TODO
	}

}
