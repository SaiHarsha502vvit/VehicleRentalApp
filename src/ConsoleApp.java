
import services.*;
import models.User;

import java.util.Scanner;

public class ConsoleApp {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        UserBook userBook = new UserBook();
        RentalShopBook shop = new RentalShopBook();
        BillingSystem billing = new BillingSystem();

        shop.loadVehicles();

        while (true) {

            System.out.println("\n---- VEHICLE RENTAL APP ----");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                doSignup(userBook);

            } else if (choice == 2) {

                User user = doLogin(userBook);

                if (user != null) {
                    userMenu(user, shop, billing);
                }

            } else if (choice == 3) {
                System.out.println("Thank you! Goodbye!");
                break;

            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    static void userMenu(User user, RentalShopBook shop, BillingSystem billing) {

        while (true) {

            System.out.println("\n---- Welcome " + user.getName() + " ----");
            System.out.println("Balance: Rs." + user.getDepositBalance());
            System.out.println("1. Add Deposit");
            System.out.println("2. View Available Vehicles");
            System.out.println("3. Rent a Vehicle");
            System.out.println("4. Return Vehicle");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                doAddDeposit(user);

            } else if (choice == 2) {
                shop.showAvailableVehicles();

            } else if (choice == 3) {
                doRent(user, shop);

            } else if (choice == 4) {
                doReturn(user, shop, billing);

            } else if (choice == 5) {
                System.out.println("Logged out successfully");
                break;

            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    static void doSignup(UserBook userBook) {

        System.out.println("\nSign Up");

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Mobile: ");
        long mobile = sc.nextLong();
        sc.nextLine();

        System.out.print("License No: ");
        String license = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        boolean created = userBook.createAccount(name, mobile, license, pass);

        if (created) {
            System.out.println("Account created successfully! Please login.");
        }
    }

    static User doLogin(UserBook userBook) {

        System.out.println("\n-- Login --");

        System.out.print("Enter Mobile: ");
        long mobile = sc.nextLong();
        sc.nextLine();

        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        User user = userBook.login(mobile, pass);

        if (user != null) {
            System.out.println("Login successful! Welcome " + user.getName());
            return user;
        }

        System.out.println("Wrong credentials. Looks like you don't have an account.");
        System.out.println("Please sign up first!");
        return null;
    }

    static void doAddDeposit(User user) {

        System.out.println("Minimum deposit amount is Rs." + UserBook.MINIMUM_TOPUP);
        System.out.print("Enter amount: Rs.");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount < UserBook.MINIMUM_TOPUP) {
            System.out.println("Amount is less than minimum. Please enter at least Rs." + UserBook.MINIMUM_TOPUP);
            return;
        }

        user.addDeposit(amount);
        System.out.println("Deposited Rs." + amount);
        System.out.println("New Balance: Rs." + user.getDepositBalance());
    }

    static void doRent(User user, RentalShopBook shop) {

        if (shop.hasActiveRental(user.getUserId())) {
            System.out.println("You already have a vehicle rented. Please return it first.");
            return;
        }

        shop.showAvailableVehicles();

        System.out.print("Enter Vehicle Name or ID: ");
        String input = sc.nextLine();

        double required = shop.getRequiredDeposit(input);

        if (required < 0) {
            System.out.println("Vehicle not found. Please check the name or ID.");
            return;
        }

        if (user.getDepositBalance() < required) {

            double shortfall = required - user.getDepositBalance();

            System.out.println("Your balance is not enough!");
            System.out.println("Required: Rs." + required);
            System.out.println("Your Balance: Rs." + user.getDepositBalance());
            System.out.println("You need Rs." + shortfall + " more.");
            System.out.print("Do you want to add Rs." + shortfall + " now? (yes/no): ");

            String ans = sc.nextLine();

            if (ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {

                System.out.print("Enter amount to add (minimum Rs." + shortfall + "): Rs.");
                double add = sc.nextDouble();
                sc.nextLine();

                if (add < shortfall) {
                    System.out.println("Amount is not enough. Rental cancelled.");
                    return;
                }

                user.addDeposit(add);
                System.out.println("Balance updated. New Balance: Rs." + user.getDepositBalance());

            } else {
                System.out.println("Rental cancelled.");
                return;
            }
        }

        boolean rented = shop.rentVehicle(user, input);

        if (rented) {
            System.out.println("\n*** Vehicle Allocated! HAPPY JOURNEY! Bandi Jagratha mawa.. ***");
            System.out.println("Current Balance: Rs." + user.getDepositBalance());
        }
    }

    static void doReturn(User user, RentalShopBook shop, BillingSystem billing) {

        if (!shop.hasActiveRental(user.getUserId())) {
            System.out.println("You don't have any active rental.");
            return;
        }

        double bill = shop.returnVehicle(user, billing);

        if (bill < 0) {
            return;
        }

        user.deductDeposit(bill);

        System.out.println("Bill Amount: Rs." + bill);
        System.out.println("Remaining Balance: Rs." + user.getDepositBalance());

        if (user.getDepositBalance() < 0) {
            System.out.println("Your bill was more than your deposit!");
            System.out.println("You still owe: Rs." + (-user.getDepositBalance()));
            System.out.println("Please pay the remaining amount to continue.");

            while (user.getDepositBalance() < 0) {
                System.out.println("Amount due: Rs." + (-user.getDepositBalance()));
                System.out.print("Enter payment: Rs.");
                double payment = sc.nextDouble();
                sc.nextLine();

                if (payment <= 0) {
                    System.out.println("Invalid amount. Please enter a valid amount.");
                    continue;
                }

                user.addDeposit(payment);

                if (user.getDepositBalance() < 0) {
                    System.out.println("Still due: Rs." + (-user.getDepositBalance()));
                } else {
                    System.out.println("Due cleared! Balance: Rs." + user.getDepositBalance());
                }
            }
        }

        System.out.println("Vehicle returned successfully. You can rent another vehicle!");
    }

}
