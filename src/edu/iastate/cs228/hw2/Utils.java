package edu.iastate.cs228.hw2;

/**
 * 
 * @author Darren Hushak
 * 
 *         Utility package for sorting
 */
public class Utils {

	/**
	 * Swaps two values of an array, given an array and two indices
	 * 
	 * @param arr
	 *            The array to be swapped
	 * @param pos1
	 *            The first index to swap
	 * @param pos2
	 *            The second index to swap
	 */
	public static void swap(int[] arr, int pos1, int pos2) {
		Utils.checkArgs(arr);
		Utils.checkValidIndex(arr, pos1);
		Utils.checkValidIndex(arr, pos2);
		if (pos1 != pos2) {
			int temp = arr[pos2];
			arr[pos2] = arr[pos1];
			arr[pos1] = temp;
		}
	}

	/**
	 * Checks the arguments for sorting classes and throws appropriate
	 * exceptions
	 * 
	 * @param arr
	 *            The array to check
	 */
	public static void checkArgs(int[] arr) {
		if (arr == null) {
			throw new IllegalArgumentException("Array is null");
		}
		if (arr.length == 0) {
			throw new IllegalArgumentException("Array has no contents");
		}
	}

	/**
	 * Checks for valid index
	 * 
	 * @param arr
	 *            The array to check for index validity
	 * @param index
	 *            The index to check against the array
	 */
	public static void checkValidIndex(int[] arr, int index) {
		Utils.checkArgs(arr);
		if (index < 0) {
			throw new IllegalArgumentException("Index is less than 0");
		}
		if (index >= arr.length) {
			throw new IllegalArgumentException("Index is out of range of array");
		}
	}
}
