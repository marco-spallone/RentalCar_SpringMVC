package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.service.AutoService;
import it.stage.rentalcar.service.PrenotazioneService;
import it.stage.rentalcar.service.UtenteService;
import it.stage.rentalcar.util.DateUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;
    private final AutoService autoService;
    public PrenotazioneController(PrenotazioneService prenotazioneService, UtenteService utenteService, AutoService autoService){
        this.prenotazioneService=prenotazioneService;
        this.utenteService=utenteService;
        this.autoService=autoService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewReservations(@RequestParam("id") int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        List<Prenotazione> reservations = prenotazioneService.getReservationsForUser(id);
        model.addAttribute("reservations", reservations);
        model.addAttribute("isAdmin", u.getIsAdmin());
        model.addAttribute("id", id);
        return "reservations";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam("field") String field, @RequestParam("value") String value, @RequestParam("id") int id, Model model) throws ParseException {
        List<Prenotazione> reservations = prenotazioneService.filter(field, value);
        model.addAttribute("reservations", reservations);
        model.addAttribute("id", id);
        return "reservations";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addReservation(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        PrenotazioneDTO p = new PrenotazioneDTO();
        model.addAttribute("newReservation", p);
        model.addAttribute("myid", u.getIdUtente());
        model.addAttribute("utente", u);
        return "reservationForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String getFreeCars(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("newReservation", prenotazioneDTO);
        return "redirect:/reservations/freeCars";
    }

    @RequestMapping(value = "/freeCars", method = RequestMethod.GET)
    public String chooseCar(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, Model model) throws ParseException {
        List<Auto> freeCars = prenotazioneService.getReservableCars(DateUtil.parseDate(prenotazioneDTO.getDataInizio()), DateUtil.parseDate(prenotazioneDTO.getDataFine()));
        model.addAttribute("auto", freeCars);
        model.addAttribute("newReservation", prenotazioneDTO);
        return "selectCar";
    }

    @RequestMapping(value = "/freeCars", method = RequestMethod.POST)
    public String insReservation(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.insOrUpReservation(prenotazioneService.checkDate(prenotazioneDTO));
        return "redirect:/reservations?id="+u.getIdUtente();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editReservation(@RequestParam("id") int id, Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        Prenotazione actualP = prenotazioneService.getReservationFromId(id);
        if(prenotazioneService.checkModificable(actualP)){
            PrenotazioneDTO newP = new PrenotazioneDTO();
            model.addAttribute("newReservation", newP);
            model.addAttribute("actualReservation", actualP);
            model.addAttribute("myid", u.getIdUtente());
            model.addAttribute("id", id);
            return "reservationForm";
        } else {
            throw new Exception("Non è possibile modificare la prenotazione: mancano meno di due giorni alla data di inizio.");
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String updateReservation(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, RedirectAttributes redirectAttributes){
        System.out.println(prenotazioneDTO.getId());
        redirectAttributes.addFlashAttribute("newReservation", prenotazioneDTO);
        return "redirect:/freeCars";
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public String approveReservation(@RequestParam("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.updateStatus(true, id);
        return "redirect:/reservations?id="+u.getIdUtente();
    }

    @RequestMapping(value = "/decline", method = RequestMethod.GET)
    public String declineReservation(@RequestParam("id") int idPren){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.updateStatus(false, idPren);
        return "redirect:/reservations?id="+u.getIdUtente();
    }

    @PostMapping(value = "/delete")
    public String deleteReservation(@RequestParam("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.delReservation(id);
        return "redirect:/reservations?id="+u.getIdUtente();
    }
}
