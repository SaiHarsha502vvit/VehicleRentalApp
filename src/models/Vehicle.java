package models;

import enums.VehicleStatus;
import enums.VehicleType;

public abstract class Vehicle {

    protected String vehicleId;
    protected String modelName;
    protected VehicleType vehicleType;
    protected int pricePer24Hours;
    protected VehicleStatus status;

    public Vehicle(String vehicleId,String modelName,VehicleType type,int price){

        this.vehicleId = vehicleId;
        this.modelName = modelName;
        this.vehicleType = type;
        this.pricePer24Hours = price;
        this.status = VehicleStatus.AVAILABLE;

    }

    public String getVehicleId(){
        return vehicleId;
    }

    public String getModelName(){
        return modelName;
    }

    public VehicleType getVehicleType(){
        return vehicleType;
    }

    public int getPricePer24Hours(){
        return pricePer24Hours;
    }

    public VehicleStatus getStatus(){
        return status;
    }

    public void setStatus(VehicleStatus status){
        this.status = status;
    }

}