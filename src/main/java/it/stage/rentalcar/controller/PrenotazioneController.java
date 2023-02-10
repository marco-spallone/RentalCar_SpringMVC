package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.service.PrenotazioneService;
import it.stage.rentalcar.service.UtenteService;
import it.stage.rentalcar.util.DateUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;
    public PrenotazioneController(PrenotazioneService prenotazioneService, UtenteService utenteService){
        this.prenotazioneService=prenotazioneService;
        this.utenteService=utenteService;
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewReservations(@RequestParam("id") int id, Model model){
        List<Prenotazione> reservations = prenotazioneService.getReservationsForUser(id);
        model.addAttribute("reservations", reservations);
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
    public String addReservation(@RequestParam(value = "id", required = false) Integer id, Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        PrenotazioneDTO p = new PrenotazioneDTO();
        if(id!=null){
            if(prenotazioneService.checkModificable(prenotazioneService.getReservationFromId(id))){
                model.addAttribute("id", id);
            } else {
                throw new Exception("Non è possibile modificare la prenotazione: mancano meno di due giorni alla data di inizio.");
            }
        }
        p.setIdUtente(u.getIdUtente());
        model.addAttribute("newReservation", p);
        return "reservationForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String getFreeCars(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO, Model model){
        return "redirect:/freeCars";
    }

    @RequestMapping(value = "/freeCars", method = RequestMethod.GET)
    public String chooseCar(PrenotazioneDTO prenotazioneDTO, Model model) {
        List<Auto> freeCars = prenotazioneService.getReservableCars(DateUtil.parseDate(prenotazioneDTO.getDataInizio()), DateUtil.parseDate(prenotazioneDTO.getDataFine()));
        model.addAttribute("auto", freeCars);
        model.addAttribute("newReservation", prenotazioneDTO);
        return "selectCar";
    }

    @PostMapping(value = "/insert")
    public String insReservation(@ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.insOrUpReservation(prenotazioneService.checkDate(prenotazioneDTO));
        return "redirect:/reservations?id="+u.getIdUtente();
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public String approveReservation(@RequestParam("idPren") int idPren, @RequestParam("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.updateStatus(true, idPren);
        return "redirect:/reservations?id="+id;
    }

    @RequestMapping(value = "/decline", method = RequestMethod.GET)
    public String declineReservation(@RequestParam("idPren") int idPren, @RequestParam("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.updateStatus(false, idPren);
        return "redirect:/reservations?id="+id;
    }

    @PostMapping(value = "/delete")
    public String deleteReservation(@RequestParam("id") int id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        prenotazioneService.delReservation(id);
        return "redirect:/reservations?id="+u.getIdUtente();
    }
}
