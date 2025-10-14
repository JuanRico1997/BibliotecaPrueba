package org.example.dao.Impl;

import org.example.config.ConfigDb;
import org.example.dao.LoanDao;
import org.example.domain.Book;
import org.example.domain.Loan;
import org.example.domain.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDaoImpl implements LoanDao {
    @Override
    public boolean createLoan(Loan loan) {
        String sql = "INSERT INTO loans (id_book, id_partner, date_loan, date_return) VALUES (?,?,?,?)";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1,loan.getBook().getId());
            ps.setInt(2,loan.getPartner().getId());
            ps.setDate(3, Date.valueOf(loan.getDate_loan()));
            ps.setDate(4, Date.valueOf(loan.getDate_return()));
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) loan.setId(rs.getInt(1));
            return true;
        } catch (SQLException e){
            System.out.println("Error al crear prestamo: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Loan> findByAsset(boolean returned) {
        List<Loan> listAsset = new ArrayList<>();
        String sql = """
        SELECT l.id_loan,
            b.id_book,
            b.title AS book_title,
            u.id_user AS id_partner,
            u.name AS partner_name,
            l.date_loan,
            l.date_return,
            l.returned
        FROM loans l
        INNER JOIN books b ON l.id_book = b.id_book
        INNER JOIN users u ON l.id_partner = u.id_user
        WHERE l.returned = ?
        """;

        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setBoolean(1,returned);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Book book = new Book(rs.getInt("id_book"),rs.getString("book_title"),null,0,0);
                Partner partner = new Partner(rs.getInt("id_partner"),rs.getString("partner_name"),null,null,null);
                Loan loan = new Loan(rs.getInt("id_loan"),book,partner,rs.getDate("date_loan").toLocalDate(),rs.getDate("date_return").toLocalDate(),rs.getBoolean("returned"));
                listAsset.add(loan);
            }

        }catch (SQLException e){
            System.out.println("Error al obtener prestamos: " + e.getMessage());
        }
        return listAsset;
    }

    @Override
    public List<Loan> findAllLoan() {
        List<Loan> listAllLoan= new ArrayList<>();
        String sql = "SELECT l.* , b.title AS book_title, u.name AS partner_name ,b.availability AS book_availability FROM loans l INNER JOIN books b ON l.id_book = b.id_book INNER JOIN users u ON l.id_partner = u.id_user";
        try (Connection conn = ConfigDb.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){

            while (rs.next()) {
            Book book = new Book(rs.getInt("id_book"),rs.getString("book_title"),null,0,0);
            Partner partner = new Partner(rs.getInt("id_partner"),rs.getString("partner_name"),null,null,null,rs.getBoolean("returned"));
            Loan loan = new Loan(rs.getInt("id_loan"),book,partner,rs.getDate("date_loan").toLocalDate(),rs.getDate("date_return").toLocalDate(),rs.getBoolean("returned"));
            listAllLoan.add(loan);
            }
        } catch (SQLException e){
            System.out.println("Error al obtener prestamos: " + e.getMessage());
        }
        return listAllLoan;
    }

    @Override
    public Loan findById(int id_loan) {
        String sql = "SELECT l.* , b.title AS book_title, u.name AS partner_name ,b.availability AS book_availability FROM loans l INNER JOIN books b ON l.id_book = b.id_book INNER JOIN users u ON l.id_partner = u.id_user WHERE l.id_loan = ?";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,id_loan);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                Book book = new Book(rs.getInt("id_book"),rs.getString("book_title"),null,0,rs.getInt("book_availability"));
                Partner partner = new Partner(rs.getInt("id_partner"),rs.getString("partner_name"),null,null,null,rs.getBoolean("returned"));
                return new Loan(rs.getInt("id_loan"),book,partner,rs.getDate("date_loan").toLocalDate(),rs.getDate("date_return").toLocalDate(),rs.getBoolean("returned"));

            }
        } catch (SQLException e){
            System.out.println("Error al obtener prestamo por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean returnLoan(int id_loan) {
        String sql = "UPDATE loans SET returned = TRUE WHERE id_loan = ?";
        try (Connection conn = ConfigDb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1,id_loan);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e){
            System.out.println("Error al devolver libro: " + e.getMessage());
            return false;
        }
    }
}
