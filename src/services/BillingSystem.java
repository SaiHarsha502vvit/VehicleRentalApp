package services;

import models.Rental;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BillingSystem {

    private static DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy  HH:mm:ss");

    public double calculateBill(Rental rental) {

        LocalDateTime start = rental.getStartTime();
        LocalDateTime end = LocalDateTime.now();

        long minutes = Duration.between(start, end).toMinutes();
        if (minutes < 1)
            minutes = 1;

        double pricePer24Hrs = rental.getVehicle().getPricePer24Hours();
        double total = minutes * pricePer24Hrs;

        long hrs = minutes / 60;
        long minutes = minutes % 60;

        System.out.println("\n  ============== BILL ==============");
        System.out.println("  Vehicle     : " + rental.getVehicle().getModelName()
                + "  [" + rental.getVehicle().getVehicleType() + "]");
        System.out.println("  Start Time  : " + start.format(FMT));
        System.out.println("  Return Time : " + end.format(FMT));
        System.out.printf("  Duration    : %d hr  %d min%n", hrs, minutes);
        System.out.printf("  Rate        : Rs.%d / 24 hrs%n", (int) pricePer24Hrs);
        System.out.printf("  Total Bill  : Rs.%.2f%n", total);
        System.out.println("  ==================================");

        return total;
    }
}
