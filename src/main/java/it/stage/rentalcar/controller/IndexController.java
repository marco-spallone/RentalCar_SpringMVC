package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    private final UtenteService utenteService;

    public IndexController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping(value = "/index")
    public String index(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        session.setAttribute("isAdmin", u.getIsAdmin());
        session.setAttribute("myid", u.getIdUtente());
        if(session.getAttribute("isAdmin").equals(true)){
            return "redirect:/customers";
        } else {
            return "redirect:/viewReservations?id="+u.getIdUtente();
        }
    }
}
