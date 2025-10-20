package org.example.dao;

import org.example.domain.Users;

import java.util.List;

public interface UserDao {
    Users createUser(Users user);
    Users findByEmail(String email);
    List<Users> findAllUsers();
}
