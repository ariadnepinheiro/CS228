package edu.iastate.cs228.hw1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DiseaseTest extends TestMaster {
	Disease diseaseTest = new Disease();
	MyWorld testWorld = new MyWorld();

	// @Test
	// public void SecondActTester(){
	// Actor actorTest2 = new Actor();
	// actorTest2.act();
	// System.out.flush();
	// assertEquals("Iteration 0: Actor 8\n", newout.toString());
	// }

	@Test
	public void LocationSetTester() {
		// Add to world
		diseaseTest.addedToWorld(testWorld);

		// Set Growth Conditions
		diseaseTest.setGrowthCondition(1.0, 35.0, 2.0);

		// Set somewhere in quadrant 1;
		diseaseTest.setLocation(50, 50);

		// Initial temp is 0, out of range, should not grow
		diseaseTest.act();
		assertEquals((Object) 1.0, (Object) diseaseTest.getStrength());

		// Change Growth Conditions
		diseaseTest.setGrowthCondition(-5.0, 35.0, 2.0);

		// Initial temp is 0, in range, should grow
		diseaseTest.act();
		assertEquals((Object) 2.0, (Object) diseaseTest.getStrength());

		// Set Growth Conditions, shoudn't grow this time
		diseaseTest.setGrowthCondition(-5, -3, 2.0);
		diseaseTest.act();
		assertEquals((Object) 2.0, (Object) diseaseTest.getStrength());

		// Reset growth conditions to temp in range, change growth rate to grow
		// faster
		diseaseTest.setGrowthCondition(-5, 5, 5.0);
		diseaseTest.act();
		assertEquals((Object) 10.0, (Object) diseaseTest.getStrength());

		// Change temp in quadrant to out of range, shouldn't grow
		testWorld.setTemp(0, 10.0);
		diseaseTest.act();
		assertEquals((Object) 10.0, (Object) diseaseTest.getStrength());

		// Switch to quadrant 1
		diseaseTest.setLocation(700, 25);
		diseaseTest.act();
		assertEquals((Object) 50.0, (Object) diseaseTest.getStrength());

		// Change temp in quadrant to out of range, shouldn't grow
		testWorld.setTemp(1, 10.0);
		diseaseTest.act();
		assertEquals((Object) 50.0, (Object) diseaseTest.getStrength());

		// Change temp in other quadrant just to make sure things aren't being
		// weird
		testWorld.setTemp(0, 0.0);
		diseaseTest.act();
		assertEquals((Object) 50.0, (Object) diseaseTest.getStrength());

		// Switch to quadrant 2, test in range and out of range
		diseaseTest.setLocation(25, 600);
		diseaseTest.act();
		assertEquals((Object) 250.0, (Object) diseaseTest.getStrength());
		// Out of range now, shouldn't grow
		testWorld.setTemp(2, 10.0);
		diseaseTest.act();
		assertEquals((Object) 250.0, (Object) diseaseTest.getStrength());

		// Switch to quadrant 3, test in range and out of range
		diseaseTest.setLocation(600, 600);
		diseaseTest.act();
		assertEquals((Object) 1250.0, (Object) diseaseTest.getStrength());
		// Out of range now, shouldn't grow
		testWorld.setTemp(3, 10.0);
		diseaseTest.act();
		assertEquals((Object) 1250.0, (Object) diseaseTest.getStrength());

	}

}
