package it.stage.rentalcar.controller;

import it.stage.rentalcar.config.MyUserDetails;
import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class UtenteController {
    private final UtenteService utenteService;
    private final PasswordEncoder passwordEncoder;

    public UtenteController(UtenteService utenteService, PasswordEncoder passwordEncoder) {
        this.utenteService = utenteService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCustomers(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails ud = (MyUserDetails)authentication.getPrincipal();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        List<Utente> customers = utenteService.getCustomers(false);
        model.addAttribute("customers", customers);
        model.addAttribute("myid", u.getIdUtente());
        model.addAttribute("isAdmin", u.getIsAdmin());
        return "customers";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String filter(@RequestParam("field") String field, @RequestParam("value") String value, Model model){
        List<Utente> customers = utenteService.filter(field, value);
        model.addAttribute("customers", customers);
        return "customers";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCustomer(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        Utente utente = new Utente();
        model.addAttribute("newCustomer", utente);
        model.addAttribute("myid", u.getIdUtente());
        return "customerForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String insCustomer(@ModelAttribute("newCustomer") Utente utente) throws Exception {
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editCustomer(@RequestParam("id") int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente u = utenteService.getUserFromUsername(authentication.getName());
        Utente newU = utenteService.getUserFromId(id);
        model.addAttribute("id", id);
        model.addAttribute("myid", u.getIdUtente());
        model.addAttribute("newCustomer", newU);
        return "customerForm";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String upCustomer(@ModelAttribute("newCustomer") Utente utente) throws Exception {
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public String userProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Utente utente = utenteService.getUserFromUsername(authentication.getName());
        model.addAttribute("newCustomer", utente);
        model.addAttribute("isAdmin", utente.getIsAdmin());
        model.addAttribute("myid", utente.getIdUtente());
        return "userProfile";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.POST)
    public String upProfile(@ModelAttribute("newCustomer") Utente utente) throws Exception {
        utenteService.insOrUpCustomer(utente);
        return "redirect:/userProfile";
    }

    @PostMapping(value = "/delete")
    public String deleteCustomer(@RequestParam("id") int id){
        utenteService.delCustomer(id);
        return "redirect:/customers";
    }
}
