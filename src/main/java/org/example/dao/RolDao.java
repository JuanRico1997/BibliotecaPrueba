package org.example.dao;

import org.example.domain.Rol;

public interface RolDao {
    Rol findByName(String name);
    Rol findById(int id);
}