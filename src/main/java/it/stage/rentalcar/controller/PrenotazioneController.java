package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Prenotazione;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.dto.PrenotazioneDTO;
import it.stage.rentalcar.mapper.PrenotazioneMapper;
import it.stage.rentalcar.service.AutoService;
import it.stage.rentalcar.service.PrenotazioneService;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        List<Prenotazione> reservations = prenotazioneService.getReservations(id);
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
    public String insReservation(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date inizio = sdf.parse(prenotazioneDTO.getDataInizio());
        Date fine = sdf.parse(prenotazioneDTO.getDataFine());
        if(fine.before(inizio) || inizio.before(now)){
            throw new Exception("Date invalide.");
        } else {
            PrenotazioneMapper prenotazioneMapper = new PrenotazioneMapper(utenteService, autoService);
            prenotazioneService.insOrUpReservation(prenotazioneMapper.fromDTOtoEntity(prenotazioneDTO));
            return "redirect:/viewReservations?isAdmin=false&id="+myid+"&myid="+myid;
        }
    }

    @RequestMapping(value = "/editReservation", method = RequestMethod.GET)
    public String editReservation(@RequestParam("myid") int myid, @RequestParam("id") int id, Model model) throws Exception {
        Prenotazione actualP = prenotazioneService.getReservationFromId(id);
        Date now = new Date();
        long diffInMillies = Math.abs(actualP.getDataInizio().getTime() - now.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if(diff==0){
            throw new Exception("Non è possibile modificare la prenotazione: mancano meno di due giorni alla data di inizio.");
        } else {
            PrenotazioneDTO newP = new PrenotazioneDTO();
            model.addAttribute("newReservation", newP);
            model.addAttribute("actualReservation", actualP);
            model.addAttribute("myid", myid);
            model.addAttribute("id", id);
            return "reservationForm";
        }
    }

    @RequestMapping(value = "editReservation", method = RequestMethod.POST)
    public String updateReservation(@RequestParam("myid") int myid, @ModelAttribute("newReservation") PrenotazioneDTO prenotazioneDTO) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        Date inizio = sdf.parse(prenotazioneDTO.getDataInizio());
        Date fine = sdf.parse(prenotazioneDTO.getDataFine());
        if(fine.before(inizio) || inizio.before(now)){
            throw new Exception("Date invalide.");
        } else {
            PrenotazioneMapper prenotazioneMapper = new PrenotazioneMapper(utenteService, autoService);
            prenotazioneService.insOrUpReservation(prenotazioneMapper.fromDTOtoEntity(prenotazioneDTO));
            return "redirect:/viewReservations?isAdmin=false&id="+myid+"&myid="+myid;
        }
    }

    @RequestMapping(value = "deleteReservation", method = RequestMethod.GET)
    public String deleteReservation(@RequestParam("myid") int myid, @RequestParam("id") int id){
        prenotazioneService.delReservation(id);
        return "redirect:/viewReservations?isAdmin=false&myid="+myid+"&id="+myid;
    }
}
