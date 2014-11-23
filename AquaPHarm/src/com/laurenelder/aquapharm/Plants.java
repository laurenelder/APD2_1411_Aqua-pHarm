package com.laurenelder.aquapharm;

public class Plants {

	public String name;
	public Integer minTemp;
	public Integer maxTemp;
	public String image;
	public String edible;

	public Plants(String plantName, Integer minPlantTemp, Integer maxPlantTemp, 
			String plantImage, String plantEdible) {
		this.name = plantName;
		this.minTemp = minPlantTemp;
		this.maxTemp = maxPlantTemp;
		this.image = plantImage;
		this.edible = plantEdible;
	}

	public String toString() {
		return name;
	}

}
