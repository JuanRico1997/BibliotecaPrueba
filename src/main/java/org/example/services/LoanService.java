package org.example.services;

import org.example.dao.Impl.LoanDaoImpl;
import org.example.dao.LoanDao;
import org.example.domain.Book;
import org.example.domain.Loan;
import org.example.domain.Partner;

import java.time.LocalDate;
import java.util.List;

public class LoanService {

    private final LoanDao loanDao;
    private final BookService bookService;
    private static final int DAYS_LOAN = 5;

    public LoanService(LoanDaoImpl loanDao, BookService bookService) {
        this.loanDao = loanDao;
        this.bookService = bookService;
    }

    public boolean createLoan(Partner partner , Book book){
        if (book == null || partner == null) return false;

        Book bookInDb = bookService.findBookById(book.getId());
        if (bookInDb == null || bookInDb.isAvailability() <= 0){
            System.out.println("El libro no esta disponible");
            return false;
        }

        LocalDate dateLoan = LocalDate.now();
        LocalDate dateReturn = dateLoan.plusDays(DAYS_LOAN);
        Loan loan = new Loan(0,bookInDb,partner,dateLoan,dateReturn,false);
        boolean create = loanDao.createLoan(loan);

        if (create){
            bookService.decreaseAvailability(bookInDb);
            return true;
        }
        return false;
    }

    public boolean returnLoan(int id_loan){
        boolean returned = loanDao.returnLoan(id_loan);
        if (!returned) return false;

        Loan loan = loanDao.findById(id_loan);
        if(loan != null && loan.getBook() != null){
            bookService.increaseAvailability(loan.getBook());
        }
        return true;
    }

    public List<Loan> listByActive(boolean active){
        return loanDao.findByAsset(active);

    }

    public List<Loan> listAll(){
        return loanDao.findAllLoan();
    }

}
