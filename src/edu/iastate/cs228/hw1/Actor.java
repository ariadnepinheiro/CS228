package edu.iastate.cs228.hw1;
/**
 * 
 * @author Darren Hushak
 * Actor class
 *
 */
public class Actor {

	private static int counter = 0;
	private int id;
	private int x;
	private int y;
	private int iter;
	private World thisWorld;
	
	
	
	/**
	 * Construct a new Actor object. It sets the initial values of its member variables. 
	 * It sets the unique ID for the object and initializes the reference to the World 
	 * object to which this Actor object belongs to null. 
	 * The ID of the first Actor object is 0. 
	 * The ID gets incremented by one each time a new Actor object is created. 
	 * Set the iteration counter to zero and initialize the location of the 
	 * object to cell (0,0). 
	 */
	
	public Actor() {
		
		//set ID to current counter value
		this.id = counter;
		
		//Increment counter
		counter ++;
		
		//xy on grid is (0,0)
		this.x = 0;
		this.y = 0;
		
		//Iteration is 0
		this.iter = 0;
		
		//Has not been added to world yet
		this.thisWorld = null;
		
	}
	
	/**
	 * Print on screen in the format “Iteration <ID>: Actor <Actor ID>.” 
	 * The <ID> is replaced by the current iteration number. <Actor ID> is replaced by 
	 * the unique ID of the Actor object that performs the act() method. 
	 */
	
	public void act() {
		System.out.println("Iteration " + this.iter + ": Actor " + this.id);
		this.iter++;
	}
	
	
	/**
	 * 
	 * @param x the column
	 * @param y the row
	 * 
	 * Remember the cell coordinate of this object
	 * 
	 * @throws 
	 * 
	 * 	IllegalArgumentException when x < 0 or x >= world width
	 *  IllegalArgumentException when y < 0 or y >= world height
	 *  IllegalStateException when the world is null
	 */
	public void setLocation(int x, int y) {
		
		if(this.thisWorld == null){
			throw new NullPointerException();
		}
		
		if(x<0 || x >= this.thisWorld.getWidth() || y<0 || y >= this.thisWorld.getHeight()){
			throw new IllegalArgumentException();
		}
		
		this.x = x;
		this.y = y;
	}
	
	
	
	/**
	 * 
	 * @param world Reference to the World object this Actor object is added
	 * @throws
	 *	NullPointerException when world is null
	 */
	protected void addedToWorld(World world) {
		if(world == null){
			throw new NullPointerException();
		}
		
		this.thisWorld = world;
		
	}
	
	/**
	 * 
	 * @return the world this object belongs to
	 */
	public World getWorld() {
		return this.thisWorld;
	}
	
	/**
	 * 
	 * @return the x coordinate of this Actor object
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * 
	 * @return the y coordinate of this Actor object
	 */
	public int getY() {
		return this.y;
	}

}
