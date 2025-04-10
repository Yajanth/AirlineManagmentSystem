package com.airplane;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner s = new Scanner(System.in);

        // Define actions using lambdas or method references
        Map<Integer, Runnable> actions = new HashMap<>();

        actions.put(1, () -> PassengerController.addNewPassenger(s));
        actions.put(2, () -> PassengerController.findPassengerByName(s));
        actions.put(3, PassengerController::printAllPassengers);
        actions.put(4, () -> PassengerController.editPassenger(s));
        actions.put(5, () -> PassengerController.deletePassenger(s));

        actions.put(6, () -> EmployeesController.AddNewEMployee(s));
        actions.put(7, () -> EmployeesController.findEmployeeByName(s));
        actions.put(8, EmployeesController::printAllEmployees);
        actions.put(9, () -> EmployeesController.editEmployee(s));
        actions.put(10, () -> EmployeesController.deleteEmployee(s));

        actions.put(11, () -> AirplanesController.AddNewAirplane(s));
        actions.put(12, AirplanesController::PrintAllPlanes);
        actions.put(13, () -> AirplanesController.EditAirplane(s));
        actions.put(14, () -> AirplanesController.DeletePlane(s));

        actions.put(15, () -> AirportsController.addNewAirport(s));
        actions.put(16, AirportsController::printAllAirports);
        actions.put(17, () -> AirportsController.editAirport(s));
        actions.put(18, () -> AirportsController.deleteAirport(s));

        actions.put(19, () -> FlightsController.addNewFlight(s));
        actions.put(20, FlightsController::showAllFlights);
        actions.put(21, () -> FlightsController.delayFlight(s));
        actions.put(22, () -> FlightsController.bookFlight(s));
        actions.put(23, () -> FlightsController.setFlightStuff(s));
        actions.put(24, () -> FlightsController.cancelFlight(s));
        actions.put(25, () -> FlightsController.printFlightStuff(s));
        actions.put(26, () -> FlightsController.printFlightPassengers(s));

        int choice = 0;
        do {
            printMenu();

            while (!s.hasNextInt()) {
                System.out.print("Please enter a valid number (1-27): ");
                s.next(); // consume invalid input
            }

            choice = s.nextInt();

            if (choice == 27) {
                System.out.println("Exiting... Thank you for using Airline Management System!");
                break;
            }

            Runnable action = actions.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println("Invalid option. Please try again.");
            }

        } while (true);
    }

    private static void printMenu() {
        System.out.println("\n========== Airline Management System ==========");

        System.out.println("Passengers:");
        System.out.println("  01. Add New Passenger");
        System.out.println("  02. Get Passenger by Name");
        System.out.println("  03. Print All Passengers");
        System.out.println("  04. Edit Passenger");
        System.out.println("  05. Delete Passenger \n");

        System.out.println("Employees:");
        System.out.println("  06. Add New Employee");
        System.out.println("  07. Get Employee by Name");
        System.out.println("  08. Print All Employees");
        System.out.println("  09. Edit Employee");
        System.out.println("  10. Fire Employee \n");

        System.out.println("Airplanes:");
        System.out.println("  11. Add New Plane");
        System.out.println("  12. Print All Planes");
        System.out.println("  13. Edit Plane");
        System.out.println("  14. Delete Plane \\n");

        System.out.println("Airports:");
        System.out.println("  15. Add New Airport");
        System.out.println("  16. Print All Airports");
        System.out.println("  17. Edit Airport");
        System.out.println("  18. Delete Airport");

        System.out.println("Flights:");
        System.out.println("  19. Create New Flight");
        System.out.println("  20. Show All Flights");
        System.out.println("  21. Delay Flight");
        System.out.println("  22. Book Flight");
        System.out.println("  23. Set Flight Staff");
        System.out.println("  24. Cancel Flight");
        System.out.println("  25. Show Flight Staff");
        System.out.println("  26. Show Flight Passengers");

        System.out.println("27. Quit");

        System.out.println("===============================================");
        System.out.print("Select an option (1-27): ");
    }
}
