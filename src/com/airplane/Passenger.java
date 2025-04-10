package com.airplane;

public class Passenger {

	private int id;
	private String firstName;
	private String lastName;
	private String Tel;
	private String email;

	public Passenger() {}

	public Passenger(int id2, String firstName2, String lastName2, String tel2, String email2) {
		this.id = id2;
		this.firstName =firstName2;
		this.lastName = lastName2;
		this.Tel = tel2;
		this.email = email2;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setTel(String Tel) {
		this.Tel = Tel;
	}

	public String getTel() {
		return Tel;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void print() {
		System.out.println("id: "+getId());
		System.out.println("Name: "+getFirstName()+" "+getLastName());
		System.out.println("Tel: "+getTel());
		System.out.println("Email: "+getEmail());
		System.out.println();
	}

}
