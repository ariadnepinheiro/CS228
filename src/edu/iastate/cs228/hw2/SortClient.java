package edu.iastate.cs228.hw2;

/**
 * @author Darren Hushak
 *
 */

/**
 * The class consists of a public main() method and a few private methods.
 * The main() method takes as input four parameters from the console
 * and generates arrays of random integers and calls the sorting methods
 * to sort those arrays and prints out the running times in milliseconds.
 *
 * The main() method takes as input
 * four integers from the console. The method first prompts the user
 * to enter the initial size isize of an array to be sorted.
 * Then it prompts the user to enter the parameter ps.
 * Next it prompts the user to enter a parameter seed, which
 * is used as a seed parameter to the Random constructor
 * for generating an array of random integers.
 * Finally it prompts the user to enter a parameter fac, which
 * is used to produce larger array sizes.
 * The values for the four parameters must be at least CUT.
 * If the user enters an item that is not an integer or
 * enters an integer that is less than CUT,
 * the method needs to handle those exceptions by printing
 * a proper message to the console and waiting for another item.
 * Those exceptions are handled by using
 * a try-catch structure in a loop. To avoid duplicate code,
 * the exception handling is identified as a private method.
 *
 * The sorting methods are tested as follows.
 * In the first application, write and use a private method named isAscending
 * to check if the array sorted by InsertionSorter.insertionSort() is indeed
 * in ascending order. If not, throw a RuntimeException. 
 * In addition, write and use a private method named isEqual to check if the arrays sorted
 * by InsertionSorter.insertionSort() and QuickSorter.quickSortRec()
 * are equal (i.e. of the same length and have the identical value at each index).
 * If not, throw a RuntimeException. 
 *
 * In the second application, call isAscending to check if
 * the array sorted by QuickSorter.quickSortRec() is indeed
 * in ascending order. If not, throw a RuntimeException. 
 * In addition, call isEqual to check if the arrays sorted
 * by QuickSorter.quickSortRec() and QuickSorter.quickSortNonRec()
 * are equal, If not, throw a RuntimeException. 
 * Also call isEqual to check if the arrays sorted by QuickSorter.quickSortRec() and
 * CountingSorter.countingSort() are equal. If not, throw a RuntimeException. 
 */

import java.util.Random;
import java.util.Scanner;

public class SortClient {
	static private final int CUT = 2;

	/**
	 * @throws RuntimeException
	 *             if the array sorted by InsertionSorter.insertionSort() is not
	 *             in ascending order.
	 * 
	 * @throws RuntimeException
	 *             if the arrays sorted by InsertionSorter.insertionSort() and
	 *             QuickSorter.quickSortRec() are not equal.
	 * 
	 * @throws RuntimeException
	 *             if the array sorted by QuickSorter.quickSortRec() is not in
	 *             ascending order.
	 * 
	 * @throws RuntimeException
	 *             if the arrays sorted by QuickSorter.quickSortRec() and
	 *             QuickSorter.quickSortNonRec() are not equal.
	 * 
	 * @throws RuntimeException
	 *             if the arrays sorted by QuickSorter.quickSortRec() and
	 *             CountingSorter.countingSort() are not equal.
	 */

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		long tic, toc;
		 int isize = getInt(sc, "Please enter an array size (>= 2): ");
		 int ps = getInt(sc, "Please enter a ps integer (>= 2): ");
		 int seed = getInt(sc, "Please enter a seed integer (>= 2): ");
		 int fac = getInt(sc, "Please enter a fac integer (>= 2): ");

		int[] a1 = genArray(isize, seed);
		int[] b1 = copyArray(a1);

		System.out
				.println("SortingMethodName\tArraySize\tSortingTimeInMilliseconds\tDepthOfRecursion");

		// /////////////////////////////////////////////////////////
		//
		//
		// Sort with insertion sort
		//
		//
		// /////////////////////////////////////////////////////////
		tic = System.currentTimeMillis();
		InsertionSorter.insertionSort(a1);
		toc = System.currentTimeMillis();

		if (!isAscending(a1)) {
			throw new RuntimeException("Insertion Sort did not sort correctly");
		}

		System.out.println("insertionSort\t" + isize + "\t" + (toc - tic));

		// /////////////////////////////////////////////////////////
		//
		//
		// Sort with Quick Sort (Recursive)
		//
		//
		// /////////////////////////////////////////////////////////
		tic = System.currentTimeMillis();
		QuickSorter.quickSortRec(b1, ps);
		toc = System.currentTimeMillis();

		if (!isEqual(a1, b1)) {
			throw new RuntimeException("Quicksort did not sort correctly");
		}
		System.out.println("quickSortRec\t" + isize + "\t" + (toc - tic));
		
		a1 = null;
		b1 = null;

		// Application Two
		int size2 = fac * isize;
		int[] a2 = genArray(size2, seed);
		int[] b2 = copyArray(a2);
		int[] c2 = copyArray(a2);

		// /////////////////////////////////////////////////////////
		//
		//
		// Sort with Counting Sort
		//
		//
		// /////////////////////////////////////////////////////////
		tic = System.currentTimeMillis();
		CountingSorter.countingSort(a2);
		toc = System.currentTimeMillis();

		if (!isAscending(a2)) {
			throw new RuntimeException("Counting Sort did not sort correctly");
		}

		System.out.println("countingSort\t" + size2 + "\t" + (toc - tic));

		// /////////////////////////////////////////////////////////
		//
		//
		// Sort with Quick Sort (Recursive)
		//
		//
		// /////////////////////////////////////////////////////////
		tic = System.currentTimeMillis();
		QuickSorter.quickSortRec(b2, ps);
		toc = System.currentTimeMillis();
		if (!isEqual(a2, b2)) {
			throw new RuntimeException(
					"Quicksort Recursive did not sort correctly");
		}
		System.out.println("quickSortRec\t" + size2 + "\t" + (toc - tic));

		// /////////////////////////////////////////////////////////
		//
		//
		// Sort with Quick Sort (Non Recursive)
		//
		//
		// /////////////////////////////////////////////////////////
		tic = System.currentTimeMillis();
		int rec = QuickSorter.quickSortNonRec(c2, ps);
		toc = System.currentTimeMillis();
		if (!isEqual(a2, c2)) {
			throw new RuntimeException(
					"Quicksort Non-Recursive did not sort correctly");
		}
		System.out.println("quickSortNonRec\t" + size2 + "\t" + (toc - tic) + "\t" + rec);

	}

	/**
	 * Gets an integer from the console. If the entered item is not an integer
	 * or its value is less than CUT, then prints a message to the console and
	 * waits for another item.
	 * 
	 * @param sysIn
	 *            The scanner object being used to ask for input
	 * @param mes
	 *            The initial message to print, instructing the user what to
	 *            input
	 * @return The int that the user specified
	 */
	private static int getInt(Scanner sysIn, String mes) {
		int i = 0;
		do {
			System.out.println(mes);
			while (!sysIn.hasNextInt()) {
				System.out.println("That's not a number!");
				sysIn.next();
			}
			i = sysIn.nextInt();
			if (i < CUT) {
				System.out
						.println("Number is too small, must be greater than 2");
				i = 0;
			}
		} while (i <= 0);
		return i;
	}

	/**
	 * Generates an array of random integers
	 * 
	 * @param size
	 *            Desired length of array to generate
	 * @param seed
	 *            Seed of random number generator
	 * @return Returns randomly generated array
	 * @throws IllegalArgumentException
	 *             If size is less than 0
	 */
	private static int[] genArray(int size, long seed) {
		if (size < 0) {
			throw new IllegalArgumentException("Size must be positive");
		}

		// Create a random number generator
		Random gen = new Random();

		// Set the seed
		gen.setSeed(seed);

		// Initialize the array
		int[] arr = new int[size];

		// Fill array with random numbers
		for (int i = 0; i < size; i++) {
			arr[i] = gen.nextInt(size);
		}
		return arr;
	}

	/**
	 * Returns a copy of an array.
	 * 
	 * @param arr
	 *            The array to be copied
	 * @return Returns the copied array
	 * 
	 * @throws IllegalArgumentException
	 *             If array is either null or holds zero values
	 */
	private static int[] copyArray(int[] arr) {
		Utils.checkArgs(arr);
		// Create new array
		int[] arrCopy = new int[arr.length];
		// Cycle through source array and assign its value at that location to
		// the new array
		for (int i = 0; i < arr.length; i++) {
			arrCopy[i] = arr[i];
		}
		return arrCopy;
	}

	/**
	 * Checks for correctly sorted array
	 * 
	 * @param arr
	 *            Array to be checked
	 * @return Returns true if array is sorted in ascending order and false
	 *         otherwise
	 * @throws IllegalArgumentException
	 *             If array is null or holds zero values
	 */
	private static boolean isAscending(int[] arr) {
		Utils.checkArgs(arr);
		// Loop through array and check if the previous index is less than the
		// current index
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] < arr[i - 1]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks for array equality
	 * 
	 * @param arr
	 *            Array to be checked against
	 * @param vec
	 *            Array to be checked
	 * @return Returns true if the two arrays are of the same length and have
	 *         the identical value at each index and false otherwise.
	 * @trhows IllegalArgumentExceptions If either array is null or holds zero
	 *         values
	 */
	private static boolean isEqual(int[] arr, int[] vec) {
		Utils.checkArgs(arr);
		Utils.checkArgs(vec);
		// Checks length
		if (arr.length != vec.length) {
			return false;
		}
		// Loop through array and check values at the corresponding index
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] != vec[i]) {
				return false;
			}
		}
		return true;
	}

}
