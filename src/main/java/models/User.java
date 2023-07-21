package models;

import java.util.ArrayList;

public class User {
    private final String name;
    private final String email;
    private final String password;
    public ArrayList<Project> projects;

    public User(String name, String email, String password,
                ArrayList<Project> projects) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.projects = projects;

    }

    public User(String name, String email, String password) {
        this(name, email, password, new ArrayList<>());
    }

    public boolean passwordMatches(String confirmPassword) {
        return this.password.equals(confirmPassword);
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}
