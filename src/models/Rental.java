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

    public Rental(String rentalId, User user, Vehicle vehicle) {
        this.rentalId = rentalId;
        this.user = user;
        this.vehicle = vehicle;

        this.startTime = LocalDateTime.now();

        this.status = RentalStatus.ACTIVE;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void closeRental() {
        actualReturnTime = LocalDateTime.now();
        status = RentalStatus.CLOSED;
    }
}
