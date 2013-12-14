package edu.iastate.cs228.hw5;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.iastate.cs228.hw5.DiGraph;
import edu.iastate.cs228.hw5.Edge;

/**
 * This test class uses a random number generator,
 * The output for the tests is the seed for the random number generator,
 * if there is an error, swap that number for CURRENTTIME.  
 * 
 * IMPORTANT:
 * These do not test the Dijkstra method
 * 
 * @author Eric Soland
 *
 */
public class DiGraphTests 
{
	//Note:  Do not set this value too high, unless you want to wait
	//private final int SIZE = 20; //Intentionally not static;
	private final int SIZE = 5;
	
	ArrayList<Integer> list;
	ArrayList<Integer[]> edgeList;
	static final int CURRENTTIME = (int) System.currentTimeMillis();
	static final int SEED = CURRENTTIME;
	static final Random rnd = new Random(SEED);
	DiGraph<Integer> digraph;
	private final static int SRC = 0;
	private final static int DST = 1;
	private final static Integer COST = 5;
	private int Max;
	private int Min;
	
	private static final boolean MyDebugMode = true;
	
	private static int hasStarted; //3 = yes
	
	@Before
	public void setup()
	{
		if ( hasStarted != 3 ) { System.out.println(SEED); }
		
		list = makeNonRepeatingIntegerArrayList(SIZE,8,rnd);
		Min = list.get(0);
		Max = list.get(SIZE - 1);
		edgeList = new ArrayList<Integer[]>();
		randomize(list,rnd);
		digraph = new DiGraph<Integer>();
		
		for(int n = 0; n < SIZE; n++)
		{
			for(int k = 0; k < SIZE; k++) // This outter loop creates more edges from the same source
			{
				if(k == n) continue; //Can't be the same 
				
				boolean isTrue = digraph.addEdge(list.get(n), list.get(k), COST);
				if(!isTrue) throw new RuntimeException("Get the addEdge Method to return true when I use it or I refuse to work!!!");
				
				Integer[] arr = new Integer[2];
				arr[SRC] = list.get(n);
				arr[DST] = list.get(k);
				
				edgeList.add(arr);
				
				if(rnd.nextInt(101) > 80) break; // This randomizes the ammount of edges going out of a source node
			}
		}
	}
	
	@After
	public void cleanUp()
	{
		list = null;
		edgeList = null;
		digraph = null;
		Min = -100;
		Max = -100;
		hasStarted = 3;
	}
	
	/**
	 * The purpose of this test is add a vertex and see if the Graph still has it.  
	 */
	@Test
	public void simpleAddVertexAndHasVertexTest()
	{
		Random rnd = new Random(System.currentTimeMillis());
		ArrayList<Integer> list = makeNonRepeatingIntegerArrayList(SIZE,20,rnd);
		randomize(list,rnd);
		DiGraph<Integer> d = new DiGraph<Integer>();
		
		for(int n = 0; n < SIZE; n++)
		{
			assertEquals("There are no values in the Graph yet",false,d.hasVertex(list.get(n)));
		}
		
		for(int n = 0; n < SIZE; n++)
		{
			
			for(int k = n + 1; k < SIZE; k++)
			{
				assertEquals("Has not added this yet",false,d.hasVertex(list.get(k)));
			}
			
			d.addVertex(list.get(n));
			assertEquals("The DiGraph should have the vertex it just added",true,d.hasVertex(list.get(n)));
		}
	}
	
	/**
	 * The purpose of this test is add an edge and see if the Graph still has it.  
	 */
	@Test
	public void CheckEdge_Test1()
	{	
		for(int n = 0; n < edgeList.size(); n++)
		{
			Integer[] arr = edgeList.get(n);
			boolean isTrue = digraph.hasEdge(arr[SRC],arr[DST]);
			assertEquals("Should have the edge that was added",true,isTrue);
		}
	}
	
	/**
	 * The purpose of this test is add the same edge and see if the graph has only changed for one.  
	 */
	@Test
	public void EdgeDuplicator_Test()
	{	
		int index = rnd.nextInt(list.size());
		Integer src = list.get(index);
		index = rnd.nextInt(list.size());
		Integer dst = list.get(index);
		while (src == dst)
		{
			index = rnd.nextInt(list.size());
			dst = list.get(index);
		}
		
		int NumOEdges = digraph.numEdges();
		if(!digraph.hasEdge(src, dst)){NumOEdges ++;}
		
		digraph.addEdge(src, dst, COST);
		
		for(int n = 0; n < 5; n++)
		{
			digraph.addEdge(src, dst, COST);
		}
		
		assertEquals(true,digraph.hasEdge(src, dst));
		assertEquals(NumOEdges,digraph.numEdges());
		
	}
	
	/**
	 * The purpose of this test is add an edge and see if the Graph still has it.  
	 */
	@Test
	public void Sizes_Test1()
	{	
		assertEquals(edgeList.size(),digraph.numEdges());
		assertEquals(list.size(),digraph.numVertices());
	}
	
	/**
	 * The purpose of this test adding something that shouldn't be added  
	 */
	@Test
	public void FalseAdd_Test1()
	{	
		Boolean[] AllFalse = new Boolean[9];
		AllFalse[0] = digraph.addEdge(null, null, null);
		AllFalse[1] = digraph.addVertex(null);
		AllFalse[2] = digraph.addVertex(list.get(rnd.nextInt(list.size())));
		AllFalse[3] = digraph.addEdge(list.get(rnd.nextInt(list.size())), null, null);
		AllFalse[4] = digraph.addEdge(null,list.get(rnd.nextInt(list.size())), null);
		AllFalse[5] = digraph.addEdge(list.get(rnd.nextInt(list.size())),list.get(rnd.nextInt(list.size())), 0);
		AllFalse[6] = digraph.addEdge(list.get(rnd.nextInt(list.size())),list.get(rnd.nextInt(list.size())), -1);
		Integer num = list.get(rnd.nextInt(list.size()));
		AllFalse[7] = digraph.addEdge(num,num, COST);
		AllFalse[8] = digraph.addEdge(5,5, COST);
		
		for(int n = 0; n < AllFalse.length; n++)
		{
			//System.out.println("AllFalse["+ n +"]: " + AllFalse[n]);
			assertEquals("This method should return false: method" + n, false, AllFalse[n] );
		}
		
		assertEquals(edgeList.size(),digraph.numEdges());
		assertEquals(list.size(),digraph.numVertices());
	}
	
	
	
	/**
	 * The purpose of this test is to test the IncomingEdges method.  
	 */
	@Test
	public void IncomingEdge_Test1()
	{	
		for(int n = Min; n < Max; n++)
		{
			Integer DstVertex = n;
			Set<Integer> set = digraph.incomingEdges(DstVertex);
			
			ArrayList<Integer> incomingVerticesList = new ArrayList<Integer>();
			
			//Get incoming edges from arraylist edgeList
			for(int k = 0; k < edgeList.size(); k++)
			{
				Integer[] pair = edgeList.get(k);
				if (pair[DST].equals(DstVertex))
				{
					incomingVerticesList.add(pair[SRC]);
				}
			}
			
			boolean isTrue = set.containsAll(incomingVerticesList);
			assertEquals("The source vertices are supposed to be in the returned set",true,isTrue);
			
		}
	}
	
	/**
	 * The purpose of this test is to test the IncomingEdges method.
	 * This adds a new edge with a node far off to a current node, the incoming edges should increase  
	 */
	@Test
	public void IncomingEdge_Test2()
	{	
		int index = rnd.nextInt(list.size());
		Integer Vertex = list.get(index);
		
		int IncomingEdges = digraph.incomingEdges(Vertex).size();
		
		Integer src = Integer.MIN_VALUE;
		digraph.addEdge(src, Vertex, COST);
		
		int IncomingEdges2 = digraph.incomingEdges(Vertex).size();
		assertEquals("Should have the vertex it just added",true,digraph.hasEdge(src, Vertex));	
		assertEquals("Adding a new Edge from a far off source should change incoming edges",IncomingEdges+1,IncomingEdges2);
	}
		
	/**
	 * The purpose of this test is to test the RemoveVertex by comparing the sizes of the edges and vertices.  
	 */
	@Test
	public void RemoveVertex_Test1()
	{	
		int index = rnd.nextInt(list.size());
		Integer Vertex = list.get(index);
		
		assertEquals("The number of Vertices is wrong", list.size(), digraph.numVertices());
		assertEquals("The number of Edges is wrong", edgeList.size(), digraph.numEdges());
		
		if(MyDebugMode){
			digraph.print(); //OptionalMethod that prints the Graph
			this.printTestGraph();
			System.out.println("Remove: " + Vertex);
		}
		
		int numAdjacent = digraph.adjacentTo(Vertex).size();
		int numIncoming = digraph.incomingEdges(Vertex).size();
		
		boolean isTrue = digraph.removeVertex(Vertex);
		assertEquals("The RemoveMethod should think it works", true, isTrue);
		
		if(MyDebugMode){
			digraph.print(); //OptionalMethod that prints the Graph
			this.printTestGraph();
		}
		
		Set<Integer> set = digraph.vertices();
		boolean isFalse = set.contains(Vertex);
		assertEquals("The digraph should not contain the vertex that was just removed", false, isFalse);
		
		int NumIncomingRemoves = 0;
		int NumAdjacentRemoves = 0;
		list.remove(Vertex); //Remove Vertex from my ArrayList System
		ArrayList<Integer[]> toRemove = new ArrayList<Integer[]>();
		for(int n = 0; n < edgeList.size(); n++) //Remove all edges that contain that vertex
		{
			Integer[] pair = edgeList.get(n);
			if(pair[DST].equals(Vertex))
			{
				toRemove.add(pair);
				NumIncomingRemoves = NumIncomingRemoves + 1;
				
			}
			else if(pair[SRC].equals(Vertex))
			{
				toRemove.add(pair);
				NumAdjacentRemoves = NumAdjacentRemoves + 1;
			}
		}
		
		for(int n = 0; n < toRemove.size(); n++)
		{
			edgeList.remove(toRemove.get(n));
		}
		
		Set<Integer> Vset = digraph.vertices();
		assertEquals("The Size of Vertex Set Recieved is wrong", list.size(), Vset.size());
		
		assertEquals("The number of Vertices is wrong", list.size(), digraph.numVertices());
		assertEquals("The number of Edges from IncomingEdges is wrong",NumIncomingRemoves, numIncoming );
		assertEquals("The number of Edges from adjacentTo is wrong",NumAdjacentRemoves, numAdjacent );
		assertEquals("The number of Edges is wrong", edgeList.size(), digraph.numEdges());
		
	}
	
	/**
	 * The purpose of this test is to test the AdjacentTo method.  
	 */
	@Test
	public void AdjacentEdge_Test1()
	{	
		if(MyDebugMode){
			//System.out.println("\nAdjacentTo Graph:");
			digraph.print(); //OptionalMethod that prints the Graph
			this.printTestGraph();
		}
		
		Iterator<Integer> iter = list.iterator();
		while(iter.hasNext())
		{
			Integer SRCVertex = iter.next();
			Set<Edge<Integer, Integer>> set = digraph.adjacentTo(SRCVertex);
			
			ArrayList<Edge<Integer, Integer>> adjecentEdgeList = new ArrayList<Edge<Integer, Integer>>();
			
			//Get incoming edges from arraylist edgeList
			for(int k = 0; k < edgeList.size(); k++)
			{
				Integer[] pair = edgeList.get(k);
				if (pair[SRC].equals(SRCVertex))
				{
					adjecentEdgeList.add(new Edge<Integer, Integer>(pair[DST],COST));
				}
			}
			
//			Iterator<Integer[]> iter = edgeList.iterator();
//			while(iter.hasNext())
//			{
//				Integer[] pair = iter.next();
//				if (pair[SRC].equals(SRCVertex))
//				{
//					adjecentEdgeList.add(new Edge<Integer, Integer>(pair[SRC],COST));
//				}
//			}
			
			boolean isTrue = set.containsAll(adjecentEdgeList);
			if(!isTrue)
			{
				System.out.println(CollectionToString(set, "Your Edge List"));
				System.out.println(CollectionToString(adjecentEdgeList, "My Edge List"));
			}
			assertEquals("The source vertices are supposed to be in the returned set",true,isTrue);
			
		}
	}
	
	//***********************************************************************************************************
	//***********************************************************************************************************
	//
	//						Beginning of Utility Methods
	//
	//***********************************************************************************************************
	//***********************************************************************************************************
	
	/**
	 * This method prints a list
	 * @param list
	 * the list
	 * @param title
	 * a title
	 */
	public static <E> String CollectionToString(Collection<E> list,String title)
	{
		String result = title + ":";
		boolean hasDoneOne = false;
		Iterator<E> iter = list.iterator();
		while(iter.hasNext())
		{
			if(hasDoneOne) result = result + " , ";
			result = result + iter.next().toString();
			hasDoneOne = true;
		}
		return result;
	}
	
	/**
	 * This method arbitrarily creates an increasing non-repeating arrayList
	 * @param size
	 * the size of the array
	 * @param up
	 * the max size of the interval between values
	 * @param rnd
	 * the random number generator
	 * @return
	 * return an arraylist of non repeating values
	 */
	public static ArrayList<Integer> makeNonRepeatingIntegerArrayList(int size,int up, Random rnd)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		int value = 1;
		for(int n = 0; n < size; n++)
		{
			value = value + rnd.nextInt(up) + 1;
			Integer Value = new Integer(value);
			list.add(Value);
		}
				
		return list;
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
		for(int n = arr.length-1; n> 0; n--)
		{
			int die = rnd.nextInt(n);
			E value = arr[die];
			arr[die] = arr[n];
			arr[n] = value;
			
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
	
	/**
	 * This method prints what the graph should look like purely contained by this test class 
	 */
	public void printTestGraph()
	{
		System.out.println("My TestGraph with ArrayLists");
		
		for(int n = 0; n < list.size(); n++)
		{
			System.out.print(list.get(n) + " --> [ ");
			boolean hasDoneOne = false;
			for(int k = 0; k < edgeList.size(); k++)
			{
				if(edgeList.get(k)[SRC].equals(list.get(n)))
				{
					if(hasDoneOne)
					{System.out.print(" , ");}
					System.out.print(edgeList.get(k)[DST]);
					if(edgeList.get(k)[DST].toString().equals(""))throw new RuntimeException();
					hasDoneOne = true;
				}
			}
			
			System.out.println(" ]");
			
		}
		System.out.println();
	}
}
