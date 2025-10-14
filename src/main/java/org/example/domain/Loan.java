package org.example.domain;

import java.time.LocalDate;

public class Loan {
    private int id;
    private Book book;
    private Partner partner;
    private LocalDate date_loan;
    private LocalDate date_return;
    private boolean returned;

    public Loan() {}

    public Loan(int id, Book book, Partner partner, LocalDate date_return, LocalDate date_loan, boolean returned) {
        this.id = id;
        this.book = book;
        this.partner = partner;
        this.date_return = date_return;
        this.date_loan = date_loan;
        this.returned = returned;
    }

    public Loan(Book book, Partner partner, LocalDate date_loan, LocalDate date_return, boolean returned) {
        this.book = book;
        this.partner = partner;
        this.date_loan = date_loan;
        this.date_return = date_return;
        this.returned = returned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public LocalDate getDate_loan() {
        return date_loan;
    }

    public void setDate_loan(LocalDate date_loan) {
        this.date_loan = date_loan;
    }

    public LocalDate getDate_return() {
        return date_return;
    }

    public void setDate_return(LocalDate date_return) {
        this.date_return = date_return;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Loan" +
                "|| id" + id +
                "|| book=" + book.getTitle() +
                "|| partner=" + partner.getName() +
                "|| date_loan=" + date_loan +
                "|| date_return=" + date_return +
                "|| returned=" + (returned ? "Si" : "No");
    }
}
