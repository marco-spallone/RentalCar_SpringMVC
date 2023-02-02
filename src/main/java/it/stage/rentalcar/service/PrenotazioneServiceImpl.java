package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
    @Override
    public List<Prenotazione> reservationsList(int id) {
        return prenotazioneRepository.reservationsList(id);
    }
    @Override
    public void insOrUpReservation(Prenotazione p){
        prenotazioneRepository.insOrUpReservation(p);
    }
}
