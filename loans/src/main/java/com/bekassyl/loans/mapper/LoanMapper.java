package com.bekassyl.loans.mapper;

import com.bekassyl.loans.dto.LoanDto;
import com.bekassyl.loans.entity.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDto toDto(Loan loan);
}
