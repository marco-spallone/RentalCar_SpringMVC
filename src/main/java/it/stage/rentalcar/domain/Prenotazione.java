package it.stage.rentalcar.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="prenotazione")
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_prenotazione")
    private int idPrenotazione;

    @Column(name="data_inizio")
    private Date dataInizio;

    @Column(name="data_fine")
    private Date dataFine;

    @Column(name="confermata")
    private boolean confermata;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="id_utente", referencedColumnName = "id_utente", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name="id_auto", referencedColumnName = "id_auto")
    private Auto auto = new Auto();

    public Prenotazione() {
    }

    public Prenotazione(int idPrenotazione, Date dataInizio, Date dataFine, boolean confermata, Utente utente, Auto auto) {
        this.idPrenotazione = idPrenotazione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.confermata = confermata;
        this.utente = utente;
        this.auto = auto;
    }

    public int getIdPrenotazione() {
        return idPrenotazione;
    }

    public void setIdPrenotazione(int idPrenotazione) {
        this.idPrenotazione = idPrenotazione;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public boolean isConfermata() {
        return confermata;
    }

    public void setConfermata(boolean confermata) {
        this.confermata = confermata;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }
}
