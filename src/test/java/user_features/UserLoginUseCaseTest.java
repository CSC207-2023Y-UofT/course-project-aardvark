package user_features;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserLoginUseCaseTest {

    @Test
    void checkExistsTestTrue(){
        User user = new User ("ama", "ama@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();
        UserLoginUseCase loginUser = new UserLoginUseCase("ama@gmail.com", "12345");
        Assertions.assertTrue(loginUser.checkExists());
    }

    @Test
    void checkExistsTestFalse(){
        UserLoginUseCase loginUser = new UserLoginUseCase("123@gmail.com", "12345");
        Assertions.assertFalse(loginUser.checkExists());
    }

    @Test
    void checkPasswordTestTrue(){
        User user = new User ("ama", "ama@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();
        UserLoginUseCase loginUser = new UserLoginUseCase("ama@gmail.com", "12345");
        Assertions.assertTrue(loginUser.checkPassword());
    }

    @Test
    void checkPasswordTestFalse(){
        User user = new User ("ama", "ama@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();
        UserLoginUseCase loginUser = new UserLoginUseCase("ama@gmail.com", "1234566");
        Assertions.assertFalse(loginUser.checkPassword());
    }
}
