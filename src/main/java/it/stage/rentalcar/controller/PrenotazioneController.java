package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.service.AutoService;
import it.stage.rentalcar.service.PrenotazioneService;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;

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
        List<Prenotazione> reservations = prenotazioneService.getReservationsForUser(id);
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
        return "reservationForm";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.POST)
    public String getFreeCars(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("newReservation", prenotazioneDTO);
        return "redirect:/freeAuto?myid="+myid;
    }

    @RequestMapping(value = "/freeAuto", method = RequestMethod.GET)
    public String chooseCar(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, Model model) throws ParseException {
        List<Prenotazione> confirmed = prenotazioneService.getReservationsBetweenDates(prenotazioneService.parseDate(prenotazioneDTO).get("inizio"), prenotazioneService.parseDate(prenotazioneDTO).get("fine"));
        List<Auto> freeCars = autoService.getFreeCars(confirmed);
        model.addAttribute("auto", freeCars);
        model.addAttribute("myid", myid);
        model.addAttribute("newReservation", prenotazioneDTO);
        return "selectCar";
    }

    @RequestMapping(value = "/freeAuto", method = RequestMethod.POST)
    public String insReservation(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        prenotazioneService.insOrUpReservation(prenotazioneService.checkDate(prenotazioneDTO));
        return "redirect:/viewReservations?isAdmin=false&id="+myid+"&myid="+myid;
    }

    @RequestMapping(value = "/editReservation", method = RequestMethod.GET)
    public String editReservation(@RequestParam("myid") int myid, @RequestParam("id") int id, Model model) throws Exception {
        Prenotazione actualP = prenotazioneService.getReservationFromId(id);
        if(prenotazioneService.checkModificable(actualP)){
            PrenotazioneDTO newP = new PrenotazioneDTO();
            model.addAttribute("newReservation", newP);
            model.addAttribute("actualReservation", actualP);
            model.addAttribute("myid", myid);
            model.addAttribute("id", id);
            return "reservationForm";
        } else {
            throw new Exception("Non è possibile modificare la prenotazione: mancano meno di due giorni alla data di inizio.");
        }
    }

    @RequestMapping(value = "editReservation", method = RequestMethod.POST)
    public String updateReservation(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        prenotazioneService.insOrUpReservation(prenotazioneService.checkDate(prenotazioneDTO));
        return "redirect:/viewReservations?isAdmin=false&id="+myid+"&myid="+myid;
    }

    @RequestMapping(value = "deleteReservation", method = RequestMethod.GET)
    public String deleteReservation(@RequestParam("myid") int myid, @RequestParam("id") int id){
        prenotazioneService.delReservation(id);
        return "redirect:/viewReservations?isAdmin=false&myid="+myid+"&id="+myid;
    }
}
