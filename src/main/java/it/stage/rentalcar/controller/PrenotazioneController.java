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

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
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
                                   HttpSession session, Model model){
        List<Prenotazione> reservations = prenotazioneService.getReservationsForUser(id);
        model.addAttribute("reservations", reservations);
        model.addAttribute("isAdmin", isAdmin);
        session.setAttribute("myid", myid);
        session.setAttribute("id", id);
        session.setAttribute("isAdmin", isAdmin);
        return "reservations";
    }

    @RequestMapping(value = "/filterReservations", method = RequestMethod.POST)
    public String filter(@RequestParam("field") String field, @RequestParam("value") String value, Model model) throws ParseException {
        List<Prenotazione> reservations = prenotazioneService.filter(field, value);
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.GET)
    public String addReservation(HttpSession session, Model model){
        PrenotazioneDTO p = new PrenotazioneDTO();
        Utente u = utenteService.getUserFromId(Integer.parseInt(session.getAttribute("myid").toString()));
        model.addAttribute("newReservation", p);
        model.addAttribute("myid", session.getAttribute("myid"));
        model.addAttribute("utente", u);
        return "reservationForm";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.POST)
    public String getFreeCars(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("newReservation", prenotazioneDTO);
        return "redirect:/freeAuto";
    }

    @RequestMapping(value = "/freeAuto", method = RequestMethod.GET)
    public String chooseCar(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, Model model) throws ParseException {
        List<Prenotazione> confirmed = prenotazioneService.getReservationsBetweenDates(prenotazioneService.parseDate(prenotazioneDTO).get("inizio"), prenotazioneService.parseDate(prenotazioneDTO).get("fine"));
        List<Auto> freeCars = autoService.getFreeCars(confirmed);
        model.addAttribute("auto", freeCars);
        model.addAttribute("newReservation", prenotazioneDTO);
        return "selectCar";
    }

    @RequestMapping(value = "/freeAuto", method = RequestMethod.POST)
    public String insReservation(HttpSession session, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        prenotazioneService.insOrUpReservation(prenotazioneService.checkDate(prenotazioneDTO));
        return "redirect:/viewReservations?isAdmin=false&id="+session.getAttribute("myid")+"&myid="+session.getAttribute("myid");
    }

    @RequestMapping(value = "/editReservation", method = RequestMethod.GET)
    public String editReservation(HttpSession session, @RequestParam("id") int id, Model model) throws Exception {
        Prenotazione actualP = prenotazioneService.getReservationFromId(id);
        if(prenotazioneService.checkModificable(actualP)){
            PrenotazioneDTO newP = new PrenotazioneDTO();
            model.addAttribute("newReservation", newP);
            model.addAttribute("actualReservation", actualP);
            model.addAttribute("myid", session.getAttribute("myid"));
            model.addAttribute("id", id);
            return "reservationForm";
        } else {
            throw new Exception("Non è possibile modificare la prenotazione: mancano meno di due giorni alla data di inizio.");
        }
    }

    @RequestMapping(value = "editReservation", method = RequestMethod.POST)
    public String updateReservation(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, RedirectAttributes redirectAttributes){
        System.out.println(prenotazioneDTO.getId());
        redirectAttributes.addFlashAttribute("newReservation", prenotazioneDTO);
        return "redirect:/freeAuto";
    }

    @RequestMapping(value = "approveReservation", method = RequestMethod.GET)
    public String approveReservation(HttpSession session, @RequestParam("id") int id){
        prenotazioneService.updateStatus(true, id);
        return "redirect:/viewReservations?isAdmin=true&myid="+session.getAttribute("myid")+"&id="+session.getAttribute("id");
    }

    @RequestMapping(value = "declineReservation", method = RequestMethod.GET)
    public String declineReservation(HttpSession session, @RequestParam("id") int idPren){
        prenotazioneService.updateStatus(false, idPren);
        return "redirect:/viewReservations?isAdmin=true&myid="+session.getAttribute("myid")+"&id="+session.getAttribute("id");
    }

    @RequestMapping(value = "deleteReservation", method = RequestMethod.GET)
    public String deleteReservation(HttpSession session, @RequestParam("id") int id){
        prenotazioneService.delReservation(id);
        return "redirect:/viewReservations?isAdmin=false&myid="+session.getAttribute("myid")+"&id="+session.getAttribute("myid");
    }
}
