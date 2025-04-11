package com.airplane;

import java.io.InputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner s = new Scanner(System.in);
        // Prompt the user for language selection
        System.out.println("Select language:");
        System.out.println("1. English");
        System.out.println("2. Français");
        System.out.println("3. Deutsch");
        System.out.print("Choose an option (1-3): ");
        
        int languageChoice = s.nextInt();
        Locale locale;

        switch (languageChoice) {
            case 1:
                locale = Locale.ENGLISH;
                break;
            case 2:
                locale = Locale.FRENCH;
                break;
            case 3:
                locale = Locale.GERMANY;
                break;
            default:
                locale = Locale.ENGLISH; // default to English if invalid option is selected
        }

        // Load localized messages
        Properties messages = loadLocalizedMessages(locale);

        // Define actions using lambdas or method references
        Map<Integer, Runnable> actions = new HashMap<>();
        // Load existing passenger data
        PassengerController.loadPassengerData();

        actions.put(1, () -> PassengerController.addNewPassenger(s));
        actions.put(2, () -> PassengerController.findPassengerByName(s));
        actions.put(3, PassengerController::printAllPassengers);
        actions.put(4, () -> PassengerController.editPassenger(s));
        actions.put(5, () -> PassengerController.passengersSortByFName());


        actions.put(6, () -> EmployeesController.addNewEmployee(s));
        actions.put(7, () -> EmployeesController.findEmployeeByName(s));
        actions.put(8, EmployeesController::printAllEmployees);
        actions.put(9, () -> EmployeesController.editEmployee(s));
        actions.put(10, () -> EmployeesController.deleteEmployee(s));
 
        actions.put(11, () -> AirplanesController.AddNewAirplane(s));
        actions.put(12, AirplanesController::PrintAllPlanes);
        actions.put(13, () -> AirplanesController.EditAirplane(s));
        actions.put(14, () -> AirplanesController.DeletePlane(s));
        actions.put(27, AirplanesController::runDiagnostics);


        actions.put(15, () -> AirportsController.addNewAirport(s));
        actions.put(16, AirportsController::printAllAirports);
        actions.put(17, () -> AirportsController.editAirport(s));
        actions.put(18, () -> AirportsController.deleteAirport(s));

        actions.put(19, () -> FlightsController.addNewFlight(s, null));
        actions.put(20, () -> FlightsController.showAllFlights(locale));
        actions.put(21, () -> FlightsController.bookFlight(s));
        actions.put(22, () -> FlightsController.setFlightStuff(s));
        actions.put(23, () -> FlightsController.cancelFlight(s, null));
        actions.put(24, () -> FlightsController.printFlightStuff(s));
        actions.put(25, () -> FlightsController.printFlightPassengers(s));

        int choice = 0;
        do {
            printMenu(messages); // Pass messages to printMenu method

            while (!s.hasNextInt()) {
                System.out.print("Please enter a valid number (1-26): ");
                s.next(); // consume invalid input
            }

            choice = s.nextInt();

            if (choice == 31) {
                System.out.println(messages.getProperty("menu.quit"));
                break;
            }

            Runnable action = actions.get(choice);
            if (action != null) {
                action.run();
            } else {
                System.out.println(messages.getProperty("menu.invalidOption"));
            }
            try {
                Thread.sleep(3000); // 3000 milliseconds = 3 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);
    }

    private static void printMenu(Properties messages) {
        System.out.println("\n========== " + messages.getProperty("menu.title") + " ==========");

        System.out.println(messages.getProperty("menu.passengers") + ":");
        System.out.println("  01. " + messages.getProperty("menu.addPassenger"));
        System.out.println("  02. " + messages.getProperty("menu.getPassenger"));
        System.out.println("  03. " + messages.getProperty("menu.printAllPassengers"));
        System.out.println("  04. " + messages.getProperty("menu.editPassenger"));
        System.out.println("  05. " + messages.getProperty("menu.passengersSortByFName") + " \n");

        System.out.println(messages.getProperty("menu.employees") + ":");
        System.out.println("  06. " + messages.getProperty("menu.addEmployee"));
        System.out.println("  07. " + messages.getProperty("menu.getEmployee"));
        System.out.println("  08. " + messages.getProperty("menu.printAllEmployees"));
        System.out.println("  09. " + messages.getProperty("menu.editEmployee"));
        System.out.println("  10. " + messages.getProperty("menu.deleteEmployee") + " \n");

        System.out.println(messages.getProperty("menu.airplanes") + ":");
        System.out.println("  11. " + messages.getProperty("menu.addAirplane"));
        System.out.println("  12. " + messages.getProperty("menu.printAllPlanes"));
        System.out.println("  13. " + messages.getProperty("menu.editAirplane"));
        System.out.println("  14. " + messages.getProperty("menu.deleteAirplane") + " \\n");

        System.out.println(messages.getProperty("menu.airports") + ":");
        System.out.println("  15. " + messages.getProperty("menu.addAirport"));
        System.out.println("  16. " + messages.getProperty("menu.printAllAirports"));
        System.out.println("  17. " + messages.getProperty("menu.editAirport"));
        System.out.println("  18. " + messages.getProperty("menu.deleteAirport"));

        System.out.println(messages.getProperty("menu.flights") + ":");
        System.out.println("  19. " + messages.getProperty("menu.createFlight"));
        System.out.println("  20. " + messages.getProperty("menu.showAllFlights"));
        System.out.println("  21. " + messages.getProperty("menu.bookFlight"));
        System.out.println("  22. " + messages.getProperty("menu.setFlightStaff"));
        System.out.println("  23. " + messages.getProperty("menu.cancelFlight"));
        System.out.println("  24. " + messages.getProperty("menu.showFlightStaff"));
        System.out.println("  25. " + messages.getProperty("menu.showFlightPassengers"));

        System.out.println("26. " + messages.getProperty("menu.quit"));

        System.out.println("===============================================");
        System.out.print(messages.getProperty("menu.selectOption"));
    }

    private static Properties loadLocalizedMessages(Locale locale) {
        String baseName = "MessagesBundle_" + locale.getLanguage() + ".properties";
        Properties props = new Properties();

        // Use FileInputStream to load the resource file from the i18n folder at the project root
        try (InputStream inputStream = new FileInputStream(new File("i18n/" + baseName))) {
            // Load properties from the file input stream
            props.load(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Resource file not found: " + baseName);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading localization file.");
            e.printStackTrace();
        }

        return props;
    }
}