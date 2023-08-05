package user_features;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    // instance variables
    private String name;
    private String email;
    private String password;

    ArrayList<HashMap<String, Object>> projectsList;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.projectsList = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

}
