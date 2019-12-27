package org.inlighting.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.inlighting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/welcome")
    @RequiresAuthentication
    public String welcome(Model model) {
        model.addAttribute("data", "Welcome");
        return "index";
    }

    @GetMapping("/user")
    public String user(Model model) {
        return "user";
    }

}
