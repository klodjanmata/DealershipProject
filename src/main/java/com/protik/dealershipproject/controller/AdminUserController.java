package com.protik.dealershipproject.controller;

import com.protik.dealershipproject.dto.UserCreateForm;
import com.protik.dealershipproject.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserAccountService userAccountService;

    public AdminUserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @ModelAttribute("userForm")
    public UserCreateForm userForm() {
        return new UserCreateForm();
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userAccountService.findAll());
        return "admin/users";
    }

    @PostMapping
    public String createUser(@ModelAttribute("userForm") @Valid UserCreateForm form,
                             BindingResult bindingResult,
                             Model model) {
        if (!bindingResult.hasErrors()) {
            try {
                userAccountService.createUser(form.getUsername(), form.getPassword(), form.getRole());
                return "redirect:/admin/users?created";
            } catch (IllegalArgumentException ex) {
                bindingResult.rejectValue("username", "userForm.username", ex.getMessage());
            }
        }

        model.addAttribute("users", userAccountService.findAll());
        model.addAttribute("error", true);
        return "admin/users";
    }
}
