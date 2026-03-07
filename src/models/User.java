package models;

public class User {

    static int userCount=0;

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
        this.userId=++userCount;
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

}
