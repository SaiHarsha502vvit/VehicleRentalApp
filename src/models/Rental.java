package models;

import java.time.LocalDateTime;
import enums.RentalStatus;

public class Rental {

    private String rentalId;
    private User user;
    private Vehicle vehicle;
    private LocalDateTime startTime;
    private LocalDateTime actualReturnTime;
    private RentalStatus status;

   
    Rental(String rentalId, User user, Vehicle vehicle)
    {
        this.rentalId = rentalId;
        this.user = user;
        this.vehicle = vehicle;

       
        this.startTime = LocalDateTime.now();

      
        this.status = RentalStatus.ACTIVE;
    }

    
    LocalDateTime getStartTime()
    {
        return startTime;
    }

    
    Vehicle getVehicle()
    {
        return vehicle;
    }

    
    RentalStatus getStatus()
    {
        return status;
    }

    
    void closeRental()
    {
        actualReturnTime = LocalDateTime.now();
        status = RentalStatus.CLOSED;
    }
}
