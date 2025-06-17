package it.epicode.u5w3d2pratica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.u5w3d2pratica.enumaration.StatoViaggio;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private StatoViaggio statoViaggio;

    @JsonIgnore
    @OneToMany(mappedBy = "viaggio")
    private List<Prenotazione> prenotazioni;

}

