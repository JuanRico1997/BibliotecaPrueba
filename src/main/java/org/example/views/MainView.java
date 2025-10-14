package org.example.views;

import org.example.controllers.AuthController;
import org.example.controllers.PartnerController;
import org.example.controllers.UserController;
import org.example.dao.Impl.UserDaoImpl;
import org.example.domain.Partner;
import org.example.domain.User;
import org.example.domain.Users;
import org.example.services.BookService;
import org.example.services.LoanService;
import org.example.services.UserService;

import javax.swing.*;

public class MainView {

    private final AuthController authController;
    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;

    // Constructor principal
    public MainView(AuthController authController, LoanService loanService,
                    BookService bookService, UserService userService) {
        this.authController = authController;
        this.loanService = loanService;
        this.bookService = bookService;
        this.userService = userService;
    }

    // Mostrar menú principal
    public void showMenu() {
        String[] options = {"Register", "Login", "Exit"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to the Library System",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> showRegister();
                case 1 -> showLogin();
                case 2 -> JOptionPane.showMessageDialog(null, "Exiting...");
                default -> JOptionPane.showMessageDialog(null, "Invalid option");
            }
        } while (choice != 2 && choice != JOptionPane.CLOSED_OPTION);
    }

    // Registro de usuario o partner
    private void showRegister() {
        String name = JOptionPane.showInputDialog("Enter your name:");
        if (name == null || name.isEmpty()) return;

        String email = JOptionPane.showInputDialog("Enter your email:");
        if (email == null || email.isEmpty()) return;

        String password = JOptionPane.showInputDialog("Enter your password:");
        if (password == null || password.isEmpty()) return;

        String[] roles = {"User", "Partner"};
        int roleChoice = JOptionPane.showOptionDialog(
                null,
                "Select role",
                "Register",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                roles,
                roles[0]
        );

        authController.register(name, email, password, roleChoice == 0 ? "User" : "Partner");
    }

    // Login de usuario o partner
    private void showLogin() {
        String email = JOptionPane.showInputDialog("Enter your email:");
        if (email == null || email.isEmpty()) return;

        String password = JOptionPane.showInputDialog("Enter your password:");
        if (password == null || password.isEmpty()) return;

        Users logged = authController.login(email, password);

        if (logged == null) {
            JOptionPane.showMessageDialog(null, "Login failed. Check email or password.");
            return;
        }

        // Redirigir según el tipo de usuario
        if (logged instanceof Partner partner) {
            PartnerController partnerController = new PartnerController(loanService, bookService);
            PartnerView partnerView = new PartnerView(partnerController, partner);
            partnerView.showMenu();
        } else if (logged instanceof User user) {
            UserController userController = new UserController(bookService, loanService, userService);
            UserView userView = new UserView(userController);
            userView.showMenu();
        }
    }
}
