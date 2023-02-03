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

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String getCustomers(@RequestParam("myid") int myid, Model model){
        List<Utente> customers = utenteService.getCustomers(false);
        model.addAttribute("customers", customers);
        model.addAttribute("myid", myid);
        return "customers";
    }
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomer(@RequestParam("myid") int myid, Model model){
        Utente utente = new Utente();
        model.addAttribute("newCustomer", utente);
        model.addAttribute("myid", myid);
        return "customerForm";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public String insCustomer(@RequestParam("myid") int myid, @ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers?isAdmin=true&myid="+myid;
    }

    @RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
    public String editCustomer(@RequestParam("id") int id, @RequestParam("myid") int myid, Model model){
        Utente utente = new Utente();
        model.addAttribute("id", id);
        model.addAttribute("myid", myid);
        model.addAttribute("newCustomer", utente);
        return "customerForm";
    }

    @RequestMapping(value = "/editCustomer", method = RequestMethod.POST)
    public String upCustomer(@RequestParam("myid") int myid, @ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers?isAdmin=true&myid="+myid;
    }

    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam("id") int id, @RequestParam("myid") int myid){
        utenteService.delCustomer(id);
        return "redirect:/customers?isAdmin=true&myid="+myid;
    }
}
