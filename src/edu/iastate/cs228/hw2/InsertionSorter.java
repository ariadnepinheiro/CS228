package edu.iastate.cs228.hw2;

/**
 * 
 * @author Darren Hushak
 * 
 *         This class consists of two static methods for sorting an integer
 *         array using the insertion sort algorithm.
 */

public class InsertionSorter {

	/**
	 * Sorts the given array in ascending order using the insertion sort
	 * algorithm. To avoid code duplication, the method calls the next method on
	 * the whole array.
	 * 
	 * @param arr
	 *            the array to be sorted
	 * @throws IllegalArgumentException
	 *             if arr is a null pointer or its length is 0.
	 */
	public static void insertionSort(int[] arr) {
		Utils.checkArgs(arr);

		// Call insertionSort with the correct parameters
		InsertionSorter.insertionSort(arr, 0, arr.length - 1);
	}

	/**
	 * Sorts the subarray between first and last (inclusive) in ascending order
	 * using the insertion sort algorithm.
	 * 
	 * @param arr
	 *            the array to be sorted
	 * @param first
	 *            index of first position in subarray
	 * @param last
	 *            index of last position in subarray
	 * @throws IllegalArgumentException
	 *             if arr is null pointer, its length is 0, first < 0, last >=
	 *             arr.length, or first > last.
	 */
	public static void insertionSort(int[] arr, int first, int last) {
		Utils.checkArgs(arr);
		if (first < 0) {
			throw new IllegalArgumentException(
					"Sub-array starting location is less than zero");
		}
		if (last >= arr.length) {
			throw new IllegalArgumentException(
					"Sub-array ending location is greater than or equal to the array length");
		}
		if (first > last) {
			throw new IllegalArgumentException(
					"Sub-array starting location is greater than ending location");
		}

		// Sort the array. Start at index one, and continually swap that element
		// with the element on its left until it is not less than the element on
		// its left. Move on to the next starting index and continue until
		// sorted.
		for (int i = first + 1; i <= last; i++) {
			int temp = arr[i];
			for (int j = i - 1; j >= 0 && temp < arr[j]; j--) {
				int swap = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = swap;
			}
		}

	}

}
