package entities;

import java.util.ArrayList;

public class User {
    private final String name;
    private final String email;
    private final String password;
    private ArrayList<Project> projects;

    public User(String name, String email, String password,
                ArrayList<Project> projects) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.projects = projects;

    }
    public String getName() {
        return this.name;
    }
}
