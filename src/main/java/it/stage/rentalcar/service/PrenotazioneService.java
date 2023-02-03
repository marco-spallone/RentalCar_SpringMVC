package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Prenotazione;

import java.util.List;

public interface PrenotazioneService {
    List<Prenotazione> getReservations(int id);
    Prenotazione getReservationFromId(int id);
    void insOrUpReservation(Prenotazione p);
    void delReservation(int id);
}
