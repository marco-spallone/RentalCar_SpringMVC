package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Prenotazione;

import java.util.Date;
import java.util.List;

public interface PrenotazioneService {
    List<Prenotazione> getReservationsForUser(int id);
    Prenotazione getReservationFromId(int id);
    List<Prenotazione> getReservationsBetweenDates(Date inizio, Date fine);
    void insOrUpReservation(Prenotazione p);
    void delReservation(int id);
}
