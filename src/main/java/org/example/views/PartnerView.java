package org.example.views;

import org.example.controllers.PartnerController;
import org.example.domain.Book;
import org.example.domain.Loan;
import org.example.domain.Partner;

import javax.swing.*;
import java.util.List;

public class PartnerView {
    private final PartnerController partnerController;
    private final Partner loggedPartner;

    public PartnerView(PartnerController controller, Partner loggedPartner) {
        this.partnerController = controller;
        this.loggedPartner = loggedPartner;
    }

    public void showMenu() {
        String[] options = {"View All Books", "Loan a Book", "View Active Loans", "Return a Book", "Logout"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "Welcome, " + loggedPartner.getName(),
                    "Partner Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> viewAllBooks();
                case 1 -> loanBook();
                case 2 -> viewActiveLoans();
                case 3 -> returnLoan();
                case 4 -> JOptionPane.showMessageDialog(null, "Logging out...");
                default -> JOptionPane.showMessageDialog(null, "Invalid option");
            }
        } while (choice != 4 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void viewAllBooks() {
        List<Book> books = partnerController.viewAllBooks();
        // Los libros ya se muestran dentro del controller
    }

    private void loanBook() {

        List<Book> books = partnerController.viewAllBooks();
        String input = JOptionPane.showInputDialog("Enter Book ID to loan:");
        if (input == null || input.isEmpty()) return;

        try {
            int bookId = Integer.parseInt(input);

            Book book = books.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);

            if (book != null) {
                partnerController.loanBook(loggedPartner, book);
            } else {
                JOptionPane.showMessageDialog(null, "Book not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID format.");
        }
    }

    private void viewActiveLoans() {
        List<Loan> loans = partnerController.viewLoans(loggedPartner);
        if (loans.isEmpty()) return;

        StringBuilder sb = new StringBuilder("Active Loans:\n");
        for (Loan loan : loans) {
            sb.append("Loan ID: ").append(loan.getId())
                    .append(" | Book: ").append(loan.getBook().getTitle())
                    .append(" | Return Date: ").append(loan.getDate_return())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private void returnLoan() {

        List<Loan> loans = partnerController.viewLoans(loggedPartner);
        if (loans.isEmpty()) return;

        StringBuilder sb = new StringBuilder("Your Active Loans:\n\n");
        for (Loan loan : loans) {
            sb.append("Loan ID: ").append(loan.getId())
                    .append(" | Book: ").append(loan.getBook().getTitle())
                    .append(" | Loan Date: ").append(loan.getDate_loan())
                    .append(" | Return Date: ").append(loan.getDate_return())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());

        String input = JOptionPane.showInputDialog("Enter Loan ID to return:");
        if (input == null || input.isEmpty()) return;

        try {
            int loanId = Integer.parseInt(input);
            Loan loan = loans.stream().filter(l -> l.getId() == loanId).findFirst().orElse(null);

            if (loan != null) {
                partnerController.returnLoan(loan);
            } else {
                JOptionPane.showMessageDialog(null, "Loan not found.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid ID format.");
        }
    }
}
