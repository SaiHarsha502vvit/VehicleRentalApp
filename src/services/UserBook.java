package services;

import java.util.HashMap;
import models.User;

public class UserBook {

    private HashMap<Long,User> users = new HashMap<>();


    public boolean userExists(long mobile){
        return users.containsKey(mobile);
    }


    public boolean createAccount(String name,long mobile,String license,String password){

        /*
         TODO: Mobile validation A valid mobile number must contain exactly 10 digits. 
                Morning Sir cheypia work edhi 
        */
        
        if(String.valueOf(mobile).length() != 10){
            System.out.println("Invalid Mobile Number");
            return false;
        }

        if(users.containsKey(mobile)){
            System.out.println("User already exists");
            return false;
        }

        User user = new User(name,mobile,license,password);

        users.put(mobile,user);

        return true;

    }


    public User login(long mobile,String password){

        User user = users.get(mobile);

        if(user != null && user.getPassword().equals(password))
            return user;

        return null;

    }

}