package user_features;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}