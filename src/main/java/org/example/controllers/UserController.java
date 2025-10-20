package org.example.controllers;

import org.example.domain.Book;
import org.example.domain.Loan;
import org.example.domain.Users;
import org.example.services.BookService;
import org.example.services.LoanService;
import org.example.services.UserService;

import javax.swing.*;
import java.util.List;

public class UserController {
    private final BookService bookService;
    private final LoanService loanService;
    private final UserService userService;

    public UserController(BookService bookService, LoanService loanService, UserService userService) {
        this.bookService = bookService;
        this.loanService = loanService;
        this.userService = userService;
    }

    // ===================== BOOK METHODS =====================
    public void createBook(Book book) {
        if (book == null) {
            JOptionPane.showMessageDialog(null, "Book cannot be null");
            return;
        }
        Book created = bookService.addBook(book);
        if (created != null) {
            JOptionPane.showMessageDialog(null, "Book created successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to create book");
        }
    }

    public void deleteBook(int bookId) {
        boolean deleted = bookService.deleteBook(bookId);
        if (deleted) {
            JOptionPane.showMessageDialog(null, "Book deleted successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to delete book");
        }
    }

    public void editBook(Book updatedBook) {
        if (updatedBook == null) {
            JOptionPane.showMessageDialog(null, "Book cannot be null");
            return;
        }

        Book existingBook = bookService.findBookById(updatedBook.getId());
        if (existingBook == null) {
            JOptionPane.showMessageDialog(null, "Book not found");
            return;
        }

        // Mantener valores no editados si no los pides en la vista (por ejemplo, year_publication)
        if (updatedBook.getTitle() == null) updatedBook.setTitle(existingBook.getTitle());
        if (updatedBook.getAuthor() == null) updatedBook.setAuthor(existingBook.getAuthor());
        if (updatedBook.isAvailability() == 0) updatedBook.setAvailability(existingBook.isAvailability());
        if (updatedBook.getYear_publication() == 0) updatedBook.setYear_publication(existingBook.getYear_publication());

        boolean updated = bookService.updateBook(updatedBook);
        JOptionPane.showMessageDialog(null, updated ? "Book updated successfully" : "Failed to update book");
    }

    public List<Book> viewAllBooks() {
        List<Book> books = bookService.allBooks();
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No books found");
        } else {
            StringBuilder sb = new StringBuilder("All Books:\n");
            for (Book book : books) {
                sb.append("ID: ").append(book.getId())
                        .append(" | Title: ").append(book.getTitle())
                        .append("| Author: ").append(book.getAuthor().getName())
                        .append(" | Availability: ").append(book.isAvailability())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        return books;
    }

    // ===================== LOAN METHODS =====================
    public List<Loan> viewAllLoans() {
        List<Loan> loans = loanService.listAll();
        if (loans.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No loans found");
        } else {
            StringBuilder sb = new StringBuilder("All Loans:\n");
            for (Loan loan : loans) {
                sb.append("Loan ID: ").append(loan.getId())
                        .append(" | Book: ").append(loan.getBook().getTitle())
                        .append(" | Partner: ").append(loan.getPartner().getName())
                        .append(" | Returned: ").append(loan.isReturned() ? "Yes" : "No")
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        return loans;
    }

    // ===================== USER METHODS =====================
    public List<Users> viewAllUsers() {
        List<Users> users = userService.listAllUsers();
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users found");
        } else {
            StringBuilder sb = new StringBuilder("All Users:\n");
            for (Users user : users) {
                sb.append("ID: ").append(user.getId())
                        .append(" | Name: ").append(user.getName())
                        .append(" | Email: ").append(user.getEmail())
                        .append(" | Role: ").append(user.getRol().getName())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        return users;
    }
}
