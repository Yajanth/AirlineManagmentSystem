package com.airplane;


public final class Pilot extends Employee {

    private String licenseNumber;

    public Pilot() {}

    public Pilot(int id, String firstName, String lastName, String email, String tel, double salary, String licenseNumber) {
        super(id, firstName, lastName, email, tel, salary, "Pilot");
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public void print() {
        super.print();
        System.out.println("License Number: " + licenseNumber);
        System.out.println();
    }
}
