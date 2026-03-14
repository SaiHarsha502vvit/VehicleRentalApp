package services;

import java.util.HashMap;
import java.util.Scanner;

import models.User;

import static utils.ConsoleColors.*;

public class UserBook {

    static Scanner sc = new Scanner(System.in);
    public static final double MINIMUM_TOPUP = 200.0;

    private HashMap<Long, User> users = new HashMap<>();

    public boolean createAccount(String name, long mobile, String license, String password) {

        if (String.valueOf(mobile).length() != 10) {
            System.out.println(error("Invalid mobile number. Must be exactly 10 digits."));
            return false;
        }

        if (!isValidLicense(license)) {
            System.out.println(error("Invalid license number."));
            System.out.println(warning("  - Length must be 8 to 15 characters."));
            System.out.println(warning("  - Must be alphanumeric (letters A-Z and digits 0-9 only)."));
            System.out.println(warning("  - Must contain at least one letter AND at least one digit."));
            return false;
        }

        if (users.containsKey(mobile)) {
            System.out.println(error("An account with this mobile number already exists."));
            return false;
        }

        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            System.out.println(error("Please enter a strong password."));
            System.out.println(warning(
                    "Password must contain\n1. One capital letter\n2. One small letter\n3. One special character\n4. Digits too\n5. At least 8 characters in length"));
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
                System.out.println(error("Your password is wrong. Try to login with the correct password."));
                System.out.println(prompt("Do you want to reset your password? Type yes to continue: "));

                String userIP = sc.next();
                userIP.toLowerCase();

                if (userIP.equals("yes")) {
                    User user2 = resetPassword(user);

                    if (user2 == null) {
                        System.out.println(warning("Giving you another chance. Please type it correctly..."));

                        User user3 = resetPassword(user);

                        if (user3 == null) {
                            System.out.println(
                                    error("Password reset failed. Please login again with correct credentials."));
                            return new String("-1");
                        }

                        return user3;
                    }

                    return user2;

                } else {
                    System.out.println(warning("Password reset skipped."));
                    return new String("-1");
                }
            }
        return null;
    }

    public User resetPassword(User user) {
        System.out.println(prompt("Please enter your new password..."));
        String newPassKey = sc.next();

        if (!newPassKey.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            System.out.println(error("Please enter a strong password."));
            System.out.println(warning(
                    "Password must contain\n1. One capital letter\n2. One small letter\n3. One special character\n4. Digits too\n5. At least 8 characters in length"));
            return null;
        }

        user.setPassword(newPassKey);

        System.out.println(success("Successfully changed password."));

        return user;
    }

    public boolean userExists(long mobile) {
        return users.containsKey(mobile);
    }
}
