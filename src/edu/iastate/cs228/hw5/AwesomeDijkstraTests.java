package edu.iastate.cs228.hw5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import edu.iastate.cs228.hw5.DiGraph;
import edu.iastate.cs228.hw5.Edge;

/**
 * Feel free to mess with the selected variables
 * @author Eric Soland
 *
 */
public class AwesomeDijkstraTests 
{
	private static final int TIME = (int) System.currentTimeMillis();
	
	//***********************************************************************************
	//***********************************************************************************
	//************************************TODO - feel free to change these variables
	/**
	 * This is the number of tests that this program will run through
	 * It is highly recommended to set really high to find more errors
	 */
	private static final int RUNS = 100000; 
	
	/**
	 * This is the possible number of Vertices, and the possible adjacent edges
	 */
	private static final int SIZE = 20; //Large if SIZE > 10, really large if SIZE >>> 20
	
	/**
	 * This is the seed that is printed to the console,
	 * It can be changed to a constant to get the same results
	 */
	private static final int SEED =  TIME;
	
	/**
	 * This program won't make an edge for all possible edges,
	 * Set this low to increase error finding chance,
	 * 
	 * If it is set at 0, it will try to include every possible edge
	 * If it is set at 100, it will not include edges
	 */
	private static final int PERCENT_CHANCE_TO_SKIP_AN_EDGE = 50; //<100
	
	/**
	 * This is the Maximum Edge Cost
	 */
	private static final int MAXEDGECOST = 4;

	/**
	 * This makes the program skip a bunch more edges that would simply be awkward and generate errors from this program,
	 * but it significantly decreases the chance of finding errors
	 */
	private static final boolean No_MyFails = true;
	
	/**
	 * This makes the program show fails, may or may not show MyFails, which are not important anyway
	 */
	private static final boolean ShowAnyFails = true; 
	//***********************************************************************************
	//***********************************************************************************
	//***********************************************************************************
	/**
	 * This is the Random Number generator
	 */
	private static final Random rnd = new Random(SEED);
	
	/**
	 * This boolean shows MyFails
	 */
	private static final boolean ShowAllFails = false;
	
	private static final int UPDATE = 1;
	private static Node[] nodeArray;
	private static DiGraph<Integer> graph;
	private static Map<Integer,Integer> myMap;
	private static int hasRun; // 3 means it has run
//	private static String error;
	private static int Start;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) 
	{
		int types = 4;
		int[] tests = new int[types];
		String[] titles = {"Major Fail: ", "Small Fail: ","Perfect: ","MyFail? : "};
		
		for(int n = 0; n < RUNS; n++)
		{
			tests[test(n)]++;
		}
		
		System.out.println();
		System.out.println("*******Test Report************************");
		for(int n = 0; n < tests.length; n++)
		{
			System.out.println(titles[n] + tests[n] + "/" + RUNS);
		}
		
		System.out.println();
		
		if(tests[0] > 0)
		{
			System.out.println("You have the incorrect number of Vertices");
		}
		if(tests[1] > 0)
		{
			System.out.println("Your some of your costs are too high, \ntry considering a case where you changed a vertex \nthat you already looked at that has edges");
		}
		if(tests[3] > 0)
		{
			System.out.println("I think the MyFails are my fault, don't worry about them");
		}
		if(tests[2] > RUNS * 99 / 100 && tests[2] < RUNS)
		{
			System.out.println("Almost all tests are perfect!");
		}

		if(!No_MyFails&&tests[2] == RUNS)
		{
			System.out.println("I think that I failed, switch the \"No_MyFails boolean to true\", and try again.");
		}
		
		if (tests[2] == RUNS)
		{
			System.out.println("All tests are perfect!! Run this program again to try to find more errors.");
		}
		
		
		System.out.println("\nSeed = " + SEED);
		
	}
	
	public static void setup()
	{
		//if(hasRun != 3) {}
		
		graph = new DiGraph<Integer>();
		
		nodeArray = new Node[SIZE];
		
		Integer[] Indices = new Integer[SIZE];
		
		for(int n = 0; n < SIZE; n++)
		{
			Indices[n] = new Integer(n);
		}
		
		randomize(Indices, rnd);
		
		
		//Start = 0;
		Start = SIZE -1;
		
		nodeArray[Start] = new Node(Start,-2, 0, -2);
		
		//This is the arrayList that will store all of the edges
		ArrayList<Integer[]> EdgeList = new ArrayList<Integer[]>();
		
		for(int n = 0; n < SIZE; n++)
		{
			if(Indices[n]==Start)continue;
			nodeArray[Indices[n]] = new Node(Indices[n],-1,-1,-1);
		}
//		for(int src = 0; src < SIZE; src++)
		for(int src = SIZE-1; src >= 0; src--)
		{	
			Node SRC = nodeArray[src];
			
			if(SRC.Parent == -1) 
				{	//if(rnd.nextInt(101)<PERCENT)list2.addLast(src);
					continue;}
			
//			for(int dst = 0; dst < SIZE; dst++)
			for(int dst = SIZE-1; dst >= 0; dst--)
			{
				Node DST = nodeArray[dst];
				
				if(dst==src||rnd.nextInt(101)<PERCENT_CHANCE_TO_SKIP_AN_EDGE)continue; //Randomly Skip edges, and edges to itself
				else if(graph.hasEdge(SRC.ID, DST.ID)) continue;
//				else if(dst < src && DST.Parent == -1) continue;
				else if(dst > src && DST.Parent == -1) continue;
				
				int edgeCost = rnd.nextInt(MAXEDGECOST)+1;	
				
				 //This line makes program more inefficient at finding errors,
				 //but it will never generate a wrong line,
				 //If the destination node has a lower cost than the source node, skip that edge
				if(No_MyFails&&SRC.Cost + edgeCost < DST.Cost){ continue;}
				
				//graph.addEdge();
				
				//Add all of the edges onto the ArrayList
				Integer[] arr = {SRC.ID, DST.ID, edgeCost};
				EdgeList.add(arr);

				DST.set(SRC.ID, edgeCost);
			}
		}
		
		//Randomize the ArrayList
		randomize(EdgeList,rnd);
		
		//Put all the Randomized edges into the ArrayList
		for(int n = 0; n < EdgeList.size(); n++)
		{
			Integer[] X = EdgeList.get(n);
			graph.addEdge(X[0],X[1],X[2]);
		}
		
		for(int k = 0; k < UPDATE; k++)
		{
			for(int n = 0; n < nodeArray.length; n++)
			{  nodeArray[n].update();  }
		}
		
		
		myMap = new HashMap<Integer, Integer>();
		for(int n = 0; n < SIZE; n++)
		{
			if(nodeArray[n].Parent == -1 || nodeArray[n].Cost == -1) continue;
			myMap.put(n,nodeArray[n].Cost);
		}
	}
	
	/**
	 * This randomizes a generic list
	 * @param arr
	 * the list
	 * @param rnd
	 * the random number generator
	 */
	public static <E> void randomize(List<E> arr, Random rnd)
	{
		for(int n = arr.size()-1; n> 0; n--)
		{
			int die = rnd.nextInt(n);
			E value = arr.get(die);
			arr.set(die, arr.get(n));
			arr.set(n, value);
		}
	}
	
	public static void cleanUp()
	{
		nodeArray = null;
		graph = null;
		myMap = null;
		hasRun = 3;
	}
	
	
	public static int test(int n)
	{
		setup();
			
		Map<Integer, Integer> yourMap = graph.Dijkstra(Start);
			
		Set<Entry<Integer, Integer>> tuya = yourMap.entrySet();
		Set<Entry<Integer, Integer>> mia = myMap.entrySet();
			

		boolean isTrue = tuya.containsAll(mia);
		isTrue = (isTrue && mia.containsAll(tuya));
		
		int TestType = 2;
		if(!isTrue)
		{
			TestType = CompareSets(mia,tuya);
			
			if(!ShowAllFails&&TestType == 3) {return TestType;}
//			else if(ShowAllFails)
//			{
//				//System.out.println("Nodes ==>"+ArrayToString(nodeArray));
//			}
			
			if(ShowAnyFails)
			{
				System.out.println("Run: " + n);
				//System.out.println(error);
				System.out.println("Graph: "+GraphToString(graph));
				System.out.println("YourMap: "+MapToString(yourMap));
				System.out.println("MyMap: "+MapToString(myMap));
				System.out.println("\n************************************************************\n");
			}
		}
		//assertEquals("See Console: When MyMap is compared to YourMap this is",true,isTrue);
		return TestType;
	}
	
	private static class Node
	{
		public int Parent;
		public int LastEdgeCost;
		public int Cost;
		public int ID;
		//public Map<Integer,Integer> IncomingEdges;
		public Node(int id, int par, int cost, int edge)
		{
			Parent = par;
			Cost = cost;
			LastEdgeCost = edge;
			ID = id;
		}
		
		public boolean set(int par,int edge)
		{			
			int cost = nodeArray[par].Cost + edge;
			if(Cost > cost || Cost < 0)
			{
				Parent = par;
				Cost = cost;
				LastEdgeCost = edge;
				update();
				return true;
			}
			
			return false;
		}
		
		public void update()
		{		
			for(int n = 0; n < SIZE; n++)
			{
				if(nodeArray[n].Parent == ID)
				{
					nodeArray[n].set(ID,nodeArray[n].LastEdgeCost);
				}
			}	
		}
		
		@Override
		public String toString()
		{
			return ID + ": P<"+Parent + "> C<" +Cost+"> " + " E<" + LastEdgeCost + ">"  ;
		}
	}
	
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
	
	/**
	 * This method returns a string verson of a map
	 * The value is assumed not to be a HashMap
	 * @param map
	 * Any Map
	 * @return
	 * the string version of the map
	 */
	public static <E> String  ArrayToString(E[] arr)
	{
		String result = "[ ";
		
		boolean hasDoneOne = false;
		for(int n = 0; n < arr.length; n++)
		{
			if(hasDoneOne) result = result + " , ";
			result = result + arr[n];
			hasDoneOne = true;
		}
			
		result = result + " ]";
		return result;
	}
	
	/**
	 * Assuming there are no duplicates, this class compares two entry sets,
	 * The first integer is the vertex, the second is the cost
	 * 
	 * If the entries with the same vertex have the same (0), greater, lower of cost, output the corresponding number
	 * If a vertex is included in one but not the other, return -2
	 * 
	 * If an entry of TuSet has any cost greater than MiSet, return -1 immediately
	 * but if an entry of MiSet has only greater of equal cost, return 1 at the end
	 * 
	 * Return:
	 * 3, tuya < mia
	 * 1, tuya > mia
	 * 2 tuya == mia
	 * 0 tuya != mia
	 */
	public static int CompareSets(Set<Entry<Integer, Integer>> MiSet, Set<Entry<Integer, Integer>> TuSet)
	{
		if(MiSet.size() != TuSet.size())return 0;
		
		Iterator<Entry<Integer, Integer>> MiIter = MiSet.iterator();	
		
		int result = 2;
		
		while(MiIter.hasNext())
		{
			Entry<Integer, Integer> MiEntry = MiIter.next();
			
			Iterator<Entry<Integer, Integer>> TuIter = TuSet.iterator();
			
			boolean foundKey = false;
			while(TuIter.hasNext())
			{
				Entry<Integer, Integer> TuEntry = TuIter.next();
				
				if(MiEntry.getKey() == TuEntry.getKey())
				{
					foundKey = true;
					
					if(MiEntry.getValue() == TuEntry.getValue()) break;
					else if(MiEntry.getValue() > TuEntry.getValue())
					{
						result = 3;
						break;
					}
					else if(MiEntry.getValue() < TuEntry.getValue())
						return 1;
					
				}
			}
			
			if(!foundKey)return 0;
		}	
		return result;		
	}
	
	/**
	 * This randomizes a generic array
	 * @param arr
	 * the array
	 * @param rnd
	 * the random number generator
	 */
	public static <E> void randomize(E[] arr, Random rnd)
	{
		for(int n = arr.length-1; n > 0; n--)
		{
			int die = rnd.nextInt(n);
			E value = arr[die];
			arr[die] = arr[n];
			arr[n] = value;
			
		}
	}
}
