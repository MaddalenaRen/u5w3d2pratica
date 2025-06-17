package it.epicode.u5w3d2pratica.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.u5w3d2pratica.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {
    @Value("${jwt.duration}")
    private long durata;
    @Value("${jwt.secret}")
    private String chiaveSegreta;

    public String createToken(User user){
        //per generare il token avremo bisogno della data di generazione del token, della durata e dell'id
        //dell'utente per il quale stiamo creando il token. Avremo inoltre bisogno anche della chiave segreta
        //per poter crittografare il token
        return Jwts.builder().issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+durata))
                .subject(user.getId()+"").signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .compact();


    }

    //questo metodo verifica se il token è valido e che non sia scaduto
    //questo metodo lancia le eccezioni in  base alla situazione che si verfica
    public void ValidateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build().parse(token);
    }
}
