package user_features;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    public void getNameTest(){
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        Assertions.assertEquals("Alice", user.getName());
    }

    @Test
    public void getEmailTest(){
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        Assertions.assertEquals("1234@gmail.com", user.getEmail());
    }

    @Test
    public void getPasswordTest(){
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        Assertions.assertEquals("12345", user.getPassword());
    }

    @Test
    public void getRepeatPasswordTest(){
        User user = new User("Alice", "1234@gmail.com", "12345", "12345");
        Assertions.assertEquals("12345", user.getRepeatPassword());
    }


}