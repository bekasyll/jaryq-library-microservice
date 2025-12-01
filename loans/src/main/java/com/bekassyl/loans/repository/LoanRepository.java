package com.bekassyl.loans.repository;

import com.bekassyl.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBookIsbn(String bookIsbn);

    List<Loan> findByMemberIin(String memberIin);

    Loan findByBookIsbnAndMemberIinAndStatus(String bookIsbn, String memberIin, Loan.LoanStatus status);

    boolean existsByBookIsbnAndMemberIinAndStatus(String bookIsbn, String memberIin, Loan.LoanStatus status);
}
