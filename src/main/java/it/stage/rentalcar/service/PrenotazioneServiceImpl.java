package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.mapper.PrenotazioneMapper;
import it.stage.rentalcar.repository.PrenotazioneRepository;
import it.stage.rentalcar.util.DateUtil;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteService utenteService;
    private final AutoService autoService;

    private final PrenotazioneMapper prenotazioneMapper;

    public PrenotazioneServiceImpl(PrenotazioneRepository prenotazioneRepository, UtenteService utenteService, AutoService autoService,
                                   PrenotazioneMapper prenotazioneMapper) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteService = utenteService;
        this.autoService = autoService;
        this.prenotazioneMapper=prenotazioneMapper;
    }

    @Override
    public List<Prenotazione> getReservationsForUser(int id) {
        return prenotazioneRepository.getReservationsForUser(id);
    }
    @Override
    public Prenotazione getReservationFromId(int id) { return prenotazioneRepository.getReservationFromId(id); }
    @Override
    public List<Auto> getReservableCars(LocalDate inizio, LocalDate fine) {
        List<Prenotazione> reservations = prenotazioneRepository.getReservationsBetweenDates(inizio, fine);
        List<Auto> reservedCars = autoService.getFreeCars(reservations);
        return reservedCars;
    }

    @Override
    public Prenotazione checkDate(PrenotazioneDTO prenotazioneDTO) throws Exception {
        LocalDate inizio = DateUtil.parseDate(prenotazioneDTO.getDataInizio());
        LocalDate fine = DateUtil.parseDate(prenotazioneDTO.getDataFine());
        if(fine.isBefore(inizio) || inizio.isBefore(LocalDate.now())){
            throw new Exception("Date invalide.");
        } else {
            return prenotazioneMapper.fromDTOtoEntity(prenotazioneDTO);
        }
    }

    @Override
    public boolean checkModificable(Prenotazione p) {
        if(p.getDataInizio().minusDays(2).isAfter(LocalDate.now())){
            System.out.println(p.getDataInizio().minusDays(2).isAfter(LocalDate.now()));
            return true;
        } else {
            System.out.println(p.getDataInizio().minusDays(2).isAfter(LocalDate.now()));
            return false;
        }
    }

    @Override
    public List<Prenotazione> filter(String field, String value) throws ParseException {
        return prenotazioneRepository.filter(field, value);
    }

    @Override
    public void insOrUpReservation(Prenotazione p){
        prenotazioneRepository.insOrUpReservation(p);
    }

    @Override
    public void updateStatus(boolean valid, int id) {
        prenotazioneRepository.updateStatus(valid, id);
    }

    @Override
    public void delReservation(int id) {
        prenotazioneRepository.delReservation(id);
    }
}
