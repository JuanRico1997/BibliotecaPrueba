package org.example.dao.Impl;

import org.example.config.ConfigDb;
import org.example.dao.UserDao;
import org.example.domain.Partner;
import org.example.domain.Rol;
import org.example.domain.User;
import org.example.domain.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public Users createUser(Users user) {
        String sql = "INSERT INTO users (name,email,password,id_rol) VALUES (?,?,?,?)";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRol().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                return user;
            }
        } catch (SQLException e){
            System.out.println("❌ Error al crear usuario: " + e.getMessage());
        }
        return null;
        }

        @Override
    public Users findByEmail(String email) {
        String sql = """
        SELECT 
            u.*, 
            r.nombre AS rol_name 
        FROM users u 
        INNER JOIN roles r ON u.id_rol = r.id_rol 
        WHERE u.email = ?
        """;

        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("rol_name"));
                String rolName = rol.getName(); // asegúrate de que el getter sea getNombre()

                if ("Partner".equalsIgnoreCase(rolName)) {
                    return new Partner(
                            rs.getInt("id_user"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rol,
                            rs.getBoolean("asset")
                    );
                } else {
                    return new User(
                            rs.getInt("id_user"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rol
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al obtener usuario por email: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Users> findAllUsers() {
        List<Users> usersList = new ArrayList<>();
        String sql = """
        SELECT 
            u.id_user,
            u.name,
            u.email,
            u.password,
            u.asset,
            r.id_rol,
            r.nombre AS rol_nombre
        FROM users u
        INNER JOIN roles r ON u.id_rol = r.id_rol
        """;

        try (Connection conn = ConfigDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("rol_nombre"));
                String rolNombre = rol.getName();

                Users user;

                if ("Partner".equalsIgnoreCase(rolNombre)) {
                    user = new Partner(
                            rs.getInt("id_user"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rol,
                            rs.getBoolean("asset")
                    );
                } else if ("User".equalsIgnoreCase(rolNombre)) {
                    user = new User(
                            rs.getInt("id_user"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rol
                    );
                } else {
                    // si hay un rol desconocido, se puede saltar
                    continue;
                }

                usersList.add(user);
            }


        } catch (SQLException e){
            System.out.println("Error al obtener usuarios: " + e.getMessage());
        }
        return usersList;
    }
}

