package it.stage.rentalcar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @GetMapping(value = "/index")
    public String index(HttpSession session){
        System.out.println("nel contr"+session.getAttribute("prova"));
        /*if(.getAttribute("isAdmin").equals("true")){
            return "customers";
        } else {
            return "viewReservations";
        }*/
        return "";
    }
}
