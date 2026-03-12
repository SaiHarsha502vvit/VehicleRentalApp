package services;

import java.util.HashMap;
import models.*;
import enums.*;

public class RentalShopBook {

    public static int count = 0;

    private HashMap<String, Vehicle> vehicles = new HashMap<>();
    private HashMap<Integer, Rental> activeRentals = new HashMap<>();

    public void loadVehicles() {
        vehicles.put("B101", new Bike("B101", "R15", 300));
        vehicles.put("B102", new Bike("B102", "Duke", 350));
        vehicles.put("B103", new Bike("B103", "Pulsar", 250));

        vehicles.put("C201", new Car("C201", "Swift", 800));
        vehicles.put("C202", new Car("C202", "Creta", 1200));
        vehicles.put("C203", new Car("C203", "Verna", 1000));
    }

    private Vehicle findVehicle(String input) {
        if (vehicles.containsKey(input))
            return vehicles.get(input);

        for (Vehicle v : vehicles.values()) {
            if (v.getModelName().equalsIgnoreCase(input))
                return v;
        }
        return null;
    }

    public boolean hasAvailableVehicles() {
        for (Vehicle v : vehicles.values()) {
            if (v.getStatus() == VehicleStatus.AVAILABLE)
                return true;
        }
        return false;
    }

    public void showAvailableVehicles() {
        System.out.println("\n  ----- Available Vehicles -----");
        System.out.printf("  %-6s %-10s %-6s %s%n", "ID", "Name", "Type", "Rate");
        System.out.println("  ------------------------------");
        boolean anyAvailable = false;
        for (Vehicle v : vehicles.values()) {
            if (v.getStatus() == VehicleStatus.AVAILABLE) {
                System.out.printf("  %-6s %-10s %-6s Rs.%d / 24 hrs%n",
                        v.getVehicleId(), v.getModelName(),
                        v.getVehicleType(), v.getPricePer24Hours());
                anyAvailable = true;
            }
        }
        if (!anyAvailable) {
            System.out.println("  Sorry, we are out of vehicles right now!");
            System.out.println("  Please check back later.");
        }
        System.out.println("  ------------------------------");
    }

    public boolean hasActiveRental(int userId) {
        return activeRentals.containsKey(userId);
    }

    public double getRequiredDeposit(String input) {
        Vehicle v = findVehicle(input);
        return (v == null) ? -1 : v.getPricePer24Hours();
    }

    public boolean rentVehicle(User user, String input) {

        Vehicle vehicle = findVehicle(input);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return false;
        }
        if (activeRentals.containsKey(user.getUserId())) {
            System.out.println("You already have an active rental.");
            return false;
        }
        if (vehicle.getStatus() == VehicleStatus.RENTED) {
            System.out.println("Vehicle is already rented.");
            return false;
        }
        if (user.getDepositBalance() < vehicle.getPricePer24Hours()) {
            System.out.printf("Insufficient balance. Required: Rs.%d  |  Your balance: Rs.%.2f%n",
                    vehicle.getPricePer24Hours(), user.getDepositBalance());
            return false;
        }

        Rental rental = new Rental("R" + (++count), user, vehicle);
        vehicle.setStatus(VehicleStatus.RENTED);
        activeRentals.put(user.getUserId(), rental);
        return true;
    }

    public double returnVehicle(User user, BillingSystem billing) {

        Rental rental = activeRentals.get(user.getUserId());
        if (rental == null) {
            System.out.println("No active rental found.");
            return -1;
        }

        double bill = billing.calculateBill(rental);
        rental.getVehicle().setStatus(VehicleStatus.AVAILABLE);
        rental.closeRental();
        activeRentals.remove(user.getUserId());
        return bill;
    }

    public Rental getUserRental(int userId) {
        return activeRentals.get(userId);
    }
}
