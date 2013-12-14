package edu.iastate.cs228.hw5;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.cs228.hw5.DiGraph;
import edu.iastate.cs228.hw5.Edge;

/**
 * Have Fun!
 * @author Eric Soland
 *
 */
public class DijkstraTests 
{
	String[] list; 
	
	@Before
	public void setup()
	{
		String[] arr = {"A","B","C","D","E","F","G"};
		list = arr;
	}
	
	@After
	public void cleanUp()
	{
		list = null;
	}
	
	@Test
	public void NullTests()
	{
		DiGraph<String> graph = new DiGraph<String>();
		int Size = 3;
		for(int n = 0; n < Size; n++)
		{
			graph.addEdge(list[n], list[n+1], n+1);
			graph.addEdge(list[n+1], list[n], n+1);
		}
		graph.addEdge(list[0], list[Size], Size+1);
		graph.addEdge(list[Size], list[0], Size+1);
		
		String vertex = list[Size+1];
		String vertex2 = list[Size+2];
		graph.addVertex(vertex);
		
		boolean[] sets = new boolean[3];
		String[] msgs = {"A source vertex that is not in the graph should result in an empty map",
				"A null source vertex should result in an empty map",
				"A vertex that has no adjacent edges should result in an empty map"};
		sets[0] = graph.Dijkstra(vertex2).isEmpty();
		sets[1] = graph.Dijkstra(null).isEmpty();
		sets[2] = graph.Dijkstra(vertex).isEmpty();
		
		for(int n = 0; n < sets.length; n++)
		{
			assertEquals("Set" + n + ": " + msgs[n],true,sets[n]);
		}
		
	}
	
	@Test
	public void test1()
	{
		DiGraph<String> graph = new DiGraph<String>();
		int Size = 3;
		for(int n = 0; n < Size; n++)
		{
			graph.addEdge(list[n], list[n+1], n+1);
			graph.addEdge(list[n+1], list[n], n+1);
		}
		graph.addEdge(list[0], list[Size], Size+1);
		graph.addEdge(list[Size], list[0], Size+1);
		
		Map<String,Integer> yourMap = graph.Dijkstra(list[0]);
		//[ <D,4> , <A,0> , <B,1> , <C,3> ]
		Map<String,Integer> myMap = new HashMap<String,Integer>();
		myMap.put(list[0], 0);
		myMap.put(list[1], 1);
		myMap.put(list[2], 3);
		myMap.put(list[3], 4);
		
		Set<Entry<String, Integer>> mySet = myMap.entrySet();
		Set<Entry<String, Integer>> yourSet = yourMap.entrySet();
		
		boolean areEqual = mySet.containsAll(yourSet) && yourSet.containsAll(mySet) ;
		
		if(!areEqual)
		{
			System.out.println("Test1\nMyMap: " + MapToString(myMap) + "\nYourMap: " + MapToString(yourMap));
		}
		assertEquals("See Console for output",true,areEqual);
	}
	
	@Test
	public void test2()
	{
		DiGraph<String> graph = new DiGraph<String>();
		
		int SRC = 0;
		int DST = 2;
		int COST = 1;
		
		int[][] grid = {{0,0,1,1,2,2,3,3,4,4}, //Source indices
						{5,3,4,4,1,2,1,6,3,2}, //costs  
						{1,3,2,4,0,4,2,4,0,2}};//Destinations indices
		
		for(int n = 0; n < grid[SRC].length; n++)
		{
			graph.addEdge(list[grid[SRC][n]], list[grid[DST][n]], grid[COST][n]);
		}
		
		Map<String,Integer> yourMap = graph.Dijkstra(list[0]);
		//[ <D,4> , <A,0> , <B,1> , <C,3> ]
		Map<String,Integer> myMap = new HashMap<String,Integer>();
		
		int[][] myGrid = {{0,1,2,3,4},  //sources
						  {0,5,4,3,6}}; //Costs
		
		for(int n = 0; n < myGrid[SRC].length; n++)
		{
			myMap.put(list[myGrid[SRC][n]], myGrid[COST][n]);
		}
		
		Set<Entry<String, Integer>> mySet = myMap.entrySet();
		Set<Entry<String, Integer>> yourSet = yourMap.entrySet();
		boolean areEqual = mySet.containsAll(yourSet) && yourSet.containsAll(mySet);
		
		if(!areEqual)
		{
			System.out.println("Test2\nGraph: "+GraphToString(graph)+"\n\nMyMap: " + MapToString(myMap) + "\nYourMap: " + MapToString(yourMap));
		}
		assertEquals("See Console for output",true,areEqual);
	}
	
	@Test
	public void test3()
	{
		DiGraph<String> graph = new DiGraph<String>();
		
		int SRC = 0;
		int DST = 2;
		int COST = 1;
		
		int[][] grid = {{0,0,1,1,2,2,3,4}, //Source indices
						{1,4,2,1,2,1,3,1}, //costs  
						{1,3,5,2,5,3,4,3}};//Destinations indices
		
		for(int n = 0; n < grid[SRC].length; n++)
		{
			graph.addEdge(list[grid[SRC][n]], list[grid[DST][n]], grid[COST][n]);
		}
		
		Map<String,Integer> yourMap = graph.Dijkstra(list[0]);
		
		Map<String,Integer> myMap = new HashMap<String,Integer>();
		
		int[][] myGrid = {{0,1,2,3,4,5},  //sources
						  {0,1,2,3,6,3}}; //Costs
		
		for(int n = 0; n < myGrid[SRC].length; n++)
		{
			myMap.put(list[myGrid[SRC][n]], myGrid[COST][n]);
		}
		
		Set<Entry<String, Integer>> mySet = myMap.entrySet();
		Set<Entry<String, Integer>> yourSet = yourMap.entrySet();
		boolean areEqual = mySet.containsAll(yourSet) && yourSet.containsAll(mySet);
		
		if(!areEqual)
		{
			System.out.println("Test3\nGraph: \n"+GraphToString(graph)+"\n\nMyMap: " + MapToString(myMap) + "\nYourMap: " + MapToString(yourMap));
		}
		assertEquals("See Console for output",true,areEqual);
	}
	
	@Test
	public void test4()
	{
		DiGraph<String> graph = new DiGraph<String>();
		
		int SRC = 0;
		int DST = 2;
		int COST = 1;
		
		int[][] grid = {{0,0,0,1,2,3,3,3,5,6}, //Source indices
						{2,2,1,1,1,1,1,3,1,5}, //costs  
						{1,2,3,4,5,2,4,5,2,4}};//Destinations indices
		
		for(int n = 0; n < grid[SRC].length; n++)
		{
			graph.addEdge(list[grid[SRC][n]], list[grid[DST][n]], grid[COST][n]);
		}
		
		Map<String,Integer> yourMap = graph.Dijkstra(list[0]);
		
		Map<String,Integer> myMap = new HashMap<String,Integer>();
		
		int[][] myGrid = {{0,1,2,3,4,5},  //sources
						  {0,2,2,1,2,3}}; //Costs
		
		for(int n = 0; n < myGrid[SRC].length; n++)
		{
			myMap.put(list[myGrid[SRC][n]], myGrid[COST][n]);
		}
		
		Set<Entry<String, Integer>> mySet = myMap.entrySet();
		Set<Entry<String, Integer>> yourSet = yourMap.entrySet();
		boolean areEqual = mySet.containsAll(yourSet) && yourSet.containsAll(mySet);
		
		if(!areEqual)
		{
			System.out.println("Test4\nGraph: \n"+GraphToString(graph)+"\n\nMyMap: " + MapToString(myMap) + "\nYourMap: " + MapToString(yourMap));
		}
		assertEquals("See Console for output",true,areEqual);
	}
	
	@Test
	public void test5()
	{
		DiGraph<String> graph = new DiGraph<String>();
		
		int SRC = 0;
		int DST = 2;
		int COST = 1;
		
		int[][] grid = {{0,0,1,1,1,1,2,2,2,3,3,3,3,3,4,4,5,5}, //Source indices
						{3,3,2,1,1,3,2,1,1,3,2,4,4,4,2,1,1,2}, //costs  
						{1,3,0,2,3,5,0,1,4,0,1,2,4,5,1,5,2,3}};//Destinations indices
		
		for(int n = 0; n < grid[SRC].length; n++)
		{
			graph.addEdge(list[grid[SRC][n]], list[grid[DST][n]], grid[COST][n]);
		}
		
		Map<String,Integer> yourMap = graph.Dijkstra(list[0]);
		
		Map<String,Integer> myMap = new HashMap<String,Integer>();
		
		int[][] myGrid = {{0,1,2,3,4,5},  //sources
						  {0,3,4,3,5,6}}; //Costs
		
		for(int n = 0; n < myGrid[SRC].length; n++)
		{
			myMap.put(list[myGrid[SRC][n]], myGrid[COST][n]);
		}
		
		Set<Entry<String, Integer>> mySet = myMap.entrySet();
		Set<Entry<String, Integer>> yourSet = yourMap.entrySet();
		boolean areEqual = mySet.containsAll(yourSet) && yourSet.containsAll(mySet);
		
		if(!areEqual)
		{
			System.out.println("Test5\nGraph: \n"+GraphToString(graph)+"\n\nMyMap: " + MapToString(myMap) + "\nYourMap: " + MapToString(yourMap));
		}
		assertEquals("See Console for output",true,areEqual);
	}
	
	//***********************************************************************************************************
	//***********************************************************************************************************
	//
	//						Beginning of Utility Methods
	//
	//***********************************************************************************************************
	//***********************************************************************************************************
	
	/**
	 * This method puts a Graph into a string
	 * @param graph
	 * the DiGraph that you implemented
	 * @return
	 * the string of the digraph
	 */
	public static <E> String GraphToString(DiGraph<E> graph)
	{
		if(graph == null) return "This graph is null";
		
		String result = "";
		Set<E> set = graph.vertices();
		
		Iterator<E> iter1 = set.iterator();
		while(iter1.hasNext())
		{
			E vertex = iter1.next();
			Set<Edge<E, Integer>> EdgeSet = graph.adjacentTo(vertex);
			result = result + vertex.toString() + " --> [ ";
			Iterator<Edge<E, Integer>> iter2 = EdgeSet.iterator();
			boolean hasDoneOne = false;
			while(iter2.hasNext())
			{
				Edge<E, Integer> edge = iter2.next();
				if(hasDoneOne)result = result + " , ";
				result = result + edge;
				hasDoneOne = true;
			}
			result = result + " ]\n";
		}
		
		return result;
	}
	
	/**
	 * This method returns a string verson of a map
	 * The value is assumed not to be a HashMap
	 * @param map
	 * Any Map
	 * @return
	 * the string version of the map
	 */
	public static <K,V> String  MapToString(Map<K,V> map)
	{
		Set<Entry<K, V>> set1 = map.entrySet();
		String result = "[ ";
		Iterator<Entry<K, V>> iter1 = set1.iterator();
		boolean hasDoneOne = false;
		while(iter1.hasNext())
		{
			Entry<K, V> entry = iter1.next();
			if(hasDoneOne) result = result + " , ";
			result = result + "<" + entry.getKey().toString() +","+entry.getValue().toString() + ">";
			hasDoneOne = true;
		}
		result = result + " ]";
		return result;
	}
}
