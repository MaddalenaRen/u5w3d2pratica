package it.epicode.u5w3d2pratica.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty(message="il nome non può essere vuoto")
    private String nome;
    @NotEmpty(message="il cognome non può essere vuoto")
    private String cognome;
    @Email(message = "Inserisci un indirizzo email valido")
    @NotEmpty(message="email non può essere vuoto")
    private String email;
    @NotEmpty(message="password non può essere vuoto")
    private String password;
}
