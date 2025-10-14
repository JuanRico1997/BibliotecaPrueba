package org.example.controllers;

import org.example.domain.Book;
import org.example.domain.Loan;
import org.example.domain.Partner;
import org.example.services.BookService;
import org.example.services.LoanService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class PartnerController {
    private final LoanService loanService;
    private final BookService bookService;

    public PartnerController(LoanService loanService, BookService bookService) {
        this.loanService = loanService;
        this.bookService = bookService;
    }

    // Crear un préstamo
    public boolean loanBook(Partner partner, Book book) {


        if (partner == null || book == null) {
            JOptionPane.showMessageDialog(null, "Partner or Book is null");
            return false;
        }

        boolean success = loanService.createLoan(partner, book);
        if (success) {
            JOptionPane.showMessageDialog(null, "Loan created successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to create loan. Maybe no copies available.");
        }
        return success;
    }

    // Ver préstamos activos del partner
    public List<Loan> viewLoans(Partner partner) {
        if (partner == null) {
            JOptionPane.showMessageDialog(null, "Partner is null");
            return List.of();
        }

        List<Loan> loans = loanService.listByActive(false) // false = no devuelto → activos
                .stream()
                .filter(l -> l.getPartner().getId() == partner.getId())
                .collect(Collectors.toList());

        if (loans.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No active loans found.");
        }

        return loans;
    }

    // Ver todos los libros
    public List<Book> viewAllBooks() {
        List<Book> books = bookService.allBooks();

        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books found.");
        } else {
            StringBuilder sb = new StringBuilder("Books List:\n");
            for (Book book : books) {
                sb.append("ID: ").append(book.getId())
                        .append(" | Title: ").append(book.getTitle())
                        .append(" | Available: ").append(book.isAvailability())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }

        return books;
    }

    // Devolver un préstamo
    public void returnLoan(Loan loan) {
        if (loan == null) {
            JOptionPane.showMessageDialog(null, "Loan is null");
            return;
        }

        boolean success = loanService.returnLoan(loan.getId());
        if (success) {
            JOptionPane.showMessageDialog(null, "Loan returned successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to return loan");
        }
    }
}
