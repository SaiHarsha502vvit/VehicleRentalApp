package abstractclasses;

import enums.VehicalStatus;
import enums.VehicalType;

public abstract class Vehicle {

    private String vehicleId;
    private String modelName;
    private VehicalType vehicalType;
    private int pricePer24Hours;
    private VehicalStatus status;

    public String getVehicleId() {
        return vehicleId;
    }
    public String getModelName() {
        return modelName;
    }
    public VehicalType getVehicalType() {
        return vehicalType;
    }
    public int getPricePer24Hours() {
        return pricePer24Hours;
    }
    public VehicalStatus getStatus() {
        return status;
    }

    public void setStatus(VehicalStatus statusGiven){
        this.status=statusGiven;
    }
}
