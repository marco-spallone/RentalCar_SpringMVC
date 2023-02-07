package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.dto.PrenotazioneDTO;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PrenotazioneService {
    List<Prenotazione> getReservationsForUser(int id);
    Prenotazione getReservationFromId(int id);
    List<Prenotazione> getReservationsBetweenDates(Date inizio, Date fine);
    List<Prenotazione> filter(String field, String value) throws ParseException;
    Map<String, Date> parseDate(PrenotazioneDTO prenotazioneDTO) throws ParseException;
    Prenotazione checkDate(PrenotazioneDTO prenotazioneDTO) throws Exception;
    boolean checkModificable(Prenotazione p) throws Exception;
    void insOrUpReservation(Prenotazione p);
    void updateStatus(boolean valid, int id);
    void delReservation(int id);
}
