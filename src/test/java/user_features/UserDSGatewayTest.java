package user_features;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

class UserDSGatewayTest {


    @Test
    void addUserTest() throws IOException {
        User user = new User("Alice", "alice@gmail.com", "12345");
        User user1 = new User("Bob", "bob@gmail.com", "123456");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.addUser(user1);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));

    }

    @Test
    void addUserTest1() throws IOException {
        User user = new User("Ama", "ama@gmail.com", "12345");
        User user1 = new User("Robbie", "robbie@gmail.com", "123456");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.addUser(user1);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));

    }

    @Test
    void checkUserExistsTestTrue(){
        User user = new User("Emma", "emma@gmail.com", "abcdef");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.checkUserExists(user));
    }

    @Test
    void checkUserExistsTestFalse(){
        User user = new User("Harry", "harry@gmail.com", "abcdef");
        UserDSGateway gateway = new UserDSGateway();
        Assertions.assertFalse(gateway.checkUserExists(user));
    }

    @Test
    void checkUserExistsTestTrue2(){
        User user = new User("Sample User", "sample.user@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        Assertions.assertTrue(gateway.checkUserExists(user));
    }

    @Test
    void checkPasswordTestTrue(){
        User oldUser = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(oldUser);
        gateway.saveChanges();
        User user = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        Assertions.assertTrue(gateway.checkPassword(user));
    }

    @Test
    void checkPasswordTestFalse(){
        User oldUser = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(oldUser);
        gateway.saveChanges();
        User user = new User("Tony Stark", "tony.stark@gmail.com", "thanos4ever");
        Assertions.assertFalse(gateway.checkPassword(user));
    }

    @Test
    void addProjectTest(){
        User user = new User("Jon", "jon.doe@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.addProject(user, project);

    }




}