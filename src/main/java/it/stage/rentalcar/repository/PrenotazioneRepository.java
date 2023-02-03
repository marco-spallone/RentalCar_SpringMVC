package it.stage.rentalcar.repository;

import it.stage.rentalcar.domain.Prenotazione;

import java.util.List;

public interface PrenotazioneRepository {
    List<Prenotazione> getReservations(int id);
    Prenotazione getReservationFromId(int id);
    void insOrUpReservation(Prenotazione p);
    void delReservation(int id);
}
