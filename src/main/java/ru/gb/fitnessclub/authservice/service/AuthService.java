package ru.gb.fitnessclub.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gb.fitnessclub.authservice.api.AppError;
import ru.gb.fitnessclub.authservice.api.JwtRequest;
import ru.gb.fitnessclub.authservice.api.JwtResponse;
import ru.gb.fitnessclub.authservice.api.RegistrationUserDto;
import ru.gb.fitnessclub.authservice.converter.RegistrationDtoConvereter;
import ru.gb.fitnessclub.authservice.entities.User;
import ru.gb.fitnessclub.authservice.exception.AuthException;
import ru.gb.fitnessclub.authservice.exception.RegistrationException;
import ru.gb.fitnessclub.authservice.integrations.AccountServiceIntegration;
import ru.gb.fitnessclub.authservice.utils.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AccountServiceIntegration accountService;
    private final RegistrationDtoConvereter regConvereter;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthException("Введено не верное имя или пароль");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Transactional
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new RegistrationException("Пароли не совпадают");
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            throw new RegistrationException("Пользователь с таким именем уже существует");
        }

        accountService.createAccount(regConvereter.registrationUserDtoToClientInfoRequest(registrationUserDto));

        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        userService.createUser(user);

        UserDetails userDetails = userService.loadUserByUsername(registrationUserDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
