package com.airplane;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public class AirplanesController {

    // In-memory storage for airplanes
    private static ArrayList<Airplane> airplanes = new ArrayList<>();

    public static void AddNewAirplane(Scanner s) {
        System.out.println("Enter economy capacity (int): ");
        int EconomyCapacity = s.nextInt();
        System.out.println("Enter business capacity (int): ");
        int BusinessCapacity = s.nextInt();
        System.out.println("Enter model: ");
        String model = s.next();

        // Generate a new ID based on the last airplane in the list
        int id = airplanes.size() > 0 ? airplanes.get(airplanes.size() - 1).id() + 1 : 0;

        // Create a new airplane object
        Airplane airplane = new Airplane(id, EconomyCapacity, BusinessCapacity, model);

        // Add airplane to the in-memory list
        airplanes.add(airplane);
        System.out.println("Airplane added successfully!");
    }

    // Implemented Functional Interface with Lambda Function
    public static void PrintAllPlanes() {
        System.out.println("---------------------------");
        // Using a lambda expression to print airplane details
        airplanes.forEach(plane -> System.out.println("Airplane Model: " + plane.model()));
        System.out.println("---------------------------");
    }

    // Using Lambda Expression and Stream API to find plane by ID
    public static Airplane getPlaneByID(int id) {
        return airplanes.stream()
                .filter(plane -> plane.id() == id)
                .findFirst()
                .orElse(null);  // Return null if not found
    }

    public static void EditAirplane(Scanner s) {
        System.out.println("Enter id (int): \n(-1 to show all planes)");
        int id = s.nextInt();
        if (id == -1) {
            PrintAllPlanes();
            System.out.println("Enter id (int): ");
            id = s.nextInt();
        }

        Airplane p = getPlaneByID(id);
        if (p == null) {
            System.out.println("Airplane not found!");
            return;
        }

        // Using lambda for conditionally setting capacity and model
        int EconomyCapacity = getUserInputOrKeepOldValue(s, "economy capacity", p.economyCapacity());
        int BusinessCapacity = getUserInputOrKeepOldValue(s, "business capacity", p.businessCapacity());
        String Model = getUserInputOrKeepOldValue(s, "model", p.model());

        // Update airplane details
        Airplane updatedAirplane = new Airplane(p.id(), EconomyCapacity, BusinessCapacity, Model);
        airplanes.set(airplanes.indexOf(p), updatedAirplane);

        System.out.println("Airplane edited successfully!");
    }

    public static void DeletePlane(Scanner s) {
        System.out.println("Enter id (int): \n(-1 to show all planes)");
        int id = s.nextInt();
        if (id == -1) {
            PrintAllPlanes();
            System.out.println("Enter id (int): ");
            id = s.nextInt();
        }

        Airplane p = getPlaneByID(id);
        if (p != null) {
            airplanes.remove(p);
            System.out.println("Plane deleted successfully!");
        } else {
            System.out.println("Airplane not found!");
        }
    }

    // Helper method to check user input or keep old value using a lambda
    private static <T> T getUserInputOrKeepOldValue(Scanner s, String fieldName, T oldValue) {
        System.out.println("Enter " + fieldName + ": \n(-1 to keep old value)");
        if (fieldName.equals("model")) {
            String input = s.next();
            return input.equals("-1") ? (T) oldValue : (T) input;
        } else {
            int input = s.nextInt();
            return input == -1 ? oldValue : (T) Integer.valueOf(input);
        }
    }
}
