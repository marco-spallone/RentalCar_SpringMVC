package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.dto.PrenotazioneDTO;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneService {
    List<Prenotazione> getReservationsForUser(int id);
    Prenotazione getReservationFromId(int id);
    List<Auto> getReservableCars(LocalDate inizio, LocalDate fine);
    List<Prenotazione> filter(String field, String value) throws ParseException;
    Prenotazione checkDate(PrenotazioneDTO prenotazioneDTO) throws Exception;
    boolean checkModificable(Prenotazione p) throws Exception;
    void insOrUpReservation(Prenotazione p);
    void updateStatus(boolean valid, int id);
    void delReservation(int id);
}
