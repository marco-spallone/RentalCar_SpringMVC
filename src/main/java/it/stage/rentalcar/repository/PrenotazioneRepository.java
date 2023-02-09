package it.stage.rentalcar.repository;

import it.stage.rentalcar.domain.Prenotazione;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PrenotazioneRepository {
    List<Prenotazione> getReservationsForUser(int id);
    Prenotazione getReservationFromId(int id);
    List<Prenotazione> getReservationsBetweenDates(LocalDate inizio, LocalDate fine);
    List<Prenotazione> filter(String field, String value) throws ParseException;
    void insOrUpReservation(Prenotazione p);
    void updateStatus(boolean valid, int id);
    void delReservation(int id);
}
