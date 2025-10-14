package org.example;//

import org.example.config.ConfigDb;
import org.example.controllers.AuthController;
import org.example.services.UserService;
import org.example.services.LoanService;
import org.example.services.BookService;
import org.example.services.AuthorService;
import org.example.views.MainView;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}