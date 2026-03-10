package services;

import models.Rental;
import java.time.Duration;
import java.time.LocalDateTime;

public class BillingSystem {

    public double calculateBill(Rental rental){

        LocalDateTime start = rental.getStartTime();
        LocalDateTime now = LocalDateTime.now();

        long hours = Duration.between(start,now).toHours();

        if(hours <= 24)
            hours = 24;

        double price = rental.getVehicle().getPricePer24Hours();

        double total = price;

        if(hours > 24){

            long extra = hours - 24;

            total += extra * 100;

        }

        
        System.out.println("\n------ BILL ------");
        System.out.println("Vehicle : "+rental.getVehicle().getModelName());
        System.out.println("Base Price : "+price);
        System.out.println("Total Bill : "+total);
        System.out.println("------------------");

        return total;

    }

}