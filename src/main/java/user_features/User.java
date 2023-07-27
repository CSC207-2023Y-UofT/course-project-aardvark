package user_features;

public class User {

    // instance variables
    String name;
    String email;
    String password;
    String repeatPassword;

    public User(String name, String email, String password, String repeatPassword){
        this.name = name;
        this.email = email;
        this.password = password;
        this.repeatPassword= repeatPassword;
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
