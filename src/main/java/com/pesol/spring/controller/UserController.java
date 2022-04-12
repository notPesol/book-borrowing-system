package com.pesol.spring.controller;

import javax.validation.Valid;

import com.pesol.spring.entity.User;
import com.pesol.spring.model.RegistrationUser;
import com.pesol.spring.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes(names = {
    "successMessage"
})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String renderRegistration(Model model) {
        model.addAttribute("registrationUser", new RegistrationUser());

        return "register";
    }
    
    @PostMapping("/registration")
    public String processRegister(@Valid @ModelAttribute RegistrationUser registrationUser, 
            BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()) {
            return "register";
        }

        User tempUser = userService
				.getByUsernameOrEmail(registrationUser.getUsername(), registrationUser.getEmail());
		
		if (tempUser != null) {
            model.addAttribute("registrationUser", new RegistrationUser());
			model.addAttribute("errorMessage", "Username or Email is already exist");
            return "register";
		}

        userService.save(registrationUser);

        model.addAttribute("successMessage", "Registration successfully");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String renderLogin() {    
        return "login";
    }

    @GetMapping("/errors/403")
    public String render403() {
        return "errors/403";
    }
}
