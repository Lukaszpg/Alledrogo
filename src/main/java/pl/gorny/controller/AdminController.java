package pl.gorny.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AdminController {

    @GetMapping("/admin")
    public String displayAdminPanel() {
        return "fragments/admin/panel";
    }

    @GetMapping("/admin/add-category")
    public String displayAddCategory() {
        return "fragments/admin/add-category";
    }


}
