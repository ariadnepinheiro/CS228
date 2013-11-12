package edu.iastate.cs228.hw3;

/** 
 * @author Darren Hushak
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class PriorityDoublyLinkedList<E> implements List<E>, IPriority<E> {
	int idCounter;
	int defaultPrio;
	int numPriorityLevel;
	int size;
	final DNode head;

	/**
	 * Guides on what to do
	 * 
	 * You are asked to implement a priority doubly-linked list class that
	 * supports both IPriority and List interfaces. The linked list has a dummy
	 * head node as described in the assignment document. The dummy head node
	 * links to the other nodes in the linked list. Be sure to read the overview
	 * section of the assignment before working on the code.
	 * 
	 * IMPORTANT: The instructions given here are incomplete and are meant to
	 * give you a starting point. You will need to check the Java SE 7 API
	 * document for a full detailed description of each of the List methods.
	 * 
	 * Choose your own private member variables that are meaningful and document
	 * these variables. You are allowed to have your own private methods in
	 * addition to what are given here. Give the private methods meaningful
	 * names and Javadoc what the methods do.
	 * 
	 */

	/**
	 * Guides on member variables/fields
	 * 
	 * - a private member variable to keep the number of nodes (elements) in the
	 * list; the dummy head node is not counted as the number of elements in the
	 * list.
	 * 
	 * - a private final member variable to keep an object reference to a dummy
	 * head node of an inner class DNode; the value of this variable must not be
	 * changed after the object is instantiated
	 * 
	 * - a private final member variable of type int to keep track of the
	 * default priority of a node if added without a given priority
	 * 
	 * The dummy head node is of class DNode that has the following required
	 * fields. You may choose your own names for these fields, but Javadoc them.
	 * 
	 * - prio, priority field that keeps the number of priority levels supported
	 * by this list The value of this field does not change after an object of
	 * this class has been instantiated
	 * 
	 * - next, this next field references the first node in the list or the head
	 * node itself if the list is empty
	 * 
	 * - prev, this previous field references the last node in the list or the
	 * head node itself if the list is empty
	 * 
	 * - id, this id field is initially set to zero for the head node
	 * 
	 */

	/**
	 * PriorityDoublyLinkedList(int numPriorityLevelP, int defaultPriorityP)
	 * 
	 * Guides on what to do
	 * 
	 * 
	 * 1. Create the head node and initilize it to have its prev field and the
	 * next field reference to itself. Set its data field to null. Set the
	 * priority of the head node to the numPriorityLevelP. 2. What you think is
	 * necessary such as initializing the remaining member variables.
	 * 
	 * @param numPriorityLevelP
	 *            : the number of priority levels supported by this list; keep
	 *            the value in the dummy head node
	 * @param defaultPriority
	 *            : A node is given the default priority if an add method does
	 *            not specify a specific priority for that node. The value of
	 *            this parameter can be between 0 inclusive and
	 *            numPriorityLevelP-1 inclusive.
	 * @throws IllegalArgumentException
	 *             if numPriorityLevelP <= 0 || defaultPriorityP >=
	 *             numPriorityLevelP || defaultPriorityP < 0
	 * 
	 */
	public PriorityDoublyLinkedList(int numPriorityLevelP, int defaultPriorityP) {
		if (numPriorityLevelP <= 0 || defaultPriorityP >= numPriorityLevelP
				|| defaultPriorityP < 0) {
			throw new IllegalArgumentException();
		}
		defaultPrio = defaultPriorityP;
		numPriorityLevel = numPriorityLevelP;
		idCounter = 0;
		size = 0;
		// Instantiate head dummy node
		head = new DNode();
		head.prio = numPriorityLevelP;
		head.next = head;
		head.prev = head;
	}

	/**
	 * Guides on what to implement for this DNode class
	 * 
	 * Objects of this class are linked together in the list.
	 * 
	 * This class must be a private non-static class to represent a node with
	 * the following required fields. 1. id to keep a node id that is unique
	 * among objects of this class. You can assume that the number of nodes is
	 * less than the maximum value of an integer. 2. prio of an int type to keep
	 * the priority of this node; the value of prio never changes. Different
	 * nodes may have different priorities, but the priority of a node must be
	 * >= 0 and < the number of priority levels supported by the list. 3. data
	 * to contain an object reference to an object of type E or null if an
	 * object is null or if the node is the dummy head node 4. prev to keep the
	 * object reference to its previous node in the list 5. next to keep the
	 * object reference to its next node in the list
	 * 
	 * 
	 * This class has two constructors. Implement both the constructors
	 */
	private class DNode {
		@SuppressWarnings("unused")
		int id;
		DNode next;
		DNode prev;
		E data;
		int prio;

		/**
		 * Hints Set prev, data, next to null. Set prio to the default priority
		 * of a node, defaultPriorityP, given as an argument of the
		 * PriorityDoublyLinkedList constructor Set the id for this node.
		 * 
		 */
		public DNode() {
			// Give ID and increment static idCounter
			id = idCounter++;
			// Set to default Priority
			prio = defaultPrio;
			// Null prev and next
			prev = next = null;
			// Null data
			data = null;
		}

		/**
		 * Hints
		 * 
		 * Initialize the fields of the DNode object accordingly.
		 * 
		 * @param obj
		 *            is null or an object reference to an actual object of type
		 *            E this node represents
		 * @param prevP
		 *            has an object reference to its previous node in the list
		 * @param nextP
		 *            has an object reference to its next node in the list
		 * @param priority
		 *            , the priority of this node
		 * @throws IllegalArgumentException
		 *             if priority < 0 || priority >= numPriorityLevelP, an
		 *             argument in the constructor of the
		 *             PriorityDoublyLinkedList class
		 */
		public DNode(E obj, DNode prevP, DNode nextP, int priority) {
			if (priority < 0 || priority >= numPriorityLevel) {
				throw new IllegalArgumentException();
			}
			// Give ID and increment static idCounter
			id = idCounter++;
			// Set to given priority
			prio = priority;
			// Set Previous and Next to correct links
			prev = prevP;
			next = nextP;
			// Null data
			data = obj;
		}

	}

	private class PriorityDoublyLinkedListIterator implements ListIterator<E> {

		/**
		 * Iterator initialized this way iterates through all elements in the
		 * list regardless of priority.
		 * 
		 * Hints:
		 * 
		 * Defined private member variables (fields) for the following.
		 * 
		 * 1. Cursor to keep track of what object reference to be returned by
		 * next() 2. A variable to indicate whether to iterate all elements in
		 * the list or only elements with a certain priority
		 */

		private DNode cursor;
		private int prioCheck;

		private PriorityDoublyLinkedListIterator() {
			prioCheck = -1;
			cursor = head.next;
		}

		/**
		 * The iterator object initialized this way iterates on the elements
		 * with the specified priority.
		 * 
		 * Hints:
		 * 
		 * 
		 * @param priority
		 *            : only nodes with the given priority are traversed
		 * @throws IllegalArgumentException
		 *             if priority < 0 || priority >= number of priority levels
		 *             given as the argument of the constructor of the
		 *             PriorityDoublyLinkedList class
		 */
		private PriorityDoublyLinkedListIterator(int priority) {
			if (priority < 0 || priority >= head.prio) {
				throw new IllegalArgumentException();
			}
			prioCheck = priority;
			cursor = head.next;
			while (cursor.prio != prioCheck && cursor != head) {
				cursor = cursor.next;
			}
		}

		/**
		 * When iterating through all nodes in the list regardless of priority
		 * (i.e., the iterator object is instantiated with a default
		 * constructor), the iterator object behaves like a normal Java
		 * iterator.
		 * 
		 * For iteration with a certain priority, only nodes with that priority
		 * are iterated. Therefore, the cursor always references a node with the
		 * same priority as the priority given as an argument in the
		 * PriorityDoublyLinkedListIterator constructor.
		 * 
		 * If the next() method is implemented correctly, this hasNext() method
		 * uses the same code regardless of whether to iterate with a specific
		 * priority or not.
		 */
		@Override
		public boolean hasNext() {
			if (cursor != head) {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * When iterating through all nodes in the list regardless of priority,
		 * this method behaves as in a normal Java iterator.
		 * 
		 * However, if the iterator object is initialized with a certain
		 * priority, returns true if there is a node with that priority in the
		 * list before the node referenced by the cursor; otherwise, returns
		 * false.
		 */
		@Override
		public boolean hasPrevious() {
			if (cursor.prev == head) {
				return false;
			} else {
				DNode tempCur = cursor;
				do {
					tempCur = tempCur.prev;
					if (tempCur.prio == prioCheck) {
						return true;
					}
				} while (tempCur != head);

			}
			return false;
		}

		/**
		 * Implement according to the Java API documentation for this method if
		 * the iterator is instantiated using a default constructor. If the
		 * iterator is instantiated with the constructor that requires a
		 * priority, next() should return the next element in the list with that
		 * priority.
		 * 
		 * Hints:
		 * 
		 * 1. Check whether to throw NoSuchElementException 2. Keep the
		 * reference of the data item at the cursor to return 3. Advance the
		 * cursor to the next element with that priority if the priority is
		 * given in a constructor.
		 * 
		 */
		@Override
		public E next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			E data = cursor.data;
			if (this.prioCheck == -1) {
				cursor = cursor.next;
			} else
				do {
					cursor = cursor.next;
				} while (cursor.prio != prioCheck && cursor != head);
			return data;
		}

		// --------------------- Unsupported operations ------------------
		@Override
		public void add(E item) {
			throw new UnsupportedOperationException();

		}

		@Override
		public int nextIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public E previous() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

		@Override
		public void set(E arg0) {

			throw new UnsupportedOperationException();

		}

	}

	/**
	 * See the requirement in IPriority.
	 * 
	 * @param priority
	 *            is zero or a positive integer less than the number of priority
	 *            levels argument of the constructor
	 * @return a ListIterator object that iterates over only the elements with a
	 *         specified priority.
	 * @throws IllegalArgumentException
	 *             if priority < 0 or priority >= the number of priority levels
	 */
	@Override
	public ListIterator<E> iteratorWithPriority(int priority) {
		if (priority < 0 || priority >= head.prio) {
			throw new IllegalArgumentException();
		}
		PriorityDoublyLinkedListIterator iter = new PriorityDoublyLinkedListIterator(
				priority);
		return iter;
	}

	/**
	 * Return a PriorityDoublyLinkedListIterator object that allows iteration
	 * regardless of priority
	 * 
	 * @ret the iterator
	 */
	@Override
	public Iterator<E> iterator() {

		PriorityDoublyLinkedListIterator iter = new PriorityDoublyLinkedListIterator();
		return iter;
	}

	/**
	 * return a PriorityDoublyLinkedListIterator object that allows iteration
	 * regardless of priority
	 * 
	 * @ret the iterator
	 */
	@Override
	public ListIterator<E> listIterator() {

		PriorityDoublyLinkedListIterator iter = new PriorityDoublyLinkedListIterator();
		return iter;
	}

	/**
	 * Return a PriorityDoublyLinkedListIterator object that allows iteration
	 * regardless of priority
	 * 
	 * @param pos
	 *            the position to start the list iterator
	 * @ret the iterator
	 */
	@Override
	public ListIterator<E> listIterator(int pos) {
		PriorityDoublyLinkedListIterator iter = new PriorityDoublyLinkedListIterator();
		for (int i = 0; i < pos; i++) {
			iter.next();
		}
		return iter;
	}

	/**
	 * 
	 * Pulls from the head priority level
	 * 
	 * @ret Number of priority levels, stored as the priority level of the head
	 *      node
	 */
	public int getNumPriorityLevels() {
		return head.prio;

	}

	/**
	 * Add the item with the given priority at the END of the list.
	 * 
	 * @param priority
	 *            : priority of this item to be kept in the node containing the
	 *            reference of the item
	 * @param item
	 *            : item may be null or may be a duplicate of an element already
	 *            in the list
	 * @throws IllegalArgumentException
	 *             if priority < 0 or priority >= the number of priority levels
	 *             of the constructor
	 * 
	 */
	@Override
	public void addWithPriority(int priority, E item) {
		if (priority < 0 || priority >= head.prio) {
			throw new IllegalArgumentException();
		}
		// Create new node
		DNode newNode = new DNode(item, head.prev, head, priority);
		// Adjust pointers
		head.prev.next = newNode;
		head.prev = newNode;
		// Increment size
		size++;
	}

	/**
	 * Add the item with the given priority as the first node in the list (i.e.,
	 * right after the head node).
	 * 
	 * @param priority
	 *            : priority of this item to be kept in the node of this item
	 * @param item
	 *            : item may be null or may be a duplicate of an element already
	 *            in the list
	 * @throws IllegalArgumentException
	 *             if priority < 0 or >= the number of priority levels of the
	 *             constructor
	 */
	@Override
	public void addFirstWithPriority(int priority, E item) {
		if (priority < 0 || priority >= head.prio) {
			throw new IllegalArgumentException();
		}
		// Create new node
		DNode newNode = new DNode(item, head, head.next, priority);
		// Adjust pointers
		head.next.prev = newNode;
		head.next = newNode;
		// Increment size
		size++;

	}

	/**
	 * Adds an item at the end of the list
	 * 
	 * @param item
	 *            Item to add
	 * @ret returns true if the list was changed
	 * 
	 */
	@Override
	public boolean add(E item) {
		// Create new node
		DNode newNode = new DNode(item, head.prev, head, defaultPrio);
		// Adjust pointers
		head.prev.next = newNode;
		head.prev = newNode;
		// Increment size
		size++;
		return true;
	}

	/**
	 * Adds an item to the list at a specific position
	 * 
	 * @param pos
	 *            The position to add the item to
	 * @param item
	 *            The item to add
	 * @throws IndexOutOfBoundsException
	 *             if pos is less than zero or greater than/equal to size of
	 *             list
	 */
	@Override
	public void add(int pos, E item) {
		if (pos < 0 || pos >= size) {
			throw new IndexOutOfBoundsException();
		}
		// Start at the head
		DNode cursor = head;

		// Advance through the list, until pos is reached
		for (int i = 0; i < pos; i++) {
			cursor = cursor.next;
		}
		// Create new node
		DNode newNode = new DNode(item, cursor, cursor.next, defaultPrio);
		// Adjust pointers
		cursor.next.prev = newNode;
		cursor.next = newNode;
		// Increment size
		size++;
	}

	/**
	 * 
	 * Clears the list - sets size to 0, and head points to itself in both next
	 * and prev
	 * 
	 */
	@Override
	public void clear() {
		size = 0;
		head.next = head.prev = head;
	}

	/**
	 * Returns the data at a given position in a list
	 * 
	 * @param pos
	 *            The position to go to for data
	 * @ret The data at said position
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds
	 */
	@Override
	public E get(int pos) {
		if (pos < 0 || pos >= size) {
			throw new IndexOutOfBoundsException();
		}
		// Start at the head
		DNode cursor = head;

		// Advance through the list, until pos is reached
		for (int i = 0; i <= pos; i++) {
			cursor = cursor.next;
		}
		// Return the data at position
		return cursor.data;
	}

	/**
	 * Searches through the list for the first element that matches the input
	 * item. If it finds one that equals the input, it adjusts the links around
	 * it to skip over it, and then decrements the size
	 * 
	 * @param item
	 *            The object to search for and remove
	 * @return True if the object was found and removed, false if no change
	 */
	@Override
	public boolean remove(Object item) {

		// Start at the head + 1
		DNode cursor = head.next;
		boolean rem = false;

		// Advance through the list, checking for equality (advancement happens
		// before check so as to not include head in the search)
		for (int i = 0; i <= size; i++) {
			if (cursor.data == null) {
				if (item == null) {
					rem = true;
				}
			} else if (cursor.data.equals(item)) {
				rem = true;
			}
			if (rem) {
				// Set previous node's link to skip over removed node
				cursor.prev.next = cursor.next;

				// Set next node's link to skip over removed node
				cursor.next.prev = cursor.prev;

				// Decrement size counter
				size--;

				return true;
			}
			cursor = cursor.next;
		}
		// Return false if object was not found
		return false;
	}

	/**
	 * Cycles through the list until it arrives at the correct position, then
	 * updates said position's datafield
	 * 
	 * @param pos
	 *            The position at which to update the data field
	 * @param item
	 *            The object to update the data field
	 * @return The object that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if position is out of bounds
	 */
	@Override
	public E set(int pos, E item) {
		if (pos < 0 || pos >= size) {
			throw new IndexOutOfBoundsException();
		}
		// Start at the head
		DNode cursor = head;

		// Advance through the list until you hit the desired position
		for (int i = 0; i <= pos; i++) {
			cursor = cursor.next;
		}

		// Store return object
		E ret = cursor.data;

		// Update the desired position's datafield
		cursor.data = item;

		return ret;
	}

	/**
	 * Returns the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns the number of elements with a given priority
	 * 
	 * @param priority
	 *            is zero or a positive integer less than the number of priority
	 *            levels
	 * @throws IllegalArgumentException
	 *             if priority < 0 or priority >= the number of priority levels
	 * @return Number of elements in the list associated with the specified
	 *         priority.
	 */
	@Override
	public int sizeGivenPriority(int priority) {
		if (priority < 0 || priority >= head.prio) {
			throw new IllegalArgumentException();
		}

		// Counter and cursor
		int cnt = 0;
		DNode cursor = head;

		for (int i = 0; i <= size; i++) {
			if (cursor.prio == priority) {
				cnt++;
			}
			cursor = cursor.next;
		}
		return cnt;

	}

	/**
	 * Searches through the list for a match to the input item
	 * 
	 * @param item
	 *            The object to search for
	 * @return True if the object was found, false if not
	 */
	@Override
	public boolean contains(Object item) {
		// Start at the head
		DNode cursor = head;

		// Advance through the list, checking for equality (advancement happens
		// before check so as to not include head in the search)
		for (int i = 0; i < size; i++) {
			cursor = cursor.next;
			if (cursor.data.equals(item)) {
				return true;
			}
		}
		// Return false if object was not found
		return false;
	}

	/**
	 * Checks for empty array by seeing if the head's next item is itself
	 * 
	 * @return True if list is empty, false if not
	 */
	@Override
	public boolean isEmpty() {
		if (head.next == head) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for equality of two lists.
	 * 
	 * @param o
	 *            Object to compare this to
	 * @ret True if o is the same list, or if every element of both lists are
	 *      equal
	 */
	@Override
	public boolean equals(Object o) {
		// Check for null input, differing classes, differing sizes
		if (o == null || !(o  instanceof List)) {
			return false;
		}

		// Can suppress warning because the class gets checked in the previous
		// if statement
		@SuppressWarnings("unchecked")
		List<E> oCast = (List<E>)o;

		// Check for same object or if both empty
		if (this == o || this.isEmpty() && oCast.isEmpty()) {
			return true;
		}

		if (this.size() != oCast.size()) {
			return false;
		}

		// Iterate through two lists and check data at each position

		for (int i = 0; i < this.size(); i++) {
			// Check for null data in this
			if (this.get(i) == null) {
				// Check for null data in that
				if (oCast.get(i) != null) {
					return false;
				}

				// If this data is not null, then check for data equality
			} else if (!this.get(i).equals(oCast.get(i))) {
				return false;
			}
		}
		

		return true;
	}

	// ----------------- Unsupported Operations ------------
	/**
	 * throw new UnsupportedOperationException() for all the unsupported
	 * operations
	 */

	/**
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public int indexOf(Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public boolean addAll(Collection<? extends E> item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public boolean addAll(int pos, Collection<? extends E> item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public int lastIndexOf(Object item) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public E remove(int pos) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public List<E> subList(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * 
	 * @throws UnsupportedOperationException
	 *             ()
	 */
	@Override
	public <T> T[] toArray(T[] arg0) {
		throw new UnsupportedOperationException();
	}

}
