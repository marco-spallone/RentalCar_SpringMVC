package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.mapper.PrenotazioneMapper;
import it.stage.rentalcar.service.AutoService;
import it.stage.rentalcar.service.PrenotazioneService;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;
    private final AutoService autoService;
    public PrenotazioneController(PrenotazioneService prenotazioneService, UtenteService utenteService, AutoService autoService){
        this.prenotazioneService=prenotazioneService;
        this.utenteService=utenteService;
        this.autoService=autoService;
    }

    @RequestMapping(value = "/viewReservations", method = RequestMethod.GET)
    public String viewReservations(@RequestParam("isAdmin") boolean isAdmin, @RequestParam("id") int id, @RequestParam("myid") int myid,
                                   Model model){
        List<Prenotazione> reservations = prenotazioneService.reservationsList(id);
        model.addAttribute("reservations", reservations);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("myid", myid);
        return "reservations";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.GET)
    public String addReservation(@RequestParam("myid") int myid, Model model){
        PrenotazioneDTO p = new PrenotazioneDTO();
        Utente u = utenteService.getUserFromId(myid);
        model.addAttribute("newReservation", p);
        model.addAttribute("myid", myid);
        model.addAttribute("utente", u);
        return "addReservation";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.POST)
    public String insReservation(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws ParseException {
        PrenotazioneMapper prenotazioneMapper = new PrenotazioneMapper(utenteService, autoService);
        prenotazioneService.insOrUpReservation(prenotazioneMapper.fromDTOtoEntity(prenotazioneDTO));
        return "redirect:/viewReservations?isAdmin=false&id="+myid+"&myid="+myid;
    }
}
