package user_features;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class UserRegisterUseCase {

    // instance variable
    User user;

    public UserRegisterUseCase(User user) throws IOException, ParseException {
        this.user = user;
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

    public String getRepeatPassword() {
        return user.repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        user.repeatPassword = repeatPassword;
    }

    public boolean checkPasswordMatch() {return (Objects.equals(user.password, user.repeatPassword));}


    }

