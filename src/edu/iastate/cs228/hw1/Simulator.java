package edu.iastate.cs228.hw1;

public class Simulator {
	static int numItr = 5;
	public static void main (String[] args){
		if (args.length > 0) {
			numItr = Integer.parseInt(args[0]);
		}
		System.out.println("Simulation of MyWorld");
		MyWorld myWorldSim = new MyWorld();
		for (int i=0;i<numItr;i++){
			((MyWorld)myWorldSim).act();
			Object[] objArr= myWorldSim.getObjects();
			for (int j=0;j<objArr.length;j++){
				((Disease) objArr[j]).act();
			}
		}
		
		System.out.println("\nSimulation of World");
		World worldSim = new World(100,100);
		Actor act1 = new Actor();
		Actor act2 = new Actor();
		worldSim.addObject(act1, 10, 10);
		worldSim.addObject(act2, 90, 90);
		
		for (int i=0;i<numItr;i++){
			worldSim.act();
			Object[] objArr= worldSim.getObjects();
			for (int j=0;j<objArr.length;j++){
				((Actor) objArr[j]).act();
			}
		}
		
	}
	
//	Print on screen ÒSimulation of MyWorldÓ
//	new MyWorld object.
//	for (int i=0; i< numItr; i++) 
//	call the act() method of the MyWorld object. 
//	call the getObjects() method to get all objects in the MyWorld object
//	for each object in the Object array, 
//	call the act() method of that object.
	
//	Print on screen ÒSimulation of World.Ó
//	new World(100,100)
//	Add 2 Actor objects into the world at the location (10,10), (90,90).
//	for (int i=0; i< numItr; i++) 
//	call the act() method of the World object. 
//	call the getObjects() method to get all objects in the World object
//	for each of the object in the Object array, 
//	call the act() method of that object.
}
