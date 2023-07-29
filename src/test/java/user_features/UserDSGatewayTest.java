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
    void addUserTest() throws IOException, ParseException {
        User user = new User("Alice", "12345@gmail.com", "12345", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));
    }

    @Test
    void addUserTest1() throws IOException, ParseException {
        User user = new User("Alice", "123456@gmail.com", "12345", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));
    }

    @Test
    void saveChangesTest() throws IOException, ParseException {
        User user = new User("Alice", "12345@gmail.com", "12345", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));

    }

}