package org.example.controllers;

import org.example.domain.Partner;
import org.example.domain.Rol;
import org.example.domain.User;
import org.example.domain.Users;
import org.example.services.UserService;

import javax.swing.*;

public class AuthController {
    private UserService userService;
    private Users loggedUser;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    public Users login(String email,String password){
        Users user = userService.login(email,password);
        if (user == null){
            loggedUser = user;
            JOptionPane.showMessageDialog(null,"❌ Login failed.");
            return null;
        }
        JOptionPane.showMessageDialog(null,"✅ Login successful. Welcome " + user.getName());
        return user;
    }

    public void register(String name, String email, String password, String roleName) {
        Users success = userService.registerUser(name, email, password, roleName);

        if (success != null) {
            JOptionPane.showMessageDialog(null, "User registered successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to register user. Email may already exist or role is invalid.");
        }
    }




    public void logout(){
        if (loggedUser != null){
            JOptionPane.showMessageDialog(null,"✅ Logged out successfully. Goodbye " + loggedUser.getName());
        }else {
            JOptionPane.showMessageDialog(null,"⚠️ No user is currently logged in.");
        }

    }
}
