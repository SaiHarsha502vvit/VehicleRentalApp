package app;

import services.*;
import models.User;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        UserBook userBook = new UserBook();
        RentalShopBook shop = new RentalShopBook();
        BillingSystem billing = new BillingSystem();

        shop.loadVehicles();

        while (true) {

            System.out.println("\n1 Signup");
            System.out.println("2 Login");
            System.out.println("3 Exit");

            int choice = sc.nextInt();

            if (choice == 1) {

                System.out.println("Name:");
                String name = sc.next();

                System.out.println("Mobile:");
                long mobile = sc.nextLong();

                System.out.println("License:");
                String license = sc.next();

                System.out.println("Password:");
                String pass = sc.next();

                if (userBook.createAccount(name, mobile, license, pass)) {
                    System.out.println("Account created successfully");
                }

            }

            else if (choice == 2) {

                /*
                 * TODO: Password retry limit
                 * User gets only 3 attempts for login.
                 */

                User user = null;

                for (int i = 1; i <= 3; i++) {

                    System.out.println("Mobile:");
                    long mobile = sc.nextLong();

                    System.out.println("Password:");
                    String pass = sc.next();

                    user = userBook.login(mobile, pass);

                    if (user != null)
                        break;

                    System.out.println("Invalid credentials attempt " + i);
                }

                if (user == null) {
                    System.out.println("Login failed");
                    continue;
                }

                System.out.println("Login Success");

                System.out.println("Deposit Amount:");
                double dep = sc.nextDouble();

                user.addDeposit(dep);

                shop.showAvailableVehicles();

                System.out.println("Enter Vehicle ID:");
                String vid = sc.next();

                boolean rented = shop.rentVehicle(user, vid);

                if (!rented)
                    continue;

                System.out.println("Vehicle rented");

                System.out.println("Press any key to return vehicle");
                sc.next();

                double bill = shop.returnVehicle(user, billing);

                user.deductDeposit(bill);

            }

            else
                break;

        }

    }

}