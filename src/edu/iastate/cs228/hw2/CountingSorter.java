package edu.iastate.cs228.hw2;

/**
 * 
 * @author Darren Hushak
 * 
 *         This class consists of a static method for sorting a non-negative
 *         integer array using the counting sort algorithm.
 */

public class CountingSorter {

	/**
	 * Sorts the given array in ascending order using the counting sort
	 * algorithm.
	 * 
	 * @param arr
	 *            the array to be sorted
	 * @throws IllegalArgumentException
	 *             if arr is a null pointer or its length is 0 or it has a
	 *             negative element.
	 */
	public static void countingSort(int[] arr) {
		Utils.checkArgs(arr);

		// Initialize maxkey, will keep track of maximum value of array
		int maxkey = 0;
		int minkey = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {

			// Putting this check in the counting loop so we don't have to loop
			// through arr more than we need to
			if (arr[i] < 0) {
				throw new IllegalArgumentException(
						"Array has negative contents");
			}

			// Find maximum value in array
			if (arr[i] > maxkey) {
				maxkey = arr[i];
			}

			// Find minimum value in array
			if (arr[i] < minkey) {
				minkey = arr[i];
			}
		}

		// Initialize keycount
		int[] keycnt = new int[maxkey + 1];

		// Every time a value appears in an array, increment that keycount's
		// index at that value
		for (int i = 0; i < arr.length; i++) {
			keycnt[arr[i]]++;
		}

		// Put counted objects back in arr, using a counter
		int cnt = 0;
		for (int i = minkey; i < keycnt.length; i++) {
			for (int j = 0; j < keycnt[i]; j++) {
				arr[cnt] = i;
				cnt++;
			}
		}

	}
}
