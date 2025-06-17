package it.epicode.u5w3d2pratica.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DipendenteDto {


    @NotEmpty(message = "Lo username non può essere nullo o vuoto")
    private String username;

    @NotEmpty(message = "Il nome non può essere nullo o vuoto")
    private String nome;

    @NotEmpty(message = "Il cognome non può essere nullo o vuoto")
    private String cognome;

    @Email(message = "Formato email non valido")
    @NotEmpty(message = "L'email non può essere nulla o vuota")
    private String email;


}
