package edu.iastate.cs228.hw2;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author Darren Hushak
 */

public class SorterTest {

	int[] vals = { 80, 120, 80, 110, 60, 1, 100 };
	int[] valsSorted = { 1, 60, 80, 80, 100, 110, 120 };
	int[] valsSubSorted = { 80, 80, 110, 120, 60, 1, 100 };
	int[] valsNeg = { -80, 120, 80, 110, 60, 1, 100 };
	int[] valsEmpty = {};

	// /////////////////////////////////////////////////////////
	//
	//
	// CountingSort Tests
	//
	//
	// /////////////////////////////////////////////////////////

	// Test for actual sorting accuracy
	@Test
	public void countingSortTest() {
		CountingSorter.countingSort(vals);
		assertThat(vals, is(valsSorted));
	}

	// Null test
	@Test(expected = IllegalArgumentException.class)
	public void countingNullTest() {
		CountingSorter.countingSort(null);
	}

	// Non-null but empty array test
	@Test(expected = IllegalArgumentException.class)
	public void countingEmptyTest() {
		CountingSorter.countingSort(valsEmpty);
	}

	// Negative number test
	@Test(expected = IllegalArgumentException.class)
	public void countingNegTest() {
		CountingSorter.countingSort(valsNeg);
	}

	// /////////////////////////////////////////////////////////
	//
	//
	// InsertionSort Tests
	//
	//
	// /////////////////////////////////////////////////////////

	// Test for actual sorting accuracy
	@Test
	public void insertionSortTest() {
		InsertionSorter.insertionSort(vals);
		assertThat(vals, is(valsSorted));
	}

	// Null test
	@Test(expected = IllegalArgumentException.class)
	public void insertionNullTest() {
		InsertionSorter.insertionSort(null);
	}

	// Non-null but empty array test
	@Test(expected = IllegalArgumentException.class)
	public void insertionEmptyTest() {
		InsertionSorter.insertionSort(valsEmpty);
	}

	// Insertion Sort Subarray Tests
	// Test for actual sorting accuracy
	@Test
	public void insertionSubSortTest() {
		InsertionSorter.insertionSort(vals, 0, 3);
		assertThat(vals, is(valsSubSorted));
	}

	// Null test
	@Test(expected = IllegalArgumentException.class)
	public void insertionSubNullTest() {
		InsertionSorter.insertionSort(null, 0, 0);
	}

	// Non-null but empty array test
	@Test(expected = IllegalArgumentException.class)
	public void insertionSubEmptyTest() {
		InsertionSorter.insertionSort(valsEmpty, 0, 0);
	}
	
	//Sub array arg 1 less than 0
	@Test(expected = IllegalArgumentException.class)
	public void insertionSubArg1Test() {
		InsertionSorter.insertionSort(vals, -1, 3);
	}
	
	//Sub array arg 2 greater than size
	@Test(expected = IllegalArgumentException.class)
	public void insertionSubArg2Test() {
		InsertionSorter.insertionSort(vals, 0, 7);
	}
	
	//Sub array arg 1 greater than arg 2
	@Test(expected = IllegalArgumentException.class)
	public void insertionSubArgsTest() {
		InsertionSorter.insertionSort(vals, 4, 3);
	}

}