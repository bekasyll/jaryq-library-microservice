package com.bekassyl.members.functions;

import com.bekassyl.members.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class MembersFunctions {
    @Bean
    public Consumer<String> updateCommunication(IMemberService memberService) {
        return memberIin -> {
            log.info("Updating communication status for the member: {}", memberIin);

            memberService.updateCommunicationStatus(memberIin);
        };
    }
}
