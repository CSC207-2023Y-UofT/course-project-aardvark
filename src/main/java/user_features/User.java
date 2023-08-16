package user_features;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An Entity class representing the user entity.
 */
public class User {

    // instance variables
    private final String name;
    private final String email;
    private final String password;

    public ArrayList<HashMap<String, Object>> projectsList;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        this.projectsList = new ArrayList<>();
    }

    /**
     *
     * @return the name of the user.
     */
    public String getName(){
        return this.name;
    }

    /**
     *
     * @return the email of the user.
     */
    public String getEmail(){
        return this.email;
    }

    /**
     *
     * @return the password of the user.
     */
    public String getPassword(){
        return this.password;
    }


}
