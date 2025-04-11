package com.airplane;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

public class FlightsController {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");
	private static ArrayList<Flight> flights = new ArrayList<>();

 public static void addNewFlight(Scanner s, Locale locale) {
        Properties messages = loadLocalizedMessages(locale);

        System.out.println(messages.getProperty("flight.origin") + ": ");
        int planeID = s.nextInt();
        Airplane plane = AirplanesController.getPlaneByID(planeID);

        System.out.println(messages.getProperty("flight.destination") + ": ");
        int originID = s.nextInt();
        Airport origin = AirportsController.getAirportById(originID);

        System.out.println(messages.getProperty("flight.destination") + ": ");
        int destinationID = s.nextInt();
        Airport destination = AirportsController.getAirportById(destinationID);

        System.out.println(messages.getProperty("flight.departureTime") + " (yyyy-MM-dd::HH:mm:ss): ");
        String dTime = s.next();
        LocalDateTime departureTime = LocalDateTime.parse(dTime, formatter);

        System.out.println(messages.getProperty("flight.arrivalTime") + " (yyyy-MM-dd::HH:mm:ss): ");
        String aTime = s.next();
        LocalDateTime arrivalTime = LocalDateTime.parse(aTime, formatter);

        Flight flight = new Flight();
        flight.setID(flights.size());
        flight.setAirplane(plane);
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flights.add(flight);

        System.out.println(messages.getProperty("flight.added"));
    }

	public static ArrayList<Flight> getAllFlights() {
		return flights;
	}

    public static void showAllFlights(Locale locale) {
        Properties messages = loadLocalizedMessages(locale);

        System.out.println("id\tAirplane\tOrigin\t\tDestination\tDeparture Time\t\tArrival Time\t\tstatus\tAvailable Economy\tAvailable Business");
        for (Flight f : flights) {
            f.print(locale);
        }
    }

//	public static void delayFlight(Scanner s) {
//		System.out.println("Enter flight id (int): ");
//		int id = s.nextInt();
//		Flight f = getFlightById(id);
//		if (f != null) {
//			f.getStatus()
//			System.out.println("Flight delayed successfully!");
//		} else {
//			System.out.println("Flight not found.");
//		}
//	}
	

	public static void bookFlight(Scanner s) {
		System.out.println("Enter flight id (int): ");
		int id = s.nextInt();
		Flight flight = getFlightById(id);

		if (flight == null) {
			System.out.println("Flight not found.");
			return;
		}

		System.out.println("Enter passenger id (int): ");
		int passengerID = s.nextInt();
		Passenger passenger = PassengerController.getPassengerByID(passengerID);

		System.out.println("1. Economy seat\n2. Business seat");
		int seatType = s.nextInt();
		System.out.println("Enter number of seats: ");
		int num = s.nextInt();

		if (seatType == 1) {
			flight.setBookedEconomy(flight.getBookedEconomy() + num);
		} else {
			flight.setBookedBusiness(flight.getBookedBusiness() + num);
		}

		for (int i = 0; i < flight.getPassengers().length; i++) {
			if (flight.getPassengers()[i] == null) {
				flight.getPassengers()[i] = passenger;
				break;
			}
		}

		System.out.println("Booked successfully!");
	}

    public static Flight getFlightById(int id) {
        for (Flight f : flights) {
            if (f.getID() == id) return f;
        }
        return null;
    }

	public static void setFlightStuff(Scanner s) {
		System.out.println("Enter flight id (int): ");
		int id = s.nextInt();
		Flight flight = getFlightById(id);

		if (flight == null) {
			System.out.println("Flight not found.");
			return;
		}

		Employee[] employees = new Employee[10];
		for (int i = 0; i < 10; i++) {
			System.out.println("Enter staff id " + (i + 1) + "/10 (or -1 to stop):");
			int eid = s.nextInt();
			if (eid == -1) break;
			employees[i] = EmployeesController.getEmployeeByID(eid);
		}

		flight.setStuff(employees);
		System.out.println("Flight staff set successfully!");
	}

    public static void cancelFlight(Scanner s, Locale locale) {
        Properties messages = loadLocalizedMessages(locale);

        System.out.println(messages.getProperty("flight.cancelPrompt"));
        int id = s.nextInt();
        Flight flight = getFlightById(id);
        if (flight != null) {
            flights.remove(flight);
            System.out.println(messages.getProperty("flight.cancelSuccess"));
        } else {
            System.out.println(messages.getProperty("flight.notFound"));
        }
    }
	public static void printFlightStuff(Scanner s) {
		System.out.println("Enter flight id (int): ");
		int id = s.nextInt();
		Flight flight = getFlightById(id);

		if (flight == null) {
			System.out.println("Flight not found.");
			return;
		}

		System.out.println("id\tFirst Name\tLast Name\tEmail\tTel\tPosition");
		for (Employee e : flight.getStuff()) {
			if (e != null) {
				System.out.printf("%d\t%s\t\t%s\t\t%s\t%s\t%s\n",
						e.getId(), e.getFirstName(), e.getLastName(),
						e.getEmail(), e.getTel(), e.getPosition());
			}
		}
	}
	private static Properties loadLocalizedMessages(Locale locale) {
	    if (locale == null) {
	        System.err.println("Locale is null, using default English messages.");
	        locale = Locale.ENGLISH;  // Default to English if locale is null
	    }

	    String baseName = "MessagesBundle_" + locale.getLanguage() + ".properties";
	    Path path = Paths.get("i18n", baseName);
	    Properties props = new Properties();

	    // Debugging: Check the resolved path for the localization file
	    System.out.println("Looking for localization file: " + path);

	    try (InputStream in = Files.newInputStream(path)) {
	        props.load(in);
	        System.out.println("Localization file loaded successfully.");
	    } catch (IOException e) {
	        System.err.println("Localization file not found at: " + path);
	        // Fallback to English if file is not found
	        return loadDefaultMessages();
	    }

	    return props;
	}

	private static Properties loadDefaultMessages() {
	    Properties props = new Properties();
	    try (InputStream defaultIn = Files.newInputStream(Paths.get("i18n", "MessagesBundle_en.properties"))) {
	        props.load(defaultIn);
	        System.out.println("Fallback to English loaded successfully.");
	    } catch (IOException ioException) {
	        System.err.println("Fallback localization file also missing.");
	    }
	    return props;
	}


	public static void printFlightPassengers(Scanner s) {
		System.out.println("Enter flight id (int): ");
		int id = s.nextInt();
		Flight flight = getFlightById(id);

		if (flight == null) {
			System.out.println("Flight not found.");
			return;
		}

		System.out.println("id\tFirst Name\tLast Name\tEmail\tTel");
		for (Passenger p : flight.getPassengers()) {
			if (p != null) {
				System.out.printf("%d\t%s\t\t%s\t\t%s\t%s\n",
						p.getId(), p.getFirstName(), p.getLastName(),
						p.getEmail(), p.getTel());
			}
		}
	}
}
