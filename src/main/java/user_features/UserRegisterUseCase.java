package user_features;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class UserRegisterUseCase {

    // instance variable
    User user;

    public UserRegisterUseCase(String name, String email, String password){
        this.user = new User(name, email, password);
    }

    public String getName(){
        return user.name;
    }

    public void setName(String name){
        user.name = name;
    }

    public String getEmail() { return user.email; }

    public void setEmail(String email) { user.email = email; }

    public String getPassword() {
        return user.password;
    }

    public void setPassword(String password) {
        user.password = password;
    }

    public void addUser() {

        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(this.user);
        gateway.saveChanges();
    }

    public boolean checkExists(){

        UserDSGateway gateway = new UserDSGateway();
        return gateway.checkUserExists(this.user);

    }


    }

