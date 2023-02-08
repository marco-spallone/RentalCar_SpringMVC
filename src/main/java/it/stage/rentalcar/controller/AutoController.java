package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.service.AutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cars")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService){
        this.autoService=autoService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCars(HttpSession session, Model model){
        List<Auto> cars = autoService.getCars();
        model.addAttribute("cars",  cars);
        model.addAttribute("myid", session.getAttribute("myid"));
        model.addAttribute("isAdmin", session.getAttribute("isAdmin"));
        return "carFleet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCar(Model model){
        Auto a = new Auto();
        model.addAttribute("newAuto", a);
        return "carForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String insCar(@ModelAttribute Auto auto){
        autoService.insOrUpCar(auto);
        return "redirect:/cars";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editCar(@RequestParam("id") Integer id, Model model){
        Auto newA = autoService.getCarFromId(id);
        model.addAttribute("newAuto", newA);
        model.addAttribute("id", id);
        return "carForm";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String upCar(@ModelAttribute Auto auto){
        autoService.insOrUpCar(auto);
        return "redirect:/cars";
    }

    @PostMapping(value = "/delete")
    public String deleteCar(@RequestParam("id") int id){
        autoService.deleteCar(id);
        return "redirect:/cars";
    }
}
