package it.stage.rentalcar.service;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.mapper.PrenotazioneMapper;
import it.stage.rentalcar.repository.PrenotazioneRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final UtenteService utenteService;
    private final AutoService autoService;

    public PrenotazioneServiceImpl(PrenotazioneRepository prenotazioneRepository, UtenteService utenteService, AutoService autoService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.utenteService = utenteService;
        this.autoService = autoService;
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
    public Map<String, Date> parseDate(PrenotazioneDTO prenotazioneDTO) throws ParseException {
        Map<String, Date> dates = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date inizio = sdf.parse(prenotazioneDTO.getDataInizio());
        Date fine = sdf.parse(prenotazioneDTO.getDataFine());
        dates.put("inizio", inizio);
        dates.put("fine", fine);
        return dates;
    }
    @Override
    public Prenotazione checkDate(PrenotazioneDTO prenotazioneDTO) throws Exception {
        Date now = new Date();
        Map<String, Date> dates = parseDate(prenotazioneDTO);
        if(dates.get("fine").before(dates.get("inizio")) || dates.get("inizio").before(now)){
            throw new Exception("Date invalide.");
        } else {
            PrenotazioneMapper prenotazioneMapper = new PrenotazioneMapper(utenteService, autoService);
            return prenotazioneMapper.fromDTOtoEntity(prenotazioneDTO);
        }
    }

    @Override
    public boolean checkModificable(Prenotazione p) {
        Date now = new Date();
        long diffInMillies = Math.abs(p.getDataInizio().getTime() - now.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if(diff==0){
            return false;
        } else {
            return true;
        }
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
