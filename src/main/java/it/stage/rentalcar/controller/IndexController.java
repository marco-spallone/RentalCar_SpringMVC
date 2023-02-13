package it.stage.rentalcar.controller;

import it.stage.rentalcar.config.MyUserDetails;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {
    private final UtenteService utenteService;
    private final PasswordEncoder passwordEncoder;

    public IndexController(UtenteService utenteService, PasswordEncoder passwordEncoder) {
        this.utenteService = utenteService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/index")
    public String index(){
        MyUserDetails details = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Utente u = utenteService.getUserFromUsername(details.getUsername());
        if(u.getIsAdmin()){
            return "redirect:/customers";
        } else {
            return "redirect:/reservations?id="+u.getIdUtente();
        }
    }

    @GetMapping(value = "/prova")
    public void insert() throws Exception {
        Utente u = new Utente();
        u.setNome("Marco");
        u.setCognome("Spallone");
        u.setUsername("ADMIN");
        u.setPassword(passwordEncoder.encode("marco"));
        u.setIsAdmin(true);
        utenteService.insOrUpCustomer(u);
    }

}
