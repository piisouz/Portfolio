package com.mycompany.systemsoftproject.models;
import com.mycompany.systemsoftproject.models.Role;

public class User {
    private String username;
    private String password; // makes private variables so user name password and a role attached to them
    private Role role;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
    // all these functions above gets the inputs
    public void setRole(Role role) {
        this.role = role;
    }
}
