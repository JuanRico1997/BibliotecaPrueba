package org.example.dao;

import org.example.domain.Loan;

import java.util.List;

public interface LoanDao {
    boolean createLoan(Loan loan);
    List<Loan> findByAsset(boolean returned);
    List<Loan> findAllLoan();
    Loan findById(int id_loan);
    boolean returnLoan(int id_loan);

}
