package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UtenteController {
    @RequestMapping(value="index")
    public String getWelcome(Model model)
    {
        return "index";
    }
}
