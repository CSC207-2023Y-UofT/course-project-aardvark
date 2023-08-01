package user_features;

public class UserLoginUseCase {

    User user;

    public UserLoginUseCase(String email, String password){

        UserDSGateway gateway = new UserDSGateway();
        String name = gateway.getName(email);
        this.user = new User(name, email, password);


    }

    public boolean checkExists(){

        UserDSGateway gateway = new UserDSGateway();
        return gateway.checkUserExists(this.user);

    }

    public boolean checkPassword(String password){

        UserDSGateway gateway = new UserDSGateway();
        return(gateway.checkPassword(this.user));

   }
}
