package com.airplane;

import java.util.ArrayList;
import java.util.Scanner;

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
        Airplane airplane = new Airplane(id,EconomyCapacity,BusinessCapacity,model );
       
        
        // Add airplane to the in-memory list
        airplanes.add(airplane);
        System.out.println("Airplane added successfully!");
    }

    public static void PrintAllPlanes() {
        System.out.println("---------------------------");
        for (Airplane plane : airplanes) {
            plane.print();
        }
        System.out.println("---------------------------");
    }

    public static Airplane getPlaneByID(int id) {
        for (Airplane a : airplanes) {
            if (a.id() == id) {
                return a;
            }
        }
        return null;  // Return null if not found
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

        System.out.println("Enter economy capacity (int): \n(-1 to keep old value)");
        int EconomyCapacity = s.nextInt();
        if (EconomyCapacity == -1) {
            EconomyCapacity = p.economyCapacity();
        }

        System.out.println("Enter business capacity (int): \n(-1 to keep old value)");
        int BusinessCapacity = s.nextInt();
        if (BusinessCapacity == -1) {
            BusinessCapacity = p.businessCapacity();
        }

        System.out.println("Enter model: \n(-1 to keep old value)");
        String Model = s.next();
        if (Model.equals("-1")) {
            Model = p.model();
        }

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
}
