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
            }else{
                System.out.println("Your Password is Wrong..\nTry to Login with Correct Password..");
                System.out.println("Or Do you want to reset your password...!\n if yes please press (yes)");

                String userIP = sc.next();
                userIP.toLowerCase();

                if (userIP.equals("yes")) {
                    resetPassword(user);

                    return user;
                }else{
                    System.out.println("Sarey ne istam vadhu antey..");
                    return new String("-1");
                }
            }
        return null;
    }

    public User resetPassword(User user){
        System.out.println("Please enter your new password...");
        String newPassKey = sc.next();

        user.setPassword(newPassKey);

        System.out.println("Successfully changed Password..");
    
        return user;
    }

    public boolean userExists(long mobile) {
        return users.containsKey(mobile);
    }
}
