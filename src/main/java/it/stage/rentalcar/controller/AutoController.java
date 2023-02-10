package it.stage.rentalcar.controller;

import it.stage.rentalcar.domain.Auto;
import it.stage.rentalcar.service.AutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService){
        this.autoService=autoService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getCars(Model model){
        List<Auto> cars = autoService.getCars();
        model.addAttribute("cars",  cars);
        return "carFleet";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCar(@RequestParam(value = "id", required = false) Integer id, Model model){
        Auto newA = new Auto();
        if(id!=null){
            newA = autoService.getCarFromId(id);
            model.addAttribute("id", id);
        }
        model.addAttribute("newAuto", newA);
        return "carForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String insCar(@ModelAttribute Auto auto){
        autoService.insOrUpCar(auto);
        return "redirect:/cars";
    }

    @PostMapping(value = "/delete")
    public String deleteCar(@RequestParam("id") int id){
        autoService.deleteCar(id);
        return "redirect:/cars";
    }
}
