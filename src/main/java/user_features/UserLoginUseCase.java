package user_features;

public class UserLoginUseCase {

    User user;

    public UserLoginUseCase(String email, String password){

        UserDSGateway gateway = new UserDSGateway();
        this.user = new User("name_holder", email, password);


    }

    public boolean checkExists(){

        UserDSGateway gateway = new UserDSGateway();
        return gateway.checkUserExists(this.user);

    }

    public boolean checkPassword(){

        UserDSGateway gateway = new UserDSGateway();
        return(gateway.checkPassword(user.email, user.password));

   }
}
