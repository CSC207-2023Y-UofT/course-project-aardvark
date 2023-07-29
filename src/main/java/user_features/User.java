package user_features;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    // instance variables
    String name;
    String email;
    String password;
    String repeatPassword;

    ArrayList<HashMap<String, Object>> projectsList;

    public User(String name, String email, String password, String repeatPassword){
        this.name = name;
        this.email = email;
        this.password = password;
        this.repeatPassword= repeatPassword;
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

    public String getRepeatPassword(){
        return this.repeatPassword;
    }
}
