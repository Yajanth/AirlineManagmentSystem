package com.airplane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class FlightsController {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd::HH:mm:ss");
	private static ArrayList<Flight> flights = new ArrayList<>();

	public static void addNewFlight(Scanner s) {
		System.out.println("Enter plane id (int): ");
		int planeID = s.nextInt();
		Airplane plane = AirplanesController.getPlaneByID(planeID);

		System.out.println("Enter origin airport id (int): ");
		int originID = s.nextInt();
		Airport origin = AirportsController.getAirportById(originID);

		System.out.println("Enter destination airport id (int): ");
		int destinationID = s.nextInt();
		Airport destination = AirportsController.getAirportById(destinationID);

		System.out.println("Enter departure time (yyyy-MM-dd::HH:mm:ss): ");
		String dTime = s.next();
		LocalDateTime departureTime = LocalDateTime.parse(dTime, formatter);

		System.out.println("Enter arrival time (yyyy-MM-dd::HH:mm:ss): ");
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

		System.out.println("Flight added successfully!");
	}

	public static ArrayList<Flight> getAllFlights() {
		return flights;
	}

	public static void showAllFlights() {
		System.out.println("id\tAirplane\tOrigin\t\tDestination\tDeparture Time\t\tArrival Time\t\tstatus\tAvailable Economy\tAvailable Business");
		for (Flight f : flights) {
			f.print();
		}
	}

	public static void delayFlight(Scanner s) {
		System.out.println("Enter flight id (int): ");
		int id = s.nextInt();
		Flight f = getFlightById(id);
		if (f != null) {
			f.delay();
			System.out.println("Flight delayed successfully!");
		} else {
			System.out.println("Flight not found.");
		}
	}

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

	public static void cancelFlight(Scanner s) {
		System.out.println("Enter flight id (int): ");
		int id = s.nextInt();
		Flight flight = getFlightById(id);
		if (flight != null) {
			flights.remove(flight);
			System.out.println("Flight cancelled successfully!");
		} else {
			System.out.println("Flight not found.");
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
