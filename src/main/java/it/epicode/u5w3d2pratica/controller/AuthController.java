package it.epicode.u5w3d2pratica.controller;

import it.epicode.u5w3d2pratica.dto.LoginDto;
import it.epicode.u5w3d2pratica.dto.UserDto;
import it.epicode.u5w3d2pratica.exception.NotFoundException;
import it.epicode.u5w3d2pratica.exception.ValidationException;
import it.epicode.u5w3d2pratica.model.User;
import it.epicode.u5w3d2pratica.service.AuthService;
import it.epicode.u5w3d2pratica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError ->objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return userService.saveUser(userDto);

    }

    @GetMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().
                    map(objectError ->objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }

        //1. verificare che utente esiste
        //2. se non esiste lancia un'eccezione
        //3. se esiste dovrà generare il token
        //4. e inviarlo al client
        return authService.login(loginDto);


    }
}