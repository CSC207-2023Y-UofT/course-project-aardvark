package user_features;

import models.Project;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

class UserDSGatewayTest {

    @Test
    void userLoginTest() {
        UserDSGateway gateway = new UserDSGateway();
        User registerUser = gateway.userRegister("Bob", "bob@gmail.com", "12345");
        UserDSGateway gateway2 = new UserDSGateway();
        User loginUser = gateway2.userLogin("bob@gmail.com", "12345");
        Assertions.assertEquals("bob@gmail.com", loginUser.getEmail());
        Assertions.assertEquals("12345", loginUser.getPassword());
    }

    @Test
    void userRegisterTest() {
        UserDSGateway gateway = new UserDSGateway();
        User registerUser = gateway.userRegister("Bob", "bob@gmail.com", "12345");
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
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.getEmail()));

    }

    @Test
    void addUserTest1() throws IOException {
        User user = new User("Ama", "ama@gmail.com", "12345");
        User user1 = new User("Robbie", "robbie@gmail.com", "123456");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        gateway.addUser(user1);
        gateway.saveChanges();
        Assertions.assertTrue(gateway.dataDocument.containsKey(user.getEmail()));

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
        Assertions.assertTrue(gateway.checkUserExists(user.getEmail()));
    }


    @Test
    void checkUserExistsTestFalse(){
        UserDSGateway gateway = new UserDSGateway();
        User loginUser = gateway.userLogin("Ama", "1234");
        Assertions.assertFalse(gateway.checkUserExists(loginUser.getEmail()));
    }

    @Test
    void checkUserExistsTestTrue2(){
        User user = new User("Sample User", "sample.user@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        Assertions.assertTrue(gateway.checkUserExists(user.getEmail()));
    }

    @Test
    void checkPasswordTestTrue(){
        User oldUser = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(oldUser);
        gateway.saveChanges();
        User user = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        Assertions.assertTrue(gateway.checkPassword(user.getEmail(), user.getPassword()));
    }

    @Test
    void checkPasswordTestFalse(){
        User oldUser = new User("Tony Stark", "tony.stark@gmail.com", "ironman4ever");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(oldUser);
        gateway.saveChanges();
        User user = new User("Tony Stark", "tony.stark@gmail.com", "thanos4ever");
        Assertions.assertFalse(gateway.checkPassword(user.getEmail(), user.getPassword()));
    }

    @Test
    @SuppressWarnings("unchecked")
    void addProjectTest(){
        User user = new User("Jon", "jon.doe@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        Project project = new Project("projectName");

        gateway.addNewProject(user, project);
        gateway.saveChanges();

    }

    @Test
    @SuppressWarnings("unchecked")
    void deleteProjectTest(){
        User user = new User("Jon", "jon.doe@gmail.com", "12345");
        UserDSGateway gateway = new UserDSGateway();
        gateway.addUser(user);
        Project project = new Project("projectName2");

        gateway.addNewProject(user, project);
        gateway.deleteProject(user, project.getName());
        gateway.saveChanges();

    }





}