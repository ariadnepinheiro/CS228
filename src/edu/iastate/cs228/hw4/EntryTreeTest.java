package edu.iastate.cs228.hw4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EntryTreeTest {
	private PrintStream old;
	protected ByteArrayOutputStream newout;

	@Before
	public void setUpStreams() {
		// Create a stream to hold the output
		newout = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(newout);
		// save old stream
		old = System.out;
		// Tell Java to use your special stream
		System.setOut(ps);
	}

	@After
	public void cleanUpStreams() {
		System.setOut(old);
	}

	EntryTree<Integer, String> tree = new EntryTree<Integer, String>();

	// Test for created head node with no siblings, parent, or children
	@Test
	public void creationTest() {
		assertEquals(tree.root.key(), null);
		assertEquals(tree.root.value(), null);
		assertEquals(tree.root.parent(), null);
		assertEquals(tree.root.child(), null);
		assertEquals(tree.root.next(), null);
		assertEquals(tree.root.prev(), null);
	}

	// Check all links in an add
	@Test
	public void addOneChildTest() {
		Integer[] path = new Integer[1];
		path[0] = (Integer) 1;
		tree.add(path, "Test");

		assertEquals(tree.root.key(), null);
		assertEquals(tree.root.value(), null);
		assertEquals(tree.root.parent(), null);
		assertNotEquals(tree.root.child(), null);
		assertEquals(tree.root.next(), null);
		assertEquals(tree.root.prev(), null);
		assertEquals(tree.root.child().key(), (Integer) 1);
		assertEquals(tree.root.child().value(), "Test");
		assertEquals(tree.root.child().next(), null);
		assertEquals(tree.root.child().prev(), null);
		assertEquals(tree.root.child().child(), null);
		assertEquals(tree.root.child().parent(), tree.root);

	}

	// Add a sibling
	@Test
	public void addSibling() {
		Integer[] path = new Integer[1];

		path[0] = 1;
		tree.add(path, "Test");

		path[0] = 2;
		tree.add(path, "Test2");

		assertEquals(tree.root.key(), null);
		assertEquals(tree.root.value(), null);
		assertEquals(tree.root.parent(), null);
		assertNotEquals(tree.root.child(), null);
		assertEquals(tree.root.next(), null);
		assertEquals(tree.root.prev(), null);
		assertEquals(tree.root.child().key(), (Integer) 1);
		assertEquals(tree.root.child().value(), "Test");
		assertNotEquals(tree.root.child().next(), null);
		assertEquals(tree.root.child().prev(), null);
		assertEquals(tree.root.child().child(), null);
		assertEquals(tree.root.child().parent(), tree.root);
		assertEquals(tree.root.child().next().key(), (Integer) 2);
		assertEquals(tree.root.child().next().value(), "Test2");
		assertEquals(tree.root.child().next().prev(), tree.root.child());
		assertEquals(tree.root.child().next().child(), null);
		assertEquals(tree.root.child().next().parent(), tree.root);

	}

	// Test printing
	@Test
	public void testPrint() {
		// Check empty
		tree.showTree();
		System.out.flush();
		assertEquals("null -> null\n", newout.toString());
	}

	// Test printing after add
	@Test
	public void testPrintAdd() {
		// Check add
		Integer[] path = new Integer[1];
		path[0] = (Integer) 1;
		tree.add(path, "Test");
		tree.showTree();
		System.out.flush();
		assertEquals("null -> null\n   1 -> Test\n", newout.toString());
	}

	// Test Remove
	@Test
	public void testPrintRemove() {
		// Check add
		Integer[] path = new Integer[1];
		path[0] = (Integer) 1;
		Integer[] path2 = { 1, 2, 3 };
		tree.add(path, "Test");
		tree.add(path2, "YouShouldn'tSeeMe");
		tree.remove(path2);
		tree.showTree();
		System.out.flush();
		assertEquals("null -> null\n   1 -> Test\n", newout.toString());
	}

	// Test Prefix
	@SuppressWarnings("deprecation")
	@Test
	public void testPrefix() {
		// Check add
		Integer[] path1 = { 1, 2, 4 };
		Integer[] path2 = { 1, 2, 3 };
		Integer[] pathprefix = { 1, 2 };
		tree.add(path1, "Test");
		assertEquals(pathprefix, tree.prefix(path2));
	}

	// Test Search
	@Test
	public void testSearch() {
		// Check add
		Integer[] path1 = { 1, 2, 4 };
		Integer[] path2 = { 1, 2, 3 };
		tree.add(path1, "Test");
		assertEquals("Test", tree.search(path1));
		assertEquals(null, tree.search(path2));
	}
}
