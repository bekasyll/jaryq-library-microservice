package com.bekassyl.members.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "members")
public class MembersInfoDto {
    private String buildVersion;
    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
