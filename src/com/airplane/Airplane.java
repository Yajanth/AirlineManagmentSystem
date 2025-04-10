package com.airplane;

public record Airplane(int id, int economyCapacity, int businessCapacity, String model) {


	public void print() {
		System.out.println("id: "+id);
		System.out.println("Economy Capacity: "+ economyCapacity);
		System.out.println("Business Capacity: "+ businessCapacity);
		System.out.println("Model: " + model);
		System.out.println();
	}

}
