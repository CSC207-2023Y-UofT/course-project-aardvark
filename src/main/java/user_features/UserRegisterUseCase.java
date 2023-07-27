package user_features;

import java.util.Objects;

public class UserRegisterUseCase {

    // instance variable
    User user;

    public UserRegisterUseCase(User user){
        this.user = user;
    }

    public String getName(){
        return user.name;
    }

    public void setName(String name){
        user.name = name;
    }

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

    public void addNewUser(){

        }


    }

}
