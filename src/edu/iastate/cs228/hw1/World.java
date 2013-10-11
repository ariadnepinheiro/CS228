package edu.iastate.cs228.hw1;

/**
 * 
 * @author Darren Hushak
 * 
 * World class keeps Actor objects in some cells of the world
 *
 */

public class World {
	
	private int width;
	private int height;
	private Actor[][][] grid;
	private int numObjects;
	/*
	 * @param worldWidth Width in number of cells 
	 * @param worldHeight Height in number of cells
	 * 
	 *  The maximum width and height are 1000.
	 *  The maximum number of Actor objects in a cell is 5.
	 *  If worldWidth <= 0 or worldWidth > maximum width
	 *     use the maximum width instead
	 *  If worldHeight <=0 or worldHeight > maximum height
	 *     use the maximum height instead
	 * 
	 */
	
	public World(int worldWidth, int worldHeight) {
		if (worldWidth > 1000){
			worldWidth = 1000;
		}
		if (worldHeight > 1000){
			worldHeight = 1000;
		}
		if (worldWidth <= 0){
			worldWidth = 0;
		}
		if (worldHeight <= 0){
			worldHeight = 0;
		}
		
		this.width = worldWidth;
		this.height = worldHeight;
		this.numObjects = 0;
		
		grid = new Actor[worldWidth][worldHeight][5];
		for(int i=0;i<worldWidth;i++){
			for(int j=0;j<worldHeight;j++){
				for(int k=0;k<5;k++)
					grid[i][j][k]=null;
			}
		}
		
		setGrid(grid, 0);
	
	}
	/**
	 * To be overridden by its subclass
	 */
	public void act() {
		
	}
	
	/**
	 * 
	 * @param object the object to be add at this cell (x, y)
	 * @param x the column
	 * @param y the row
	 * 
	 * The new object will be added at the cell (x,y) if there are less than 5 objects in this cell
	 * Be sure to make the added object know that it is in this world and it is at this cell.
	 * Check which methods of the Actor class to call.
	 * 
	 * @throws IllegalStateException when already max number of objects are in that cell
	 * @throws IllegalArgumentException if x or y is not in the valid range
	 * @throws NullPointerException if the object is null
	 * 
	 * 
	 */
	public void addObject(Actor object, int x, int y) {
		if (grid[x][y][4]!=null){
			throw new IllegalStateException();
		}
		if (x>=this.width || y >= this.height || x<0 || y<0){
			throw new IllegalArgumentException();
		}
		if (object == null){
			throw new NullPointerException();
		}
		
		for(int i=0;i<5;i++){
			if (grid[x][y][i] == null){
				grid[x][y][i] = object;
				this.numObjects ++;
				i = 5;
			}
		}
		
		object.addedToWorld(this);
		object.setLocation(x,y);

	}
	
	/**
	 * 
	 * @return the world height
	 */
	public int getHeight() {
		
		return this.height;
	}
	/**
	 * 
	 * @return the world width
	 */
	public int getWidth() {
		return this.width;
	}
	/**
	 * 
	 * @return Total number of objects in the world
	 */
	public int numberOfObjects() {
		
		return this.numObjects;
		
	}
	
	/**
	 * 
	 * @return Array of Actor objects that are in this world
	 * 
	 * up cast
	 */
	public Object[] getObjects() {
		Object[] objArr = new Object[this.numObjects];
		int arrCounter = 0;
		for(int i=0;i<this.width;i++){
			for(int j=0;j<this.height;j++){
				for(int k=0;k<5;k++)
					if(grid[i][j][k]!=null){
						objArr[arrCounter] = (Object) grid[i][j][k];
						arrCounter++;
					}
			}
		}
		return objArr;
		
	}
	
	
	
	/** To make this method work with you code, you need to complete the TODO parts below.
	 *
	 *  It checks if aGrid is a 3D array with the same positive length in each dimension.
	 *  If so, it sets the grid to aGrid and the other private fields of class World to 
	 *  the dimension lengths of aGrid and numObjs.
	 *
	 *  Note that some checks are omitted. For example, no check is performed to make sure
	 *  that numObjs is consistent with the number of Actor objects in aGrid.
	 *
	 *  Each Actor object in aGrid has to be set to this World object.
	 *
	 * @param aGrid reference to a 3D array of Actor objects.
	 *
	 * @param numObjs the number of Actor objects in aGrid.
	 *
	 * @throws IllegalArgumentException if the length of each dimension is out of range
	 *         or 2nd/3rd dimension has different lengths.
	*/
	
	public void setGrid(Actor[][][] aGrid, int numObjs) {

		 int nrow = aGrid.length;
		 if ( nrow < 1 )
			throw new IllegalArgumentException("1stD length is out of range");

	     int ncol = aGrid[0].length;
	     if ( ncol < 1 )
			 throw new IllegalArgumentException("2ndD length is out of range");

		 int ncel = aGrid[0][0].length;
		 if ( ncel != 5 )
			 throw new IllegalArgumentException("3rdD length is not " + 5);

		 for ( int j = 0; j < nrow; j++ ) {

		    if ( aGrid[j].length != ncol )
		       throw new IllegalArgumentException("Different 2ndD lengths");

		    for ( int k = 0; k < ncol; k++ )
		        if ( aGrid[j][k].length != ncel )
			       throw new IllegalArgumentException("Different 3rdD lengths");
		  } // checks if each dimension is of the same length.

		  this.grid = aGrid;
		  this.width = nrow;
		  this.height = ncol;
		  this.numObjects = numObjs;

	} 
}
