package user_features;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

class UserDSGatewayTest {

    @Test
    void userLoginTest() {
        UserDSGateway gateway = new UserDSGateway();
        User loginUser = gateway.UserLogin("amy@gmail.com", "12345");
        Assertions.assertEquals("name_holder", loginUser.getName());
        Assertions.assertEquals("amy@gmail.com", loginUser.getEmail());
        Assertions.assertEquals("12345", loginUser.getPassword());
    }

    @Test
    void userRegisterTest() {
        UserDSGateway gateway = new UserDSGateway();
        User registerUser = gateway.UserRegister("Bob", "bob@gmail.com", "12345");
        Assertions.assertEquals("Bob", registerUser.getName());
        Assertions.assertEquals("bob@gmail.com", registerUser.getEmail());
        Assertions.assertEquals("12345", registerUser.getPassword());
    }

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
    void getNameTest() {
        User user = new User("Kelly", "kelly@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.saveChanges();
        Assertions.assertEquals("Kelly", gateway.getName("kelly@gmail.com"));
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
        Assertions.assertTrue(gateway.checkPassword(user.email, user.password));
    }

    @Test
    void checkPasswordTestFalse(){
        User oldUser = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(oldUser);
        gateway.saveChanges();
        User user = new User("Tony Stark", "tony.stark@gmail.com", "thanos4ever");
        Assertions.assertFalse(gateway.checkPassword(user.email, user.password));
    }

    @Test
    @SuppressWarnings("unchecked")
    void addProjectTest(){
        User user = new User("Jon", "jon.doe@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        JSONObject visualElement = new JSONObject();
        visualElement.put("visualName", new JSONArray());

        HashMap<String, Object> project = new HashMap<>();
        project.put("ProjectName", "projectName");
        project.put("UpdateDate", "updatedAt");
        project.put("Width", "width");
        project.put("Height", "height");
        project.put("VisualElements", visualElement);
        gateway.addProject(user, project);
        gateway.saveChanges();

    }

    @Test
    @SuppressWarnings("unchecked")
    void deleteProjectTest(){
        User user = new User("Jon", "jon.doe@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        JSONObject visualElement = new JSONObject();
        visualElement.put("visualName", new JSONArray());

        HashMap<String, Object> project = new HashMap<>();
        project.put("ProjectName", "projectName");
        project.put("UpdateDate", "updatedAt");
        project.put("Width", "width");
        project.put("Height", "height");
        project.put("VisualElements", visualElement);
        gateway.addProject(user, project);
        Assertions.assertTrue(gateway.deleteProject(user, project));
        gateway.saveChanges();

    }

}