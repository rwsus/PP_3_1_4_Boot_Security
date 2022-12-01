package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String index(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("users", userService.getAllUsers());
        User currentUser = userService.findUserByEmail(principal.getName());
        String rolesTrimmed = currentUser.trimRoles();
        modelMap.addAttribute("rolesTrimmed", rolesTrimmed);
        modelMap.addAttribute("currentUser", currentUser);

        return "admin/admin_page";
    }

    @GetMapping("/{id}")
    public String showUserByID(@PathVariable("id") long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.findUserById(id));
        System.out.println(userService.findUserById(id));
        return "/admin/show";
    }

    @GetMapping("/new")
    public String newUser(ModelMap modelMap, Principal principal) {
        User currentUser = userService.findUserByEmail(principal.getName());
        String rolesTrimmed = currentUser.trimRoles();
        modelMap.addAttribute("rolesTrimmed", rolesTrimmed);
        modelMap.addAttribute("currentUser", currentUser);
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("roles", roleService.getRoles());
        return "/admin/admin_page";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user.getEmail(), user.getPassword(), user.getRoles(),
                user.getName(), user.getLastname(), user.getAge());
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(ModelMap modelMap, @PathVariable("id") Long id) {
        modelMap.addAttribute("user", userService.findUserById(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }


}