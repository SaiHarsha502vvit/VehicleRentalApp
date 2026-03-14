package services;

import models.Rental;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static utils.ConsoleColors.*;

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
        long myminutes = minutes % 60;

        System.out.println(title("\n  ============== BILL =============="));
        System.out.println(info("  Vehicle     : " + rental.getVehicle().getModelName()
                + "  [" + rental.getVehicle().getVehicleType() + "]"));
        System.out.println(info("  Start Time  : " + start.format(FMT)));
        System.out.println(info("  Return Time : " + end.format(FMT)));
        System.out.printf("%s%n", info(String.format("  Duration    : %d hr  %d min", hrs, myminutes)));
        System.out.printf("%s%n", info(String.format("  Rate        : Rs.%d / 24 hrs", (int) pricePer24Hrs)));
        System.out.printf("%s%n", success(String.format("  Total Bill  : Rs.%.2f", total)));
        System.out.println(title("  =================================="));

        return total;
    }
}
