package models;

public class User {

    static int userCount = 0;

    private int userId;
    private String name;
    private long mobile;
    private String licenseNumber;
    private String password;
    private double depoistBalance;

    public User(String name, long mobile, String licenseNumber, String password) {
        this.name = name;
        this.mobile = mobile;
        this.licenseNumber = licenseNumber;
        this.password = password;
        this.userId = ++userCount;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public long getMobile() {
        return mobile;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getPassword() {
        return password;
    }

    public double getDepoistBalance() {
        return depoistBalance;
    }

    public boolean addDeposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amout");
            return false;
        }
        depoistBalance = depoistBalance + amount;
        System.out.println(depoistBalance + " Deposited Successfully");

        return true;

    }

    public boolean deductDeposit(double amount) {

        if (amount > depoistBalance) {
            System.out.println("Insufficient Balance.");
            return false;
        }

        depoistBalance = depoistBalance - amount;
        System.out.println("Deducted amount from your  advance : " + depoistBalance);

        return true;
    }

}
