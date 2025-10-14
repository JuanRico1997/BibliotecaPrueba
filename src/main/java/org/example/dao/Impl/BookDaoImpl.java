package org.example.dao.Impl;

import org.example.config.ConfigDb;
import org.example.dao.BookDao;
import org.example.domain.Author;
import org.example.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    @Override
    public boolean createBook(Book book) {
        String sql = "INSERT INTO books (title, id_author, publication_year, availability) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());   // ← aquí va 0 si no se setea antes
            ps.setInt(3, book.getYear_publication());
            ps.setInt(4, book.isAvailability());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) book.setId(rs.getInt(1));
            return true;

        } catch (SQLException e) {
            System.out.println("Error al crear libro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAvailableBook(int idBook, int availability) {
        String sql = "UPDATE books SET availability = ? WHERE id_book = ?";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, availability);
            ps.setInt(2, idBook);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteBook(int idBook) {
        String sql = "DELETE FROM books WHERE id_book = ?";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBook);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Book> findAllBook() {
        List<Book> listAllBook = new ArrayList<>();
        String sql = "SELECT b.*, a.name AS author_name FROM books b INNER JOIN authors a ON b.id_author = a.id_author";

        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Author author = new Author(rs.getInt("id_author"), rs.getString("author_name"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), author, rs.getInt("publication_year"), rs.getInt("availability"));
                listAllBook.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
        return listAllBook;
    }

    @Override
    public List<Book> findBookByAvailable() {
        String sql = "SELECT b.*, a.name AS author_name FROM books b INNER JOIN authors a ON b.id_author = a.id_author WHERE b.availability > 0";
        List<Book> listBooksAvailable = new ArrayList<>();

        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Author author = new Author(rs.getInt("id_author"), rs.getString("author_name"));
                Book book = new Book(rs.getInt("id_book"), rs.getString("title"), author, rs.getInt("publication_year"), rs.getInt("availability"));
                listBooksAvailable.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener libros disponibles: " + e.getMessage());
        }
        return listBooksAvailable;
    }

    @Override
    public Book findBookById(int id) {
        String sql = "SELECT b.*, a.name AS author_name FROM books b INNER JOIN authors a ON b.id_author = a.id_author WHERE id_book = ?";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Author author = new Author(rs.getInt("id_author"), rs.getString("author_name"));
                return new Book(rs.getInt("id_book"), rs.getString("title"), author, rs.getInt("publication_year"), rs.getInt("availability"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener libro por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, id_author = ?, publication_year = ?, availability = ? WHERE id_book = ?";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getYear_publication());
            ps.setInt(4, book.isAvailability());
            ps.setInt(5, book.getId());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar libro: " + e.getMessage());
        }
        return false;
    }
}