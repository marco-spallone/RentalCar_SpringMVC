package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.PrenotazioneService;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;
    public PrenotazioneController(PrenotazioneService prenotazioneService, UtenteService utenteService){
        this.prenotazioneService=prenotazioneService;
        this.utenteService = utenteService;
    }

    @RequestMapping(value = "/viewReservations", method = RequestMethod.GET)
    public String viewReservations(@RequestParam("isAdmin") boolean isAdmin, @RequestParam("id") int id, Model model){
        List<Prenotazione> reservations = prenotazioneService.reservationsList(id);
        Prenotazione p = new Prenotazione();
        model.addAttribute("reservations", reservations);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("id", id);
        model.addAttribute("reservation", p);
        return "reservations";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.GET)
    public String addReservation(@RequestParam("id") int id, @RequestParam("isAdmin") boolean isAdmin, Model model){
        Prenotazione p = new Prenotazione();
        Utente u = utenteService.getUserFromId(id);
        model.addAttribute("newReservation", p);
        model.addAttribute("utente", u);
        return "addReservation";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.POST)
    public String addReservation(@ModelAttribute("newReservation") Prenotazione prenotazione){
        prenotazioneService.insOrUpReservation(prenotazione);
        return "redirect:/viewReservations?isAdmin="+prenotazione.getUtente().getIsAdmin()+"&id="+prenotazione.getUtente().getIdUtente();
    }
}
