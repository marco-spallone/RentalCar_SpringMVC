package it.stage.rentalcar.mapper;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.service.AutoService;
import it.stage.rentalcar.service.AutoServiceImpl;
import it.stage.rentalcar.service.UtenteService;
import it.stage.rentalcar.service.UtenteServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class PrenotazioneMapper {

    private UtenteService utenteService = new UtenteServiceImpl();
    private AutoService autoService = new AutoServiceImpl();

    public PrenotazioneMapper(UtenteService u, AutoService a) {
        this.utenteService = u;
        this.autoService = a;
    }

    public Prenotazione fromDTOtoEntity(PrenotazioneDTO prenotazioneDTO) throws ParseException {
        Utente utente = utenteService.getUserFromId(prenotazioneDTO.getIdUtente());
        Auto auto = autoService.getAutoFromId(prenotazioneDTO.getIdAuto());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return new Prenotazione(
                prenotazioneDTO.getId(),
                sdf.parse(prenotazioneDTO.getDataInizio()),
                sdf.parse(prenotazioneDTO.getDataFine()),
                prenotazioneDTO.isApprovata(),
                utente,
                auto);
    }

}
