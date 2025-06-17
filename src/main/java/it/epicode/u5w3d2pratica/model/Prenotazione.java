package it.epicode.u5w3d2pratica.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"dipendente_id", "dataRichiesta"}))
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dataRichiesta;
    private String note;
    private String preferenze;

    @ManyToOne
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;


    @ManyToOne
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;
}
