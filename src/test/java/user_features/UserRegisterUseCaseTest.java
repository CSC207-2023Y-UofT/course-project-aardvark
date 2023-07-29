package user_features;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserRegisterUseCaseTest {

    @Test
    void getNameTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        Assertions.assertEquals("Alice", register.getName());

    }

    @Test
    void setNameTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        register.setName("Jennifer");
        Assertions.assertEquals("Jennifer", register.getName());
    }

    @Test
    void getEmailTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        Assertions.assertEquals("1234@gmail.com", register.getEmail());

    }

    @Test
    void setEmailTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        register.setEmail("abcdef@gmail.com");
        Assertions.assertEquals("abcdef@gmail.com", register.getEmail());
    }

    @Test
    void getPasswordTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        Assertions.assertEquals("12345", register.getPassword());
    }

    @Test
    void setPasswordTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        register.setPassword("abcdef");
        Assertions.assertEquals("abcdef", register.getPassword());
    }

    @Test
    void getRepeatPasswordTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        Assertions.assertEquals("12345", register.getRepeatPassword());
    }

    @Test
    void setRepeatPasswordTest() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        register.setRepeatPassword("abcdef");
        Assertions.assertEquals("abcdef", register.getRepeatPassword());
    }

    @Test
    void checkPasswordMatchTrue() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        assertTrue(register.checkPasswordMatch());
    }

    @Test
    void checkPasswordMatchFalse() throws IOException, ParseException {
        User user = new User("Alice", "1234@gmail.com", "12345", "abcdef");
        UserRegisterUseCase register = new UserRegisterUseCase(user);
        assertFalse(register.checkPasswordMatch());
    }
}