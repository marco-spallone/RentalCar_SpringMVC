package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.repository.PrenotazioneRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;

    public PrenotazioneServiceImpl(PrenotazioneRepository prenotazioneRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
    }

    @Override
    public List<Prenotazione> getReservationsForUser(int id) {
        return prenotazioneRepository.getReservationsForUser(id);
    }
    @Override
    public Prenotazione getReservationFromId(int id) { return prenotazioneRepository.getReservationFromId(id); }

    @Override
    public List<Prenotazione> getReservationsBetweenDates(Date inizio, Date fine) {
        return prenotazioneRepository.getReservationsBetweenDates(inizio, fine);
    }

    @Override
    public void insOrUpReservation(Prenotazione p){
        prenotazioneRepository.insOrUpReservation(p);
    }
    @Override
    public void delReservation(int id) {
        prenotazioneRepository.delReservation(id);
    }
}
