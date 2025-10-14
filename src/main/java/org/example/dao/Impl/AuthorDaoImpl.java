package org.example.dao.Impl;

import org.example.config.ConfigDb;
import org.example.dao.AuthorDao;
import org.example.domain.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author findByName(String nameAuthor) {
        String sql = "SELECT * FROM authors WHERE name = ?";
        try (Connection conn = ConfigDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1,nameAuthor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return new Author(rs.getInt("id_author"),rs.getString("name"));
            }

        }catch (SQLException e){
            System.out.println("Error al obtener autor por nombre: " + e.getMessage());

        }
        return null;
    }

    @Override
    public boolean createAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    author.setId(rs.getInt(1));
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error al crear autor: " + e.getMessage());
        }
        return false;
    }
}
