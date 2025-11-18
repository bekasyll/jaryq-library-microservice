package com.bekassyl.loans.repository;

import com.bekassyl.loans.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByBookId(Long bookId);
    List<Loan> findByMemberId(Long memberId);
    Optional<Loan> findByIdAndStatusNot(Long id, Loan.LoanStatus status);
    boolean existsByBookIdAndStatusNot(Long bookId, Loan.LoanStatus status);
}
