package edu.iastate.cs228.hw1;


public interface IWorld {
	public void prepare();
	public void setTemp(int quad, double temp);
	public double getTemp(int quad);
	public Object[] getObjects();
	public double getSumStrength();
	public Disease[] initDiseases(String numDisStr);
	public int initLocations(String locationsStr, Disease[] diseaseArr);
	public int initGrowthConditions(String growthStr, Disease[] diseaseArr);
	public int initTemps(String tempStr);
}
