package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUser(ModelMap modelMap, Principal principal) {
        User currentUser = userService.findUserByEmail(principal.getName());
        String rolesTrimmed = currentUser.trimRoles();
        modelMap.addAttribute("rolesTrimmed", rolesTrimmed);
        modelMap.addAttribute("currentUser", currentUser);
        return "/user";
    }

}