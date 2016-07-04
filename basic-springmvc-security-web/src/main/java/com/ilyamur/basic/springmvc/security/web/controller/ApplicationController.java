package com.ilyamur.basic.springmvc.security.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class ApplicationController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model, Principal principal) {
        model.addAttribute("userName", (principal != null) ? principal.getName() : "anonymous");
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            (new SecurityContextLogoutHandler()).logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/admin_console", method = RequestMethod.GET)
    public String adminConsole() {
        return "admin_console";
    }
}
