package com.ptconsultancy.security;

import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.UserDetailUtils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangePasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailUtils userDetailUtils;

    @GetMapping(value = "/changepassword")
    public String changePass(ChangePasswordForm changePasswordForm, Model model) {

        model.addAttribute("errorFlag", false);
        model.addAttribute("newPassError", "");

        return "changepassword";
    }

    @PostMapping(value = "/changepassword")
    public String actuallyChangePass(@Valid ChangePasswordForm changePasswordForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "changepassword";
        }

        if (changePasswordForm.getNewPassword().equals(changePasswordForm.getRetypeNewPassword())) {
            User user = userRepository.findByUserName(userDetailUtils.getUserName()).get(0);
            user.setLoggedIn(true);
            user.setPasswordHash(passwordEncoder.encode(changePasswordForm.getNewPassword()));
            userRepository.save(user);
        } else {
            model.addAttribute("errorFlag", true);
            model.addAttribute("newPassError", "The retyped new pass word does not match the new password - please try again!");
            return "changepassword";
        }

        model.addAttribute("errorFlag", false);
        return "redirect:home";
    }
}
