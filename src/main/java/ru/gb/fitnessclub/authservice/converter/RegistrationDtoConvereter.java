package ru.gb.fitnessclub.authservice.converter;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.fitnessclub.authservice.api.ClientInfoRequest;
import ru.gb.fitnessclub.authservice.api.RegistrationUserDto;

@Component
@Data
@RequiredArgsConstructor
public class RegistrationDtoConvereter {


    public ClientInfoRequest registrationUserDtoToClientInfoRequest(RegistrationUserDto userDto){
        return ClientInfoRequest.getBuilder()
                .login(userDto.getUsername())
                .username(userDto.getRealName())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .build();

    }

}
