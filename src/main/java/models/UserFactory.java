package models;

public class UserFactory {
    public User create(String name, String email, String password) {
        return new User(name, email, password);
    }
}
