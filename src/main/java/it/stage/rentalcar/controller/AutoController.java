package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.service.AutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService){
        this.autoService=autoService;
    }

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String getCars(@RequestParam("myid") int myid, @RequestParam("id") int id, @RequestParam("isAdmin") boolean isAdmin, Model model){
        List<Auto> cars = autoService.getCars();
        model.addAttribute("cars",  cars);
        model.addAttribute("myid", myid);
        model.addAttribute("id", id);
        model.addAttribute("isAdmin", isAdmin);
        return "carFleet";
    }

    @RequestMapping(value = "/addCar", method = RequestMethod.GET)
    public String addCar(@RequestParam("myid") int myid, Model model){
        Auto a = new Auto();
        model.addAttribute("myid", myid);
        model.addAttribute("newAuto", a);
        return "carForm";
    }

    @RequestMapping(value = "/addCar", method = RequestMethod.POST)
    public String insCar(@RequestParam("myid") int myid, @ModelAttribute Auto auto){
        autoService.insOrUpCar(auto);
        return "redirect:/cars?isAdmin=true&myid="+myid+"&id="+myid;
    }

    @RequestMapping(value = "/editCar", method = RequestMethod.GET)
    public String editCar(@RequestParam("myid") int myid, @RequestParam("id") int id, Model model){
        Auto newA = new Auto();
        Auto actualA = autoService.getCarFromId(id);
        model.addAttribute("myid", myid);
        model.addAttribute("newAuto", newA);
        model.addAttribute("actualAuto", actualA);
        model.addAttribute("id", id);
        return "carForm";
    }

    @RequestMapping(value = "/editCar", method = RequestMethod.POST)
    public String upCar(@RequestParam("myid") int myid, @ModelAttribute Auto auto){
        autoService.insOrUpCar(auto);
        return "redirect:/cars?isAdmin=true&myid="+myid+"&id="+myid;
    }

    @RequestMapping(value = "/deleteCar", method = RequestMethod.GET)
    public String deleteCar(@RequestParam("myid") int myid, @RequestParam("id") int id){
        autoService.deleteCar(id);
        return "redirect:/cars?isAdmin=true&myid="+myid+"&id="+myid;
    }
}
