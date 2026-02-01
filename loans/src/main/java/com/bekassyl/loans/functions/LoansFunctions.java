package com.bekassyl.loans.functions;

import com.bekassyl.loans.service.ILoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class LoansFunctions {
    @Bean
    public Consumer<String> updateCommunication(ILoanService loanService) {
        return loanInfo -> {
            String[] parts = loanInfo.split("/");

            log.info("Updating communication status for the loan: {}", "ISBN: " + parts[0] + " - IIN: " + parts[1]);

            loanService.updateCommunicationStatus(parts[0], parts[1]);
        };
    }
}
