
import services.*;
import models.User;

import java.util.Scanner;

import static utils.ConsoleColors.*;

public class ConsoleApp {

    static Scanner sc = new Scanner(System.in);
    static BillingSystem billing = new BillingSystem();
    static UserBook userBook = new UserBook();
    static RentalShopBook shop = new RentalShopBook();
    static {
        shop.loadVehicles();
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println(title("\n---- VEHICLE RENTAL APP ----"));
            System.out.println(menu("1. Sign Up"));
            System.out.println(menu("2. Login"));
            System.out.println(menu("3. Exit"));
            System.out.print(prompt("Enter choice: "));

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                doSignup(userBook);

            } else if (choice == 2) {

                User user = doLogin(userBook);

                if (user != null) {
                    userMenu(user, shop, billing);
                } else {
                    System.out.println(error("Sorry user does not exist..."));
                }

            } else if (choice == 3) {
                System.out.println(success("Thank you! Goodbye!"));
                break;

            } else {
                System.out.println(error("Invalid choice"));
            }
        }
    }

    static void userMenu(User user, RentalShopBook shop, BillingSystem billing) {

        while (true) {

            System.out.println(title("\n---- Welcome " + user.getName() + " ----"));
            System.out.println(info("Balance: Rs." + user.getDepositBalance()));
            System.out.println(menu("1. Add Deposit"));
            System.out.println(menu("2. View Available Vehicles"));
            System.out.println(menu("3. Rent a Vehicle"));
            System.out.println(menu("4. Return Vehicle"));
            System.out.println(menu("5. Logout"));
            System.out.print(prompt("Enter choice: "));

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
                System.out.println(success("Logged out successfully"));
                break;

            } else {
                System.out.println(error("Invalid choice"));
            }
        }
    }

    static void doSignup(UserBook userBook) {

        System.out.println(title("\nSign Up"));

        System.out.print(prompt("Name: "));
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(error("Name cannot be empty. Please try signup again."));
            return;
        }

        System.out.print(prompt("Mobile: "));
        String mobileInput = sc.nextLine().trim();

        if (mobileInput.isEmpty()) {
            System.out.println(error("Mobile number cannot be empty. Please try signup again."));
            return;
        }

        if (!mobileInput.matches("\\d+")) {
            System.out.println(error("Mobile number must contain digits only."));
            return;
        }

        long mobile = Long.parseLong(mobileInput);

        System.out.print(prompt("License No: "));
        String license = sc.nextLine().trim();

        if (license.isEmpty()) {
            System.out.println(error("License number cannot be empty. Please try signup again."));
            return;
        }

        System.out.print(prompt("Password: "));
        String pass = sc.nextLine();

        if (pass.trim().isEmpty()) {
            System.out.println(error("Password cannot be empty. Please try signup again."));
            return;
        }

        boolean created = userBook.createAccount(name, mobile, license, pass);

        if (created) {
            System.out.println(success("Account created successfully! Please login."));
        } else {
            System.out.println(warning("Please provide all details correctly."));
        }
    }

    static User doLogin(UserBook userBook) {

        System.out.println(title("\n-- Login --"));

        System.out.print(prompt("Enter Mobile: "));
        String mobileInput = sc.nextLine().trim();

        if (mobileInput.isEmpty()) {
            System.out.println(error("Mobile number cannot be empty."));
            return null;
        }

        if (!mobileInput.matches("\\d+")) {
            System.out.println(error("Invalid mobile number. Please enter digits only."));
            return null;
        }

        long mobile = Long.parseLong(mobileInput);

        System.out.print(prompt("Enter Password: "));
        String pass = sc.nextLine();

        if (pass.trim().isEmpty()) {
            System.out.println(error("Password cannot be empty."));
            return null;
        }

        Object user = userBook.login(mobile, pass);

        if (user == null) {
            System.out.println(error("Wrong credentials. Looks like you don't have an account."));
            System.out.println(warning("Please sign up first!"));
            return null;
        }

        if (user.equals("-1")) {
            return null;
        }

        User user2 = (User) user;

        if (user != null) {
            System.out.println(success("Login successful! Welcome " + user2.getName()));
            return user2;
        }

        return null;
    }

    static void doAddDeposit(User user) {

        System.out.println(info("Minimum deposit amount is Rs." + UserBook.MINIMUM_TOPUP));
        System.out.print(prompt("Enter amount: Rs."));
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount < UserBook.MINIMUM_TOPUP) {
            System.out
                    .println(error("Amount is less than minimum. Please enter at least Rs." + UserBook.MINIMUM_TOPUP));
            return;
        }

        user.addDeposit(amount);
        System.out.println(success("Deposited Rs." + amount));
        System.out.println(info("New Balance: Rs." + user.getDepositBalance()));
    }

    static void doRent(User user, RentalShopBook shop) {

        if (shop.hasActiveRental(user.getUserId())) {
            System.out.println(warning("You already have a vehicle rented. Please return it first."));
            return;
        }

        if (!shop.hasAvailableVehicles()) {
            System.out.println(warning("\n  Sorry, we are currently out of vehicles!"));
            System.out.println(warning("  All our vehicles are rented out at the moment."));
            System.out.println(warning("  Please try again later."));
            return;
        }

        shop.showAvailableVehicles();

        System.out.print(prompt("Enter Vehicle Name or ID: "));
        String input = sc.nextLine();

        double required = shop.getRequiredDeposit(input);

        if (required < 0) {
            System.out.println(error("Vehicle not found. Please check the name or ID."));
            return;
        }

        if (user.getDepositBalance() < required) {

            double shortfall = required - user.getDepositBalance();

            System.out.println(warning("Your balance is not enough!"));
            System.out.println(info("Required: Rs." + required));
            System.out.println(info("Your Balance: Rs." + user.getDepositBalance()));
            System.out.println(warning("You need Rs." + shortfall + " more."));
            System.out.print(prompt("Do you want to add Rs." + shortfall + " now? (yes/no): "));

            String ans = sc.nextLine();

            if (ans.equalsIgnoreCase("yes") || ans.equalsIgnoreCase("y")) {

                System.out.print(prompt("Enter amount to add (minimum Rs." + shortfall + "): Rs."));
                double add = sc.nextDouble();
                sc.nextLine();

                if (add < shortfall) {
                    System.out.println(error("Amount is not enough. Rental cancelled."));
                    return;
                }

                user.addDeposit(add);
                System.out.println(success("Balance updated. New Balance: Rs." + user.getDepositBalance()));

            } else {
                System.out.println(warning("Rental cancelled."));
                return;
            }
        }

        boolean rented = shop.rentVehicle(user, input);

        if (rented) {
            System.out.println(success("\n*** Vehicle Allocated! HAPPY JOURNEY! Bandi Jagratha mawa.. ***"));
            System.out.println(info("Current Balance: Rs." + user.getDepositBalance()));
        }
    }

    static void doReturn(User user, RentalShopBook shop, BillingSystem billing) {

        if (!shop.hasActiveRental(user.getUserId())) {
            System.out.println(warning("You don't have any active rental."));
            return;
        }

        double bill = shop.returnVehicle(user, billing);

        if (bill < 0) {
            return;
        }

        user.deductDeposit(bill);

        System.out.println(info("Bill Amount: Rs." + bill));
        System.out.println(info("Remaining Balance: Rs." + user.getDepositBalance()));

        if (user.getDepositBalance() < 0) {
            System.out.println(warning("Your bill was more than your deposit!"));
            System.out.println(error("You still owe: Rs." + (-user.getDepositBalance())));
            System.out.println(warning("Please pay the remaining amount to continue."));

            while (user.getDepositBalance() < 0) {
                System.out.println(error("Amount due: Rs." + (-user.getDepositBalance())));
                System.out.print(prompt("Enter payment: Rs."));
                double payment = sc.nextDouble();
                sc.nextLine();

                if (payment <= 0) {
                    System.out.println(error("Invalid amount. Please enter a valid amount."));
                    continue;
                }

                user.addDeposit(payment);

                if (user.getDepositBalance() < 0) {
                    System.out.println(error("Still due: Rs." + (-user.getDepositBalance())));
                } else {
                    System.out.println(success("Due cleared! Balance: Rs." + user.getDepositBalance()));
                }
            }
        }

        System.out.println(success("Vehicle returned successfully. You can rent another vehicle!"));
    }

}
