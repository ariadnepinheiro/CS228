package edu.iastate.cs228.hw1;

/**
 * 
 * @author Darren Hushak
 * 
 * This Disease class is a sub-class of the Actor class.
 *
 */

public class Disease extends Actor implements IDisease {
	
	// Declare fields to store the temperatures and the growth rate
	// Add other fields as necessary

	private double lTemp;
	private double hTemp;
	private double gRate;
	private double strength;
	
	public Disease() {
		super();
		this.lTemp = 0;
		this.hTemp = 0;
		this.gRate = 0;
		this.strength = 1;
	}
	
	
	/**
	 * @param lTemp Lower bound temperature for the disease to grow at this gRate
	 * @param hTemp Upper bound temperature for the disease to grow at this gRate
	 * @param gRate The growth rate
	 */
	public void setGrowthCondition(double lTemp, double hTemp, double gRate) {
		this.lTemp = lTemp;
		this.hTemp = hTemp;
		this.gRate = gRate;
		
	}
	
	
	/**
	 * Override the act() method of Actor
	 * 
	 */
	@Override
	public void act() {
		//Find quadrant
		int halfwidth = ((MyWorld) this.getWorld()).getWidth()/2;
		int halfheight = ((MyWorld) this.getWorld()).getHeight()/2;
		
		int quadrant = this.getX()/halfwidth;
		if (this.getY()>halfheight){
			quadrant +=2;
		}
		
		
		double currentTemp = ((MyWorld) this.getWorld()).getTemp(quadrant);
		
		if (currentTemp>=this.lTemp && currentTemp <= this.hTemp){
			this.strength *= this.gRate;
		}
	
		
		
	}

	@Override
	public double getStrength() {
		return this.strength;
	}

}
