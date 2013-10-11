package edu.iastate.cs228.hw2;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class UtilsTest {

	int[] arr = { 1, 2, 3, 4, 5 };
	int[] arrSwapped = { 5, 2, 3, 4, 1 };
	int[] arrEmpty = {};

	// Test swap functionality
	@Test
	public void swapTest() {
		Utils.swap(arr, 0, 4);
		assertThat(arr, is(arrSwapped));
	}

	// Test swap null array
	@Test(expected = IllegalArgumentException.class)
	public void swapNullTest() {
		Utils.swap(null, 0, 0);
	}

	// Test swap empty array
	@Test(expected = IllegalArgumentException.class)
	public void swapEmptyTest() {
		Utils.swap(arrEmpty, 0, 0);
	}

	// Test swap pos1 negative
	@Test(expected = IllegalArgumentException.class)
	public void swapNegTest1(){
		Utils.swap(arr, -1, 3);
	}

	// Test swap pos1 out of bounds
	@Test(expected = IllegalArgumentException.class)
	public void swapBoundsTest1(){
		Utils.swap(arr, 5, 3);
	}

	// Test swap pos2 negative
	@Test(expected = IllegalArgumentException.class)
	public void swapNegTest2(){
		Utils.swap(arr, 3, -1);
	}

	// Test swap pos2 out of bounds
	@Test(expected = IllegalArgumentException.class)
	public void swapBoundsTest2(){
		Utils.swap(arr, 3, 5);
	}

	// Test checkArgs null array
	@Test(expected = IllegalArgumentException.class)
	public void checkArgsNullTest(){
		Utils.checkArgs(null);
	}
	
	// Test checkArgs empty array
	@Test(expected = IllegalArgumentException.class)
	public void checkArgsEmptyTest(){
		Utils.checkArgs(arrEmpty);
	}

	// Test checkValidIndex negative
	@Test(expected = IllegalArgumentException.class)
	public void checkIndexNegTest(){
		Utils.checkValidIndex(arr, -1);
	}

	// Test checkValidIndex out of bounds
	@Test(expected = IllegalArgumentException.class)
	public void checkIndexBoundsTest(){
		Utils.checkValidIndex(arr, 5);
	}
	
}
