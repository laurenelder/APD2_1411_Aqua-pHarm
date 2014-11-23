package com.laurenelder.aquapharm;

public class Fish {

	public String name;
	public Integer minTemp;
	public Integer maxTemp;
	public String image;
	public String edible;

	public Fish(String fishName, Integer minfishTemp, Integer maxfishTemp, String 
			fishImage, String fishEdible) {
		this.name = fishName;
		this.minTemp = minfishTemp;
		this.maxTemp = maxfishTemp;
		this.image = fishImage;
		this.edible = fishEdible;
	}

	public String toString() {
		return name;
	}

}
