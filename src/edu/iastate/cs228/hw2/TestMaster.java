package edu.iastate.cs228.hw2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;





public class TestMaster {
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
	
	


}
