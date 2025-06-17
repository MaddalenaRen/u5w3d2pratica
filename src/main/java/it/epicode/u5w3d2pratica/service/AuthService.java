package it.epicode.u5w3d2pratica.service;


import it.epicode.u5w3d1pratica.dto.LoginDto;
import it.epicode.u5w3d1pratica.exception.NotFoundException;
import it.epicode.u5w3d1pratica.model.User;
import it.epicode.u5w3d1pratica.repository.UserRepository;
import it.epicode.u5w3d1pratica.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    public String login(LoginDto loginDto) throws NotFoundException {
        User user= userRepository.findByEmail(loginDto.getEmail()).
                orElseThrow(() -> new NotFoundException("utente con email: " + loginDto.getEmail() + " non trovato"));

        if(loginDto.getPassword().equals(user.getPassword())){
            //utente autenticato, creo token
            return jwtTool.createToken(user);
        }else {
            throw new NotFoundException("utente con questo email/password non trovato");
        }
    }
}
