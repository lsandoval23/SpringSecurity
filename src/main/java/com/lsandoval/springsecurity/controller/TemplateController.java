package com.lsandoval.springsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// A diferencia de la anotacion rest controller, controller permite devolver vistas html
@Controller
@RequestMapping("/")
public class TemplateController {

    //  El controlador manejará el endpoint que se configuro para el login y devolverá la vista login que se implemente
    //  en el paquete de templates.
    @GetMapping("login_custom")
    public String getLogin(){
        return "login";
    }


}
