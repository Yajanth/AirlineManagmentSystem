package com.airplane;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Comparator;

public class PassengerController {

    private static final List<Passenger> passengers = new ArrayList<>();
    private static final Path passengerFile = Paths.get("passengers.txt");

    // Method to load existing passengers from a file
    public static void loadPassengerData() {
        Path path = Paths.get("passengers.txt");

        if (Files.exists(path)) {
            try {
                List<String> lines = Files.readAllLines(path);

                for (String line : lines) {
                    String[] details = line.split(",");
                    if (details.length == 5) {
                        int id = Integer.parseInt(details[0]);
                        String firstName = details[1];
                        String lastName = details[2];
                        String tel = details[3];
                        String email = details[4];
                        passengers.add(new Passenger(id, firstName, lastName, tel, email));
                    } else {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }

                System.out.println("Loaded " + passengers.size() + " passengers from file.");

            } catch (IOException e) {
                System.out.println("Error reading passenger data: " + e.getMessage());
            }
        } else {
            System.out.println("passenger.txt not found!");
        }
    }

    // Method to save passenger data to the file
    public static void savePassengerData() {
        try (BufferedWriter writer = Files.newBufferedWriter(passengerFile)) {
            for (Passenger p : passengers) {
                String passengerData = String.format("%d,%s,%s,%s,%s%n",
                        p.getId(), p.getFirstName(), p.getLastName(), p.getTel(), p.getEmail());
                writer.write(passengerData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add a new passenger
    public static void addNewPassenger(Scanner s) {
        System.out.println("Enter first name: ");
        String firstName = s.next();
        System.out.println("Enter last name: ");
        String lastName = s.next();
        System.out.println("Enter tel: ");
        String tel = s.next();
        System.out.println("Enter email: ");
        String email = s.next();

        int id = passengers.isEmpty() ? 1 : passengers.get(passengers.size() - 1).getId() + 1;
        Passenger p = new Passenger(id, firstName, lastName, tel, email);
        passengers.add(p);

        System.out.println("Passenger added successfully!");
        savePassengerData();  // Save to file after adding
    }

    // Method to edit a passenger's details
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
        savePassengerData();  // Save to file after editing
    }

    // Method to find a passenger by name
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

    // Method to print all passengers
    public static void printAllPassengers() {
    	PassengerController.loadPassengerData();
    	System.out.println("----------------------------------------------------");
        passengers.forEach(Passenger::print);
    }

    // Method to delete a passenger
    public static void deletePassenger(Scanner s) {
        System.out.println("Enter passenger id to delete: ");
        int id = s.nextInt();
        passengers.removeIf(p -> p.getId() == id);
        System.out.println("Passenger deleted successfully!");
        savePassengerData();  // Save to file after deleting
    }

    // Stream example: Sort passengers by first name
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

    // Method to get a passenger by ID
    public static Passenger getPassengerByID(int passengerID) {
        return passengers.stream()
                .filter(passenger -> passenger.getId() == passengerID)
                .findFirst()
                .orElse(null);
    }
}
