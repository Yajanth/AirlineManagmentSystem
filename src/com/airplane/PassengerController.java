package com.airplane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Comparator;

public class PassengerController {

    private static final List<Passenger> passengers = new ArrayList<>();

    public static void addNewPassenger(Scanner s) {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();
        System.out.println("Enter tel: ");
        String tel = s.next();
        System.out.println("Enter email: ");
        String email = s.next();

        int id = passengers.isEmpty() ? 0 : passengers.get(passengers.size() - 1).getId() + 1;
        Passenger p = new Passenger(id, firstName, lastName, tel, email);
        passengers.add(p);

        System.out.println("Passenger added successfully!");
    }

    public static void editPassenger(Scanner s) {
        System.out.println("Enter passenger id: ");
        int id = s.nextInt();
        Optional<Passenger> optionalPassenger = passengers.stream().filter(p -> p.getId() == id).findFirst();

        if (optionalPassenger.isEmpty()) {
            System.out.println("Passenger not found!");
            return;
        }

        Passenger passenger = optionalPassenger.get();

        System.out.println("Enter first name (-1 to keep old): ");
        String firstName = s.next();
        if (!firstName.equals("-1")) passenger.setFirstName(firstName);

        System.out.println("Enter last name (-1 to keep old): ");
        String lastName = s.next();
        if (!lastName.equals("-1")) passenger.setLastName(lastName);

        System.out.println("Enter Tel (-1 to keep old): ");
        String tel = s.next();
        if (!tel.equals("-1")) passenger.setTel(tel);

        System.out.println("Enter email (-1 to keep old): ");
        String email = s.next();
        if (!email.equals("-1")) passenger.setEmail(email);

        System.out.println("Passenger updated successfully!");
    }

    public static void findPassengerByName(Scanner s) {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();

        passengers.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .ifPresentOrElse(Passenger::print, () -> System.out.println("Passenger not found"));
    }

    public static void printAllPassengers() {
        passengers.forEach(Passenger::print);
    }

    public static void deletePassenger(Scanner s) {
        System.out.println("Enter passenger id to delete: ");
        int id = s.nextInt();
        passengers.removeIf(p -> p.getId() == id);
        System.out.println("Passenger deleted successfully!");
    }

    public static void useStreamsDemo() {
        System.out.println("--- Stream Example: Sorted by First Name ---");
        passengers.stream()
                .sorted(Comparator.comparing(Passenger::getFirstName))
                .forEach(Passenger::print);

        System.out.println("--- Count of Passengers with Last Name starting with 'A' ---");
        long count = passengers.stream()
                .filter(p -> p.getLastName().toLowerCase().startsWith("a"))
                .count();
        System.out.println("Count: " + count);
    }

	public static Passenger getPassengerByID(int passengerID) {
		// TODO Auto-generated method stub
		return null;
	}
} 