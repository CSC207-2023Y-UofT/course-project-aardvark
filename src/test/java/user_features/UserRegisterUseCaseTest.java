package user_features;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterUseCaseTest {

    @Test
    void getNameTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        Assertions.assertEquals("Alice", register.getName());

    }

    @Test
    void setNameTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        register.setName("Jennifer");
        Assertions.assertEquals("Jennifer", register.getName());
    }

    @Test
    void getEmailTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        Assertions.assertEquals("1234@gmail.com", register.getEmail());

    }

    @Test
    void setEmailTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        register.setEmail("abcdef@gmail.com");
        Assertions.assertEquals("abcdef@gmail.com", register.getEmail());
    }

    @Test
    void getPasswordTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        Assertions.assertEquals("12345", register.getPassword());
    }

    @Test
    void setPasswordTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        register.setPassword("abcdef");
        Assertions.assertEquals("abcdef", register.getPassword());
    }

    @Test
    void addUserTest() {
        User user = new User("Alice", "1234@gmail.com", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        register.addUser();

        File userFile = new File("src/main/java/user_features/DataModel.json");
        Assertions.assertTrue(userFile.exists());

        UserDSGateway gateway = new UserDSGateway();
        Assertions.assertTrue(gateway.checkUserExists(user));
    }

    @Test
    void checkExistsTestTrue() {
        User user = new User("Amy", "amy@gmail.com", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase("Amy", "amy@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();

        Assertions.assertTrue(register.checkExists());

    }

    @Test
    void checkExistsTestFalse() {
        UserRegisterUseCase register = new UserRegisterUseCase("Chris", "chris@gmail.com", "12345");
        Assertions.assertFalse(register.checkExists());
    }

    @Test
    void getUserTest() {
        UserRegisterUseCase register = new UserRegisterUseCase("Alice", "1234@gmail.com", "12345");
        User user = register.getUser();
        Assertions.assertAll(
                () -> assertEquals("Alice", user.getName()),
                () -> assertEquals("1234@gmail.com", user.getEmail()),
                () -> assertEquals("12345", user.getPassword())
        );
    }
}