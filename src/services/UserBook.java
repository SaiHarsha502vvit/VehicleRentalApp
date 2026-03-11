package services;

import java.util.HashMap;
import models.User;

public class UserBook {

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

    public User login(long mobile, String password) {
        User user = users.get(mobile);
        if (user != null && user.getPassword().equals(password))
            return user;
        return null;
    }

    public boolean userExists(long mobile) {
        return users.containsKey(mobile);
    }
}
