package com.ptconsultancy.security;

import com.ptconsultancy.users.UserRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangePasswordController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/changepass")
    public String changePass(ChangePasswordForm changePasswordForm, Model model) {

        return "changepassword";
    }

    @PostMapping(value = "/changepass")
    public String actuallyChangePass(@Valid ChangePasswordForm changePasswordForm, Model model, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "changepassword";
        }

        return "home";
    }
}
