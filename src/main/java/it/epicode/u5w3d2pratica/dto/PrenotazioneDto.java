package it.epicode.u5w3d2pratica.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PrenotazioneDto {
@NotNull(message = "la data non può essere nulla")
    private LocalDate dataRichiesta;
    private String note;
    private String preferenze;

    @NotNull(message = "L'id del dipendente non può essere nullo ")
    private Integer dipendenteId;

    @NotNull(message = "L'id del viaggio non può essere nullp")
    private Integer viaggioId;
}
