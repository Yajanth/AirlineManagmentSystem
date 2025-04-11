package com.airplane;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EmployeesController {
    private static final List<Employee> employees = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void addNewEmployee(Scanner s) {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();
        System.out.println("Enter email: ");
        String email = s.next();
        System.out.println("Enter Tel: ");
        String tel = s.next();
        System.out.println("Enter salary (double): ");
        double salary = s.nextDouble();
        System.out.println("Enter position: ");
        String position = s.next();

        int id = employees.isEmpty() ? 0 : employees.get(employees.size() - 1).getId() + 1;
        Employee employee = new Employee(id, firstName, lastName, tel, email, salary, position);
        employees.add(employee);
        System.out.println("Employee added successfully!");
    }

    public static void editEmployee(Scanner s) {
        System.out.println("Enter employee id: ");
        int id = s.nextInt();
        Optional<Employee> optionalEmployee = employees.stream().filter(e -> e.getId() == id).findFirst();

        if (optionalEmployee.isEmpty()) {
            System.out.println("Employee not found!");
            return;
        }

        Employee employee = optionalEmployee.get();

        System.out.println("Enter first name (-1 to keep old): ");
        String firstName = s.next();
        if (!firstName.equals("-1")) employee.setFirstName(firstName);

        System.out.println("Enter last name (-1 to keep old): ");
        String lastName = s.next();
        if (!lastName.equals("-1")) employee.setLastName(lastName);

        System.out.println("Enter Tel (-1 to keep old): ");
        String tel = s.next();
        if (!tel.equals("-1")) employee.setTel(tel);

        System.out.println("Enter email (-1 to keep old): ");
        String email = s.next();
        if (!email.equals("-1")) employee.setEmail(email);

        System.out.println("Enter salary (-1 to keep old): ");
        double salary = s.nextDouble();
        if (salary != -1) employee.setSalary(salary);

        System.out.println("Enter position (-1 to keep old): ");
        String position = s.next();
        if (!position.equals("-1")) employee.setPosition(position);

        System.out.println("Employee updated successfully!");
    }

    public static void findEmployeeByName(Scanner s) {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();

        employees.stream()
                .filter(e -> e.getFirstName().equalsIgnoreCase(firstName) && e.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .ifPresentOrElse(Employee::print, () -> System.out.println("Employee not found"));
    }

    public static void printAllEmployees() {
        employees.forEach(Employee::print);
    }

    public static void deleteEmployee(Scanner s) {
        System.out.println("Enter employee id to delete: ");
        int id = s.nextInt();
        boolean removed = employees.removeIf(e -> e.getId() == id);
        if (removed) {
            System.out.println("Employee deleted successfully!");
        } else {
            System.out.println("Employee not found.");
        }
    }

    public static Employee getEmployeeByID(int id) {
        return employees.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public static void sortBySalary() {
        System.out.println("---  Sorted by Salary ---");
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .forEach(Employee::print);

        System.out.println("--- Count of Employees with Salary > 50000 ---");
        long count = employees.stream().filter(e -> e.getSalary() > 50000).count();
        System.out.println("Count: " + count);
    }
}
