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
        User user = new User("Alice", "alice@gmail.com", "12345", "12345");
        User user1 = new User("Bob", "bob@gmail.com", "123456", "123456");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.addUser(user1);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));

    }

    @Test
    void addUserTest1() throws IOException, ParseException {
        User user = new User("Ama", "ama@gmail.com", "12345", "12345");
        User user1 = new User("Robbie", "robbie@gmail.com", "123456", "123456");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.addUser(user1);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.email));

    }


}