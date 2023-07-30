package ru.gb.fitnessclub.authservice.integrations;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.fitnessclub.authservice.api.ClientInfoRequest;
import ru.gb.fitnessclub.authservice.api.RegistrationUserDto;

@Component
@RequiredArgsConstructor
public class AccountServiceIntegration {

    private final WebClient accountServiceWebClient;

    public void createAccount(ClientInfoRequest clientInfo){
        accountServiceWebClient.post()
                .uri("/api/v1/clients//accounts/info/update")
                .body(clientInfo,ClientInfoRequest.class)
                .retrieve()
                .toBodilessEntity()
                .block();

    }


}
