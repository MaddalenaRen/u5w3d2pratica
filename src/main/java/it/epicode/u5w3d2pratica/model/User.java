package it.epicode.u5w3d2pratica.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = " users")
public class User {
    @GeneratedValue
    @Id
    private int id;
    private String nome;
    private String cognome;
    @Column(unique = true)
    private String email;
    private String password;

}
