package models;

import enums.VehicleType;

public class Bike extends Vehicle {

    public Bike(String vehicleId, String modelName, int pricePer24Hours) {
        super(vehicleId, modelName, VehicleType.BIKE, pricePer24Hours);
    }
}
