package models;

public class User {

    private static int counter = 1;

    private int userId;
    private String name;
    private long mobile;
    private String licenseNumber;
    private String password;
    private double depositBalance;

    public User(String name,long mobile,String license,String password){

        this.userId = counter++;
        this.name = name;
        this.mobile = mobile;
        this.licenseNumber = license;
        this.password = password;

    }

    public int getUserId(){
        return userId;
    }

    public long getMobile(){
        return mobile;
    }

    public String getPassword(){
        return password;
    }

    public double getDepositBalance(){
        return depositBalance;
    }

    public void addDeposit(double amount){
        depositBalance += amount;
    }

    public void deductDeposit(double amount){
        depositBalance -= amount;
    }

}