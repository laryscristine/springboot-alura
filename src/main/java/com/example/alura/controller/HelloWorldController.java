package com.example.alura.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //indica que é um controller
public class HelloWorldController {
    @RequestMapping("/") // Indica pro navegador qual 'suburl' será utilizada necessário sempre declarar o construtor para usar
    @ResponseBody // indica para o springboot que é "um corpo" e não uma página
    public String hello() {
        return "Hello world";
    }
}
