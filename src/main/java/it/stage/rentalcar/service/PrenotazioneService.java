package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;

import java.util.List;

public interface PrenotazioneService {
    List<Prenotazione> reservationsList(int id);
    void insOrUpReservation(Prenotazione p);
}
