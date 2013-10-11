package edu.iastate.cs228.hw1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ActorTest extends TestMaster {
	Actor actorTest = new Actor();
	World testWorld = new World(100,100);


//	@Test
//	public void SecondActTester(){
//		Actor actorTest2 = new Actor();
//		actorTest2.act();
//		System.out.flush();
//		assertEquals("Iteration 0: Actor 8\n", newout.toString());
//	}
	
	@Test
	public void ActTester(){
		actorTest.act();
		actorTest.act();
		System.out.flush();
		assertEquals("Iteration 0: Actor 7\nIteration 1: Actor 7\n", newout.toString());
	}
	
	
	@Test(expected = NullPointerException.class) 
	public void NullWorldTester(){
		actorTest.addedToWorld(null);
	}
	
	@Test(expected = NullPointerException.class) 
	public void NullWorldLocationTester(){
		actorTest.setLocation(3,7);
	}
	
	@Test
	public void UninitializedX(){
		assertEquals((Object) 0,(Object) actorTest.getX());
	}
	
	@Test
	public void UninitializedY(){
		assertEquals((Object) 0,(Object) actorTest.getY());
	}
	

	@Test
	public void AddedWorld(){
		actorTest.addedToWorld(testWorld);
		assertEquals((Object) testWorld,(Object) actorTest.getWorld());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void OutofBoundsX(){
		actorTest.addedToWorld(testWorld);
		actorTest.setLocation(101,5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void OutofBoundsY(){
		actorTest.addedToWorld(testWorld);
		actorTest.setLocation(5,101);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void LowBoundsX(){
		actorTest.addedToWorld(testWorld);
		actorTest.setLocation(-30,5);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void LowBoundsY(){
		actorTest.addedToWorld(testWorld);
		actorTest.setLocation(5,-30);
	}
	
	@Test
	public void LocationSetTester(){
		actorTest.addedToWorld(testWorld);
		actorTest.setLocation(3,7);
		assertEquals((Object) 3,(Object) actorTest.getX());
		assertEquals((Object) 7,(Object) actorTest.getY());
		//Change a second time to make sure things still hit right
		actorTest.setLocation(80,35);
		assertEquals((Object) 80,(Object) actorTest.getX());
		assertEquals((Object) 35,(Object) actorTest.getY());
		//Test low bounds
		actorTest.setLocation(0,0);
		assertEquals((Object) 0,(Object) actorTest.getX());
		assertEquals((Object) 0,(Object) actorTest.getY());
		//Test high bounds
		actorTest.setLocation(testWorld.getWidth()-1,testWorld.getHeight()-1);
		assertEquals((Object) (testWorld.getWidth()-1),(Object) actorTest.getX());
		assertEquals((Object) (testWorld.getHeight()-1),(Object) actorTest.getY());
	}
	

}
