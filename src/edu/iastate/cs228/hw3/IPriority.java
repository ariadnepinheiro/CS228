package edu.iastate.cs228.hw3;

import java.util.ListIterator;

public interface IPriority<E> {
	/**
	 * 
	 * @param priority
	 *            is zero or a positive integer less than the number of priority
	 *            levels
	 * @throws IllegalArgumentException
	 *             if priority < 0 or priority >= the number of priority levels
	 * @return Number of elements in the list associated with the specified
	 *         priority.
	 */
	int sizeGivenPriority(int priority);

	/**
	 * 
	 * @param priority
	 *            is zero or a positive integer less than the number of priority
	 *            levels argument of the constructor
	 * @return a ListIterator object that iterates over only the elements with a
	 *         specified priority.
	 * @throws IllegalArgumentException
	 *             if priority < 0 or priority >= the number of priority levels
	 */
	ListIterator<E> iteratorWithPriority(int priority);

	/**
	 * 
	 * @return the number of priority levels this list supports.
	 * 
	 *         This number was specified when the PriorityDoublyLinkedList was
	 *         instantiated and it is stored in the dummy head node.
	 */
	int getNumPriorityLevels();

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
	 */
	void addWithPriority(int priority, E item);

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
	void addFirstWithPriority(int priority, E item);

}
