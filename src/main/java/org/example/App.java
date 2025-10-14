package org.example;

import org.example.controllers.AuthController;
import org.example.dao.Impl.*;
import org.example.dao.RolDao;
import org.example.services.*;
import org.example.views.MainView;

public class App {

    private final AuthController authController;
    private final LoanService loanService;
    private final BookService bookService;
    private final UserService userService;
    private final MainView mainView;

    public App() {
        // 1. Crear RolDao y UserService
        RolDao rolDao = new RolDaoImpl();
        this.userService = new UserService(new UserDaoImpl(), rolDao);

        // 2. Crear el AuthorService (BookService lo necesita)
        AuthorService authorService = new AuthorService(new AuthorDaoImpl());

        // 3. Crear el BookService con BookDao Y AuthorService
        this.bookService = new BookService(new BookDaoImpl(), authorService);

        // 4. Crear el LoanService con LoanDao Y BookService
        this.loanService = new LoanService(new LoanDaoImpl(), bookService);

        // 5. Crear el AuthController con el userService
        this.authController = new AuthController(userService);

        // 6. Crear la vista principal con TODOS los servicios
        this.mainView = new MainView(authController, loanService, bookService, userService);
    }

    public void start() {
        // 7. Mostrar el menú principal
        mainView.showMenu();
    }
}