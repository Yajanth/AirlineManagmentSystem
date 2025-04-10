package com.airplane;

public record Airplane(int id, int economyCapacity, int businessCapacity, String model) {

//	private int id;
//	private int EconomyCapacity;
//	private int BusinessCapacity;
//	private String model;
//
//	public Airplane() {}
//
//	public Airplane(int id2, int economyCapacity2, int businessCapacity2, String model2) {
//		
//		this.id = id2;
//		this.EconomyCapacity = economyCapacity2;
//		this.BusinessCapacity = businessCapacity2;
//		this.model = model2;
//	}


	public void print() {
		System.out.println("id: "+id);
		System.out.println("Economy Capacity: "+ economyCapacity);
		System.out.println("Business Capacity: "+ businessCapacity);
		System.out.println("Model: " + model);
		System.out.println();
	}

}
