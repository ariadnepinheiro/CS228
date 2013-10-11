package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MyWorld extends World implements IWorld{

	private int numDiseases;
	private Disease[] diseaseArr;
	private double temperature[] = new double[4];
	private int iter;
	
	public MyWorld(){
		super(720,640);
		for (int i=0;i<4;i++){
			this.temperature[i] = 0.0;
		}
		this.numDiseases = 0;
		this.prepare();
		this.iter = 0;
	}
	
	
	@Override
	public void prepare() {
		//Initialize variables
		String numDisStr = "";
		String locationStr = "";
		String growthStr = "";
		String tempStr = "";
		
		
		FileReader inputFile = null;

		try {
			inputFile = new FileReader("simulation.config");
		} catch (FileNotFoundException e) {
			 String current;
				try {
					current = new java.io.File( "." ).getCanonicalPath();
			        System.out.println("Place file in this directory:"+current);
				} catch (IOException e1) {
			}
			System.out.println("Terminating the program.");
			System.exit(-1);
		}
		Scanner sc = new Scanner(inputFile);
		while(sc.hasNextLine()){
			String line;
			line = sc.nextLine(); // read the next line into a String object
			String[] sArr = line.split("="); // split the string at Ò=Ó; sArr is an array of String
			if(sArr[0].equalsIgnoreCase("NumDiseases")){
				numDisStr = sArr[1];
			}
			if(sArr[0].equalsIgnoreCase("Locations")){
				locationStr = sArr[1];
			}
			if(sArr[0].equalsIgnoreCase("DiseasesGrowth")){
				growthStr = sArr[1];
			}
			if(sArr[0].equalsIgnoreCase("Temperature")){
				tempStr = sArr[1];
			}
			
			
		}
		//Error tracker - if all works well, err should stay 0 
		int err = 0;
		
		this.diseaseArr = initDiseases(numDisStr);
		if (diseaseArr == null){
			err--;
		}
		err += initLocations(locationStr, this.diseaseArr);
		err += initGrowthConditions(growthStr, this.diseaseArr);
		err += initTemps(tempStr);
		
		//Check for error
		if (err != 0){
			System.out.println("Terminating the program.");
			System.exit(-1);
		}
		
	}
	
	@Override
	public void act(){
		System.out.println("Iteration " + this.iter + ": World disease strength is " + this.getSumStrength());
		this.iter++;
	}

	@Override
	public void setTemp(int quad, double temp) {
		if(quad< 0 || quad > 3){
			throw new IllegalArgumentException();
		}
		this.temperature[quad] = temp;
		
	}

	@Override
	public double getTemp(int quad) {
		if(quad< 0 || quad > 3){
			throw new IllegalArgumentException();
		}
		return this.temperature[quad];
	}

	@Override
	public double getSumStrength() {
		double strengthSum = 0.0;
		Object[] objArr = this.getObjects();
		for (int i=0;i<objArr.length;i++){
			strengthSum += ((Disease) objArr[i]).getStrength();
		}
		return strengthSum;
	}

	
	@Override
	public Disease[] initDiseases(String numDisStr) {
		//Check for null number of diseases
		if (numDisStr == null){
			System.out.println("ÒCheck the NumDiseases line in simulation.config.");
			return null;
		}
		
		//This is here because the freakin' Eclipse debugger needs an instance of Disease to be declared before it can be used as an array of Diseases. Stupid.
		//Disease test = new Disease();
		
		
		this.numDiseases = Integer.parseInt(numDisStr);
		Disease[] diseaseArr = new Disease[this.numDiseases];
		for (int i=0;i<this.numDiseases;i++){
			diseaseArr[i]=new Disease();
		}
		return diseaseArr;	
	}

	@Override
	public int initLocations(String locationsStr, Disease[] diseaseArr) {
		
		//Check for any non-numbers, periods, or semicolons in the string
		if(locationsStr.matches("^[0-9\\.;,]+$")){
			
			//Split string by semicolons
			String[] locs = locationsStr.split(";");
			//Check for correct number of disease coordinate values
			
			if (locs.length == this.numDiseases){
				//Loop through all coordinate pairs
				for (int i=0;i<locs.length;i++){
					String[] coords = locs[i].split(",");
					
						//Check for correct coordinate formatting
						if (coords.length!=2){
							System.out.println("Check the Locations line in simulation.config");
							return -1;
						}
					//Fill disease locations with value of disease string
					addObject(diseaseArr[i],Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
//					diseaseArr[i].setLocation(Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
				}
				//Successful
				return 0;
			}
		}
		
		//Unsuccessful
		System.out.println("Check the Locations line in simulation.config");
		return -1;
	}

	@Override
	public int initGrowthConditions(String growthStr, Disease[] diseaseArr) {
		
		//Check for any non-numbers, periods, or semicolons in the string
		if(growthStr.matches("^[0-9\\.;,]+$")){
			
			//Split string by semicolons
			String[] growths = growthStr.split(";");
			
			//Check for correct number of disease coordinate values
			if (growths.length == this.numDiseases){
				
				//Loop through all growth conditions
				for (int i=0;i<growths.length;i++){
					String[] vals = growths[i].split(",");
					
						//Check for correct coordinate formatting
						if (vals.length!=3){
							System.out.println("Check the DiseasesGrowth line in simulation.config.");
							return -1;
						}
					//Fill disease growth conditions with value of disease growth condition string
					diseaseArr[i].setGrowthCondition(Double.parseDouble(vals[0]),Double.parseDouble(vals[1]),Double.parseDouble(vals[2]));
				}
				//Successful
				return 0;
			}
		}
		//If incorrect, print a warning and return -1
		System.out.println("Check the DiseasesGrowth line in simulation.config.");
		return -1;
	}

	@Override
	public int initTemps(String tempStr) {
		//Check for any non-numbers, periods, or semicolons in the string
		if(tempStr.matches("^[0-9\\.;,]+$")){
			String[] temps = tempStr.split(";");
			//Check for four temperatures
			if (temps.length == 4){
				//Fill temperatures array with value of temperature string
				for (int i=0;i<4;i++){
					this.temperature[i] = Double.parseDouble(temps[i]);
				}
				return 0;
			}
		}
		//If null or not 4 temperatures, print a warning and return 0
		System.out.println("Check the Temperature line in simulation.config.");
		return -1;
	}
	

}
