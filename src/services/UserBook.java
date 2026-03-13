package services;

import java.util.HashMap;
import java.util.Scanner;

import models.User;

public class UserBook {

    static Scanner sc = new Scanner(System.in);
    public static final double MINIMUM_TOPUP = 200.0;

    private HashMap<Long, User> users = new HashMap<>();

    public boolean createAccount(String name, long mobile, String license, String password) {

        if (String.valueOf(mobile).length() != 10) {
            System.out.println("Invalid mobile number. Must be exactly 10 digits.");
            return false;
        }

        if (!isValidLicense(license)) {
            System.out.println("Invalid license number.");
            System.out.println("  - Length must be 8 to 15 characters.");
            System.out.println("  - Must be alphanumeric (letters A-Z and digits 0-9 only).");
            System.out.println("  - Must contain at least one letter AND at least one digit.");
            return false;
        }

        if (users.containsKey(mobile)) {
            System.out.println("An account with this mobile number already exists.");
            return false;
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            System.out.println("Please Enter a Strong Password");
            System.out.println("Please Enter a Strong Password");
            System.out.println(
                    "Password Must contain\n1. One Capital letter\n2. one Small letter\n3. one Special Character\n4. Digits too..\n5. At last the password length should be greater than 8..");
            return false;
        }

        users.put(mobile, new User(name, mobile, license, password));
        return true;
    }

    private boolean isValidLicense(String license) {
        if (license == null)
            return false;
        int len = license.length();
        if (len < 8 || len > 15)
            return false;

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : license.toCharArray()) {
            if (Character.isLetter(c))
                hasLetter = true;
            else if (Character.isDigit(c))
                hasDigit = true;
            else
                return false;
        }

        return hasLetter && hasDigit;
    }

    public Object login(long mobile, String password) {
        User user = users.get(mobile);
        if (user != null)
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                System.out.println("Your Password is Wrong..\nTry to Login with Correct Password..");
                System.out.println("Or Do you want to reset your password...!\n if yes please press (yes)");

                String userIP = sc.next();
                userIP.toLowerCase();

                if (userIP.equals("yes")) {
                    User user2 = resetPassword(user);

                    if (user2 == null) {
                        System.out.println("Giving You another chance please type it correctly...");

                        User user3 = resetPassword(user);

                        if (user3 == null) {
                            System.out.println("Password reset failed. Please login again with correct credentials.");
                            return new String("-1");
                        }

                        return user3;
                    }

                    return user2;

                } else {
                    System.out.println("Sarey ne istam vadhu antey..");
                    return new String("-1");
                }
            }
        return null;
    }

    public User resetPassword(User user) {
        System.out.println("Please enter your new password...");
        String newPassKey = sc.next();

        if (!newPassKey.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            System.out.println("Please Enter a Strong Password");
            System.out.println(
                    "Password Must contain\n1. One Capital letter\n2. one Small letter\n3. one Special Character\n4. Digits too..\n5. At last the password length should be greater than 8..");
            return null;
        }

        user.setPassword(newPassKey);

        System.out.println("Successfully changed Password..");

        return user;
    }

    public boolean userExists(long mobile) {
        return users.containsKey(mobile);
    }
}
