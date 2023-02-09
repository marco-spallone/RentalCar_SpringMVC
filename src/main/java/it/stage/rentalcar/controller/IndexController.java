package it.stage.rentalcar.controller;

import it.stage.rentalcar.config.CustomDetailsManager;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    public String index(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        if(u.getIsAdmin()){
            return "redirect:/customers";
        } else {
            return "redirect:/reservations?id="+u.getIdUtente();
        }
    }
}
