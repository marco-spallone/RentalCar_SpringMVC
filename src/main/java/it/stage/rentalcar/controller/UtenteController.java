package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Utente;
import it.stage.rentalcar.service.UtenteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UtenteController {
    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String getCustomers(HttpSession session, Model model){
        List<Utente> customers = utenteService.getCustomers(false);
        model.addAttribute("customers", customers);
        session.setAttribute("myid", "1");
        session.setAttribute("isAdmin", "true");
        return "customers";
    }

    @RequestMapping(value = "/filterCustomers", method = RequestMethod.POST)
    public String filter(@RequestParam("field") String field, @RequestParam("value") String value, Model model){
        List<Utente> customers = utenteService.filter(field, value);
        model.addAttribute("customers", customers);
        return "customers";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public String addCustomer(HttpSession session, Model model){
        Utente utente = new Utente();
        model.addAttribute("newCustomer", utente);
        model.addAttribute("myid", session.getAttribute("myid"));
        return "customerForm";
    }

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public String insCustomer(@ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers";
    }

    @RequestMapping(value = "/editCustomer", method = RequestMethod.GET)
    public String editCustomer(HttpSession session, @RequestParam("id") int id, Model model){
        Utente newU = utenteService.getUserFromId(id);
        model.addAttribute("id", id);
        model.addAttribute("myid", session.getAttribute("myid"));
        model.addAttribute("newCustomer", newU);
        return "customerForm";
    }

    @RequestMapping(value = "/editCustomer", method = RequestMethod.POST)
    public String upCustomer(@ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/customers";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
    public String userProfile(HttpSession session, Model model){
        Utente utente = utenteService.getUserFromId(Integer.parseInt(String.valueOf(session.getAttribute("myid"))));
        model.addAttribute("newCustomer", utente);
        return "userProfile";
    }

    @RequestMapping(value = "/userProfile", method = RequestMethod.POST)
    public String upProfile(@ModelAttribute("newCustomer") Utente utente){
        utenteService.insOrUpCustomer(utente);
        return "redirect:/userProfile";
    }

    @RequestMapping(value = "/deleteCustomer", method = RequestMethod.GET)
    public String deleteCustomer(@RequestParam("id") int id){
        utenteService.delCustomer(id);
        return "redirect:/customers";
    }
}
