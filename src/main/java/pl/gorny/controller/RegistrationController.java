package pl.gorny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @GetMapping("/register")
    public String getRegisterPage() {
        return "fragments/register";
    }
}
