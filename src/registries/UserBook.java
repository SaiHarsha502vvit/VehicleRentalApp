package registries;

import java.util.HashMap;
import java.util.Map;

import models.User;

public class UserBook {
    Map<Long,User> users = new HashMap<>();


    public boolean createAccount(String name,Long mobile,String license,String password) {
        return true;
    }

    
}
