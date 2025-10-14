package org.example.domain;

public abstract class Users {

    private int id;
    private String name;
    private String email;
    private String password;
    private Rol rol;


    public Users() {}

    public Users(int id, String name, String email, String password,Rol rol) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public Users(String name, String email, String password,Rol rol) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Users" +
                "id:" + id +
                "|| name:" + name +
                "|| email:" + email +
                "|| password:" + password +
                "|| rol: " + rol;
    }
}
