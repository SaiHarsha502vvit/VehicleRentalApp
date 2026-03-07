import java.util.HashMap;
import java.util.Scanner;

import models.User;

public class ConsoleApplication {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        startSystem();
    }

    static void startSystem() {
        System.out.println("Welcome to Rental Application");
        System.out.println("=============================");

        System.out.println("1.Sign Up \n2.Login");

        System.out.println(
                "Choose your choice \nIf you have account choose 2.Login \n          or\nChoose 1.Sign Up  to create account");
        int userIp = sc.nextInt();

        switch (userIp) {
            case 1:
                signUp();
                break;
            case 2:
                login();
            default:
                break;
        }

    }

    public static void signUp() {

        System.out
                .println("Please Enter User details... \n1.Your name\n2.Password\n3.Mobile Number \n4.License Number\n");

        String username = sc.next();
        String password = sc.next();
        long mobile = sc.nextLong();
        String licenseNumber = sc.next();

        System.out.println(username + "\n" + password + "\n" + mobile + "\n" + licenseNumber);

        // Task 1 = if given wrong ask them to give right information

        User harsha = new User(username, mobile, licenseNumber, password);

        System.out.println("Harsha's userId "+harsha.getUserId());

        User harshaVardhan = new User(username, mobile, licenseNumber, password);

        System.out.println("Harsha Vardhan's userId "+harshaVardhan.getUserId());



    }
  

    public static void login() {
        // Task 2 = Write logic for Login;
    }
}
