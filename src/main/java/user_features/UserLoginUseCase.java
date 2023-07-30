package user_features;

public class UserLoginUseCase {

    User user;

    public UserLoginUseCase(String name, String email, String password){

        this.user = new User(name, email, password);

    }
}
