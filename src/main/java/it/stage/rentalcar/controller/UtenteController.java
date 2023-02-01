package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class UtenteController {
    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @RequestMapping(value = "/customers/{isAdmin}", method = RequestMethod.GET)
    public String getCustomers(@PathVariable("isAdmin") Boolean isAdmin, Model model){
        Utente utente = new Utente();
        List<Utente> customers = utenteService.getCustomers(isAdmin);
        model.addAttribute("customers", customers);
        model.addAttribute("delCustomer", utente);
        return "customers";
    }
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomer(Model model){
        Utente utente = new Utente();
        model.addAttribute("newCustomer", utente);
        return "addCustomer";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public String insCustomer(@ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers/0";
    }

    @RequestMapping(value = "/editCustomer/{id}", method = RequestMethod.GET)
    public String editCustomer(@PathVariable("id") int id, Model model){
        Utente utente = new Utente();
        model.addAttribute("id", id);
        model.addAttribute("newCustomer", utente);
        return "addCustomer";
    }

    @RequestMapping(value = "/editCustomer/{id}", method = RequestMethod.POST)
    public String upCustomer(@ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers/0";
    }
}
