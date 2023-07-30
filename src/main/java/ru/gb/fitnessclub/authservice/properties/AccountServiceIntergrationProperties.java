package ru.gb.fitnessclub.authservice.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(prefix = "integration.account-service")
@Data
public class AccountServiceIntergrationProperties {
    private String url;
    private Integer connectionTimeout;
    private Integer readTimeout;
    private Integer writeTimeout;

}
