package com.laurenelder.aquapharm;

public class Containers {

	public String type;
	public String length;
	public String width;
	public String height;

	public Containers(String containerType, String containerLength, String containerWidth, 
			String containerHeight) {
		this.type = containerType;
		this.length = containerLength;
		this.width = containerWidth;
		this.height = containerHeight;
	}

	public String toString() {
		return type;
	}

}
