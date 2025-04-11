package com.airplane;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
//    	seedAirplanes();

        System.out.println("---------------------------");
        // Using a lambda expression to print airplane details
        airplanes.forEach(plane -> System.out.println("Airplane ID: " + plane.id()+ "\n" + "Airplane Model: " + plane.model()));
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
    public static void runDiagnostics() {

        ExecutorService executor = Executors.newFixedThreadPool(airplanes.size());
        List<Callable<String>> diagnosticTasks = new ArrayList<>();

        for (Airplane plane : airplanes) {
            diagnosticTasks.add(() -> {
                try {
                    Thread.sleep(1000 + new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                String result = String.format("Diagnostics for %s (ID: %d) completed. Status: OK",
                                              plane.model(), plane.id());

                // Write individual diagnostic result to a file
                writeToLogFile(result);

                return result;
            });
        }

        try {
            long startTime = System.currentTimeMillis();
            List<Future<String>> results = executor.invokeAll(diagnosticTasks);
            long endTime = System.currentTimeMillis();

            System.out.println("\n----- Diagnostic Results -----");
            for (Future<String> future : results) {
                System.out.println(future.get());
            }
            System.out.printf("\nAll diagnostics completed in %.3f seconds.\n",
                              (endTime - startTime) / 1000.0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
    private static void writeToLogFile(String content) {
        Path logPath = Paths.get("logs", "diagnostics.log");

        try {
            // Ensure logs directory exists
            Files.createDirectories(logPath.getParent());

            // Append to log file using StandardOpenOption
            Files.writeString(logPath,
                              content + System.lineSeparator(),
                              StandardCharsets.UTF_8,
                              StandardOpenOption.CREATE,
                              StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.err.println("Error writing to diagnostics log: " + e.getMessage());
        }
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
    static void seedAirplanes() {
        AirplanesController.addTestAirplane(new Airplane(1, 100, 20, "Boeing 737"));
        AirplanesController.addTestAirplane(new Airplane(2, 150, 30, "Airbus A320"));
        AirplanesController.addTestAirplane(new Airplane(3, 120, 25, "Embraer E190"));
    }
    public static void addTestAirplane(Airplane airplane) {
        airplanes.add(airplane);
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
