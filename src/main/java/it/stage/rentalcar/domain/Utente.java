package it.stage.rentalcar.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="utente")
public class Utente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente")
    private int idUtente;

    @Column(name="isAdmin")
    private boolean isAdmin;

    @Column(name="nome")
    private String nome;

    @Column(name="cognome")
    private String cognome;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "utente", orphanRemoval = true)
    private List<Prenotazione> prenotazione = new ArrayList<>();

    public Utente() {

    }

    public Utente(boolean isCustomer, String nome, String cognome, String username, String password){
        this.isAdmin = isCustomer;
        this.nome=nome;
        this.cognome=cognome;
        this.username=username;
        this.password=password;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Prenotazione> getPrenotazione() {
        return prenotazione;
    }

    public void setPrenotazione(List<Prenotazione> prenotazione) {
        this.prenotazione = prenotazione;
    }
}

