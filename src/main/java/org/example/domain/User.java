package org.example.domain;

public class User extends Users{

    public User() {}

    public User(int id, String name, String email, String password, Rol rol) {
        super(id, name, email, password, rol);
    }

    public User(String name, String email, String password, Rol rol) {
        super(name, email, password, rol);
    }

    @Override
    public String toString() {
        return "User: " + getName() + " (" + getEmail() + ")";
    }
}
