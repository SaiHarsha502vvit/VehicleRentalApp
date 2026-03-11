package models;

import enums.VehicleType;

public class Car extends Vehicle {

    public Car(String vehicleId, String modelName, int pricePer24Hours) {
        super(vehicleId, modelName, VehicleType.CAR, pricePer24Hours);
    }
}
