package org.example.dao.Impl;

import org.example.config.ConfigDb;
import org.example.dao.RolDao;
import org.example.domain.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolDaoImpl implements RolDao {

    @Override
    public Rol findByName(String name) {
        String sql = "SELECT * FROM roles WHERE nombre = ?";

        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar rol por nombre: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Rol findById(int id) {
        String sql = "SELECT * FROM roles WHERE id_rol = ?";

        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Rol(
                        rs.getInt("id_rol"),
                        rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar rol por ID: " + e.getMessage());
        }
        return null;
    }
}