package org.example.views;

import org.example.controllers.UserController;
import org.example.domain.Author;
import org.example.domain.Book;
import org.example.domain.Loan;
import org.example.domain.Users;

import javax.swing.*;
import java.util.List;

public class UserView {
    private final UserController controller;

    public UserView(UserController controller) {
        this.controller = controller;
    }


    public void showMenu() {
        String[] options = {
                "Create Book",
                "Edit Book",
                "Delete Book",
                "View All Books",
                "View All Loans",
                "View All Users",
                "Logout"
        };
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "User Menu",
                    "Library System",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> createBook();
                case 1 -> editBook();
                case 2 -> deleteBook();
                case 3 -> controller.viewAllBooks();
                case 4 -> controller.viewAllLoans();
                case 5 -> controller.viewAllUsers();
                case 6 -> JOptionPane.showMessageDialog(null, "Logging out...");
                default -> JOptionPane.showMessageDialog(null, "Invalid option");
            }
        } while (choice != 6 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void createBook() {
        String title = JOptionPane.showInputDialog("Enter book title:");
        if (title == null || title.isEmpty()) return;

        String authorName = JOptionPane.showInputDialog("Enter author name:");
        if (authorName == null || authorName.isEmpty()) return;

        String copiesStr = JOptionPane.showInputDialog("Enter number of copies:");
        if (copiesStr == null || copiesStr.isEmpty()) return;

        try {
            int copies = Integer.parseInt(copiesStr);

            // Crear el objeto Author
            Author author = new Author();
            author.setName(authorName);

            // Crear el libro y asignarle el autor
            Book book = new Book();
            book.setTitle(title);
            book.setAvailability(copies);
            book.setAuthor(author);

            controller.createBook(book);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format for copies.");
        }
    }

    private void editBook() {
        String idStr = JOptionPane.showInputDialog("Enter Book ID to edit:");
        if (idStr == null || idStr.isEmpty()) return;

        try {
            int bookId = Integer.parseInt(idStr);
            String newTitle = JOptionPane.showInputDialog("Enter new title:");
            if (newTitle == null || newTitle.isEmpty()) return;

            String newAuthor = JOptionPane.showInputDialog("Enter new author:");
            if (newAuthor == null || newAuthor.isEmpty()) return;

            String copiesStr = JOptionPane.showInputDialog("Enter new number of copies:");
            if (copiesStr == null || copiesStr.isEmpty()) return;

            Author author = new Author();
            author.setName(newAuthor);

            int copies = Integer.parseInt(copiesStr);
            Book updatedBook = new Book();
            updatedBook.setId(bookId);
            updatedBook.setTitle(newTitle);
            updatedBook.setAuthor(author);
            updatedBook.setAvailability(copies);
            controller.editBook(updatedBook);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID or number format.");
        }
    }

    private void deleteBook() {
        String idStr = JOptionPane.showInputDialog("Enter Book ID to delete:");
        if (idStr == null || idStr.isEmpty()) return;

        try {
            int bookId = Integer.parseInt(idStr);
            controller.deleteBook(bookId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID format.");
        }
    }
}
