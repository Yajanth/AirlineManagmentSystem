package com.airplane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Comparator;

public class AirportsController {

    private static final List<Airport> airports = new ArrayList<>();

    public static void addNewAirport(Scanner s) {
        System.out.println("Enter city: ");
        String city = s.next();

        int id = airports.isEmpty() ? 0 : airports.get(airports.size() - 1).getID() + 1;
        Airport airport = new Airport(id, city);
        airports.add(airport);

        System.out.println("Airport added successfully!");
    }

    public static void printAllAirports() {
        System.out.println("----------------");
        System.out.println("id\tcity");
        airports.forEach(Airport::print);
        System.out.println("----------------");
    }

    public static void editAirport(Scanner s) {
        System.out.println("Enter airport id (int): ");
        int id = s.nextInt();

        Optional<Airport> optionalAirport = airports.stream().filter(a -> a.getID() == id).findFirst();

        if (optionalAirport.isEmpty()) {
            System.out.println("Airport not found!");
            return;
        }

        Airport airport = optionalAirport.get();
        System.out.println("Enter new city: ");
        String city = s.next();
        airport.setCity(city);

        System.out.println("Airport edited successfully!");
    }

    public static void deleteAirport(Scanner s) {
        System.out.println("Enter airport id to delete: ");
        int id = s.nextInt();

        boolean removed = airports.removeIf(a -> a.getID() == id);

        if (removed) {
            System.out.println("Airport deleted successfully!");
        } else {
            System.out.println("Airport not found!");
        }
    }

    public static Airport getAirportById(int id) {
        return airports.stream()
                .filter(a -> a.getID() == id)
                .findFirst()
                .orElse(null);
    }

    public static List<Airport> getAllAirports() {
        return airports;
    }
} 
