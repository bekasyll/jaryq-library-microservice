package com.bekassyl.loans.mapper;

import com.bekassyl.loans.dto.request.LoanRequestDto;
import com.bekassyl.loans.dto.LoanDto;
import com.bekassyl.loans.entity.Loan;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toEntityFromRequest(LoanRequestDto loanRequestDto);
    LoanDto toDto(Loan loan);
    List<LoanDto> toDtoList(List<Loan> loans);
}
