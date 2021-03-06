package com.ptconsultancy.security;

import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.UserDetailUtils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangePasswordController {

    private Environment env;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    UserDetailUtils userDetailUtils;

    @Autowired
    public void ChangePasswordController(Environment env, UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailUtils userDetailUtils) {
        this.env = env;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailUtils = userDetailUtils;
    }

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

        String passwordHash = userRepository.findByUserName(userDetailUtils.getUserName()).get(0).getPasswordHash();

        if (passwordEncoder.matches(changePasswordForm.getOldPassword(), passwordHash)) {
            if (changePasswordForm.getNewPassword().equals(changePasswordForm.getRetypeNewPassword())) {
                User user = userRepository.findByUserName(userDetailUtils.getUserName()).get(0);
                user.setLoggedIn(true);
                user.setPasswordHash(passwordEncoder.encode(changePasswordForm.getNewPassword()));
                userRepository.save(user);
            } else {
                model.addAttribute("errorFlag", true);
                model.addAttribute("newPassError", env.getProperty("changepass.newpass.errormessage"));
                return "changepassword";
            }
        } else {
            model.addAttribute("errorFlag", true);
            model.addAttribute("newPassError", env.getProperty("changepass.oldpass.errormessage"));
            return "changepassword";
        }

        model.addAttribute("errorFlag", false);
        return "redirect:home";
    }
}
