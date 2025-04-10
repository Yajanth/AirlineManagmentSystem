package com.airplane;

public class Airport {

	private int id;
	private String city;

	public Airport(int id2, String city2) {
		this.id = id2;
		this.city = city2;
		
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void print() {
		System.out.println(id+"\t"+city);
	}

}
