package edu.iastate.cs228.hw4;

import java.util.Arrays;

/**
 * @author Darren Hushak
 * 
 *         An entry tree class
 */
public class EntryTree<K, V> {
	/**
	 * dummy root node made public for grading
	 */
	public Node root;

	// Quote from TA in discussion board: We provided prefixlen as an
	// alternative to
	// help consolidate code, but if you have an easier way of doing the same
	// thing, then you can remove prefixlen. - because of this, I'm removing
	// prefixlen

	protected class Node implements EntryNode<K, V> {
		private Node child; // link to the first child node
		private Node parent; // link to the parent node
		private Node prev; // link to the previous sibling
		private Node next; // link to the next sibling
		private K key; // the key for this node
		private V value; // the value at this node

		public Node(K aKey, V aValue) {
			key = aKey;
			value = aValue;
			child = null;
			parent = null;
			prev = null;
			next = null;
		}

		@Override
		public EntryNode<K, V> parent() {
			return parent;
		}

		@Override
		public EntryNode<K, V> child() {
			return child;
		}

		@Override
		public EntryNode<K, V> next() {
			return next;
		}

		@Override
		public EntryNode<K, V> prev() {
			return prev;
		}

		@Override
		public K key() {
			return key;
		}

		@Override
		public V value() {
			return value;
		}
	}

	public EntryTree() {
		root = new Node(null, null);
	}

	/**
	 * Returns the value of the entry with a specified key sequence, or null if
	 * this tree contains no entry with the key sequence.
	 * 
	 * @param keyarr
	 * @return
	 */
	public V search(K[] keyarr) {
		// Start at the root
		Node cursor = root;
		// Advance through each layer, looking for the correct key value from
		// each child
		for (int i = 0; i < keyarr.length; i++) {
			cursor = findChildWithKey(cursor, keyarr[i]);

			// If the cursor comes to either a leaf or the end of a set of
			// siblings (the youngest child?), it returns null (meaning that
			// there is no child with that key value. Probably a good idea
			// anyway, as kids lose things, and keys are important, and
			// shouldn't be lost)
			if (cursor == null) {
				return null;
			}
		}
		return cursor.value;
	}

	/**
	 * The method returns an array of type K[] with the longest prefix of the
	 * key sequence specified in keyarr such that the keys in the prefix label
	 * the nodes on the path from the root to a node. The length of the returned
	 * array is the length of the longest prefix.
	 * 
	 * @param keyarr
	 *            The array to find a prefix for
	 * @return The matching prefix of the array
	 */
	public K[] prefix(K[] keyarr) {
		// Check for bad values
		if (keyarr == null || keyarr.length == 0) {
			return null;
		}

		int retLength = 0;

		// Start at the root
		Node cursor = root;

		for (int i = 0; i < keyarr.length; i++) {
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}
			cursor = findChildWithKey(cursor, keyarr[i]);
			if (cursor == null) {
				break;
			}
			retLength++;
		}

		K[] ret = Arrays.copyOf(keyarr, retLength);
		return ret;

	}

	/**
	 * The method locates the node P corresponding to the longest prefix of the
	 * key sequence specified in keyarr such that the keys in the prefix label
	 * the nodes on the path from the root to the node. If the length of the
	 * prefix is equal to the length of keyarr, then the method places aValue at
	 * the node P and returns true. Otherwise, the method creates a new path of
	 * nodes (starting at a node S) labelled by the corresponding suffix for the
	 * prefix, connects the prefix path and suffix path together by making the
	 * node S a child of the node P, and returns true.
	 * 
	 * @param keyarr
	 *            The key array to follow in placing of the new node/updating
	 *            the new value
	 * @param aValue
	 *            The value to place in the new or updated node
	 * @return false on invalid input, true on successful add/update of a node
	 *         with value aValue
	 */
	public boolean add(K[] keyarr, V aValue) {
		// Check for bad values
		if (keyarr == null || keyarr.length == 0 || aValue == null) {
			return false;
		}

		// Start at the root
		Node cursor = root;

		// For each item in keyarr, advance down a generation, finding the child
		// with that key. If no child with that key exists, it births a new one
		// (phew, this code is getting a little steamy), and gives it said key
		// value.
		for (int i = 0; i < keyarr.length; i++) {

			// If some dummy put in a null for that element of the key array,
			// throw that doofus an exception
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}

			Node childNode = findChildWithKey(cursor, keyarr[i]);

			if (childNode == null) {
				// Witness the awesome power of childbirth
				childNode = new Node(keyarr[i], null);
				childNode.parent = cursor;
				childNode.parent.child = childNode;
			}

			// Point to the child. Mostly with a firm and disciplinary disdain,
			// but with a soft and gentle hint of love, so the child knows that
			// discipline is important, but it doesn't make you a monster as a
			// parent
			cursor = childNode;

		}

		// Give value to your child. No matter what anybody tells you, some
		// children are more valuable to their parents than others
		cursor.value = aValue;
		return true;
	}

	/**
	 * Removes the entry for a key sequence from this tree and returns its value
	 * if it is present. Also checks its parents to see if they need to be
	 * deleted. Otherwise, it makes no change to the tree and returns null.
	 * 
	 * @param keyarr
	 *            The keyarr sequence to remove
	 * @return null if the keyarr sequence does not correspond to a valid path
	 *         down the tree. Returns the value at the keyarr point in the tree
	 *         if it is valid
	 */
	public V remove(K[] keyarr) {
		// Check for bad values
		if (keyarr == null || keyarr.length == 0) {
			return null;
		}

		// Start at the root
		Node cursor = root;
		V ret;

		// Advance through every keyarr value
		for (int i = 0; i < keyarr.length; i++) {

			// Check for null values (I put this here to save a loop)
			if (keyarr[i] == null) {
				throw new NullPointerException();
			}
			// Find the child with the key value
			cursor = findChildWithKey(cursor, keyarr[i]);

			// If no child with key value, return null (keyarr does not exist)
			if (cursor == null) {
				return null;
			}
		}
		// At this point we've found the cursor that corresponds to the given
		// keyarr
		ret = cursor.value;
		// Set that Node's value to null, so it can be checked for leaf removal
		cursor.value = null;
		// Trim the tree
		removeLeafNodesUpward(cursor);
		return ret;
	}

	/**
	 * The method prints the tree on the console in the output format shown in
	 * an example output file.
	 */
	public void showTree() {
		// Start at root
		Node cursor = root;
		printChildrenRecursive(0, cursor);
	}

	/**
	 * Given a Node and a key value, this method returns the passed Node's child
	 * that is equal to the key value. It starts with the Node's child, and
	 * advances through that list until it either finds the correct child or
	 * reaches the end of the list.
	 * 
	 * @param cursor
	 *            The Parent node of the children being searched
	 * @param key
	 *            The key to search for in the children
	 * @return Null if no child, or no children with the key value. Else returns
	 *         the child node with the correct key value
	 */
	private Node findChildWithKey(Node cursor, K key) {
		// Check for TWINK (TWo Income No Kids - such money, very wow)
		// If you have a dry or nonexistent sense of humor, this section checks
		// to see whether or not the Node has a child. If not, passes null
		if (cursor.child == null) {
			return null;
		}

		// Sets the cursor to the first child in the linked list
		cursor = cursor.child;

		// Iterates through all of the siblings until it either finds the
		// sibling with the matching key, or reaches the end of the list (maybe
		// they put that one up for adoption?)
		while (!cursor.key.equals(key) || !cursor.key.equals(null)) {
			// Go to the next sibling
			cursor = cursor.next;
		}

		// Send the kid out into the world. Hopefully he did well in college and
		// can find a sustainable job for himself and his wife and kids.
		return cursor;

	}

	/**
	 * Checks to see if a node qualifies for deletion. If it does, remove all
	 * links to it, and check recursively to see if its parents also qualify for
	 * deletion, and delete them if they do. Qualifications for deletion: No
	 * child links, and a null value
	 * 
	 * @param cursor
	 *            The Node to check for leaf removal. Will also work up the tree
	 *            from this leaf to check for removal.
	 */
	private void removeLeafNodesUpward(Node cursor) {

		// If cursor is null, get out of here
		if (cursor == null) {
			return;
		}
		// If this cursor has no children and no value, then it is a communist.
		// Oh wait, no it's just a leaf. Sorry, got trigger happy there.
		if (cursor.child == null && cursor.value == null) {

			// If you've got a parent, check to see if they need to be killed
			// (the horror.. the horror)
			// The extra check for the parent's child pointer is to see if it
			// points at cursor. If it does, Cursor is either first in line or
			// the only child node of that parent. If the parent's child pointer
			// isn't the cursor, then the parent has other children and
			// therefore does not need killing
			if (cursor.parent != null && cursor.parent.child.equals(cursor)) {
				// Disown your eldest child such that the next in line is now
				// the eldest. If the second eldest doesn't exist, then that
				// parent's child pointer is now null
				cursor.parent.child = cursor.next;
				// Check for parent killing
				removeLeafNodesUpward(cursor.parent);
			}

			// If you've got siblings, change their linking such that you never
			// existed
			if (cursor.next != null) {
				cursor.next.prev = cursor.prev;
			}
			if (cursor.prev != null) {
				cursor.prev.next = cursor.next;
			}

		}
	}

	/**
	 * This method recursively prints the children, their children, and their
	 * children etc. of a given node until leafs are found and until the cursor
	 * has gone to the end of the linked list.
	 * 
	 * @param numLevels
	 *            Number of levels down the tree we are, which translates to a
	 *            new tab
	 * @param cursor
	 *            The cursor to start printing at
	 */
	private void printChildrenRecursive(int numLevels, Node cursor) {
		// If this cursor doesn't exist, then we have nothing to print
		if (cursor == null) {
			return;
		}

		// Advance through the siblings
		while (cursor.next != null) {
			// Recursive call - Prints all of this Node's children
			printChildrenRecursive(numLevels + 1, cursor.child);

			// Put dem tabs in to make it saucy lookin'
			for (int i = 0; i < numLevels; i++) {
				System.out.print("\t");
			}
			// Print out the key and value
			System.out.print(cursor.key + " -> " + cursor.value + "\n");
			// Advance to next sibling
			cursor = cursor.next;
		}
	}
}
