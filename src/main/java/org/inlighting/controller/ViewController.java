package org.inlighting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class ViewController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("data", "Welcome");
        return "index";
    }

}
