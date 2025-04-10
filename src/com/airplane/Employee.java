package com.airplane;

import java.util.Objects;

public final class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;
    private double salary;
    private String position;

    public Employee() {}

    public Employee(int id, String firstName, String lastName, String email, String tel, double salary, String position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.salary = salary;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public double getSalaray() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void print() {
        System.out.println("id: " + id);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Tel: " + tel);
        System.out.println("Salary: " + salary);
        System.out.println("Position: " + position);
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Double.compare(employee.salary, salary) == 0 &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(lastName, employee.lastName) &&
                Objects.equals(email, employee.email) &&
                Objects.equals(tel, employee.tel) &&
                Objects.equals(position, employee.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, tel, salary, position);
    }

    @Override
    public String toString() {
        return String.format("Employee{id=%d, name='%s %s', email='%s', tel='%s', salary=%.2f, position='%s'}",
                id, firstName, lastName, email, tel, salary, position);
    }
}
