package services;

import java.util.HashMap;
import models.*;
import enums.*;

public class RentalShopBook {

    private HashMap<String, Vehicle> vehicles = new HashMap<>();

    private HashMap<Integer, Rental> activeRentals = new HashMap<>();

    public void loadVehicles() {

        vehicles.put("B101", new Vehicle("B101", "R15", VehicleType.BIKE, 300) {
        });
        vehicles.put("B102", new Vehicle("B102", "Duke", VehicleType.BIKE, 350) {
        });
        vehicles.put("B103", new Vehicle("B103", "Pulsar", VehicleType.BIKE, 250) {
        });

        vehicles.put("C201", new Vehicle("C201", "Swift", VehicleType.CAR, 800) {
        });
        vehicles.put("C202", new Vehicle("C202", "Creta", VehicleType.CAR, 1200) {
        });
        vehicles.put("C203", new Vehicle("C203", "Verna", VehicleType.CAR, 1000) {
        });

    }

    public void showAvailableVehicles() {

        System.out.println("\nAvailable Vehicles\n");

        for (Vehicle v : vehicles.values()) {

            if (v.getStatus() == VehicleStatus.AVAILABLE) {

                System.out.println(
                        v.getVehicleId() + "  " +
                                v.getModelName() + "  " +
                                v.getVehicleType() + "  " +
                                v.getPricePer24Hours());

            }

        }

    }

   

    public boolean rentVehicle(User user, String vehicleId) {

        Vehicle vehicle = vehicles.get(vehicleId);

        if (vehicle == null) {
            System.out.println("Vehicle not found");
            return false;
        }

        // Prevent user from renting multiple vehicles
        if (activeRentals.containsKey(user.getUserId())) {
            System.out.println("User already has an active rental");
            return false;
        }

        if (vehicle.getStatus() == VehicleStatus.RENTED) {
            System.out.println("Vehicle already rented");
            return false;
        }

        if (user.getDepositBalance() < vehicle.getPricePer24Hours()) {
            System.out.println("Deposit not sufficient");
            return false;
        }

        Rental rental = new Rental("R" + System.currentTimeMillis(), user, vehicle);

        vehicle.setStatus(VehicleStatus.RENTED);

        activeRentals.put(user.getUserId(), rental);

        return true;
    }

    public Rental getUserRental(int userId) {

        return activeRentals.get(userId);

    }

    public double returnVehicle(User user, BillingSystem billing) {

        Rental rental = activeRentals.get(user.getUserId());

        if (rental == null) {
            System.out.println("No active rental");
            return -1;
        }

        double bill = billing.calculateBill(rental);

        rental.getVehicle().setStatus(VehicleStatus.AVAILABLE);

        activeRentals.remove(user.getUserId());

        return bill;

    }

}