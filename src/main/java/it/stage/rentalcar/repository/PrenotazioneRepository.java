package it.stage.rentalcar.repository;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;

import java.util.List;

public interface PrenotazioneRepository {
    List<Prenotazione> reservationsList(int id);
    void insOrUpReservation(Prenotazione p);
}
