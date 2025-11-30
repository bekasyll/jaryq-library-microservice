package com.bekassyl.loans.repository;

import com.bekassyl.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByBookIsbn(String bookIsbn);
    List<Loan> findByMemberCardNumber(String memberCardNumber);
    Loan findByBookIsbnAndMemberCardNumberAndStatus(String bookIsbn, String memberCardNumber, Loan.LoanStatus status);

    boolean existsByBookIsbnAndMemberCardNumberAndStatus(String bookIsbn, String memberCardNumber, Loan.LoanStatus status);

    Optional<Loan> findByIdAndStatusNot(Long id, Loan.LoanStatus status);
}
