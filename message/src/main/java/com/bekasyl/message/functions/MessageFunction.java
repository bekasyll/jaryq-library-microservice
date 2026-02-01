package com.bekasyl.message.functions;

import com.bekasyl.message.dto.LoanMsgDto;
import com.bekasyl.message.dto.MemberMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Slf4j
@Configuration
public class MessageFunction {

    @Bean
    public Function<MemberMsgDto, String> sendMemberCreated() {
        return memberMsgDto -> {
            log.info("Sending an SMS to the member about the creation of an account with details: {}", memberMsgDto.toString());

            return memberMsgDto.memberIin();
        };
    }

    @Bean
    public Function<LoanMsgDto, String> sendLoanCreated() {
        return loanMsgDto -> {
            log.info("Sending an SMS to the member about the processing of the loan with details: {}", loanMsgDto.toString());

            return String.format("%s/%s",  loanMsgDto.bookIsbn(), loanMsgDto.memberIin());
        };
    }
}
