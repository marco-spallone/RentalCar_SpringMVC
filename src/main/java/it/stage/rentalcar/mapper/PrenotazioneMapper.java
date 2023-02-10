package it.stage.rentalcar.mapper;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.service.AutoService;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Component
public class PrenotazioneMapper {

    private final UtenteService utenteService;
    private final AutoService autoService;

    public PrenotazioneMapper(UtenteService u, AutoService a) {
        this.utenteService = u;
        this.autoService = a;
    }

    public Prenotazione fromDTOtoEntity(PrenotazioneDTO prenotazioneDTO) throws ParseException {
        Utente utente = utenteService.getUserFromId(prenotazioneDTO.getIdUtente());
        Auto auto = autoService.getCarFromId(prenotazioneDTO.getIdAuto());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new Prenotazione(
                prenotazioneDTO.getId(),
                LocalDate.parse(prenotazioneDTO.getDataInizio()),
                LocalDate.parse(prenotazioneDTO.getDataFine()),
                prenotazioneDTO.isApprovata(),
                utente,
                auto);
    }

}
