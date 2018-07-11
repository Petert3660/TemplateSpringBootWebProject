package com.ptconsultancy.admin;

import com.ptconsultancy.users.Role;
import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDetailUtils userDetailUtils;

    @GetMapping(value = "/admin")
    public String tags(Model model) {

        return "admin";
    }

    @GetMapping(value = "/adduser")
    public String addUser(AddUserForm addUserForm, Model model) {

        createAndAddRoles(model);

        return "adduser";
    }

    @PostMapping(value = "/adduser/save")
    public String saveUser(@Valid AddUserForm addUserForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            createAndAddRoles(model);
            return "adduser";
        }

        List<User> users = userRepository.findByUserName(addUserForm.getUsername());

        if (users.size() != 0) {
            createAndAddRoles(model);
            model.addAttribute("usernameError", true);
            return "adduser";
        }

        userRepository.save(new User(addUserForm.getUsername(), new BCryptPasswordEncoder().encode(addUserForm.getPassword()), addUserForm.getRole(), addUserForm.getFirstname(), addUserForm.getLastname()));

        model.addAttribute("addSuccess", true);

        return "admin";
    }

    @GetMapping(value = "/deleteuser")
    public String deleteUser(DeleteUserForm deleteUserForm, Model model) {

        createAndAddUsernames(model);

        return "deleteuser";
    }

    @PostMapping(value = "/deleteuser/delete")
    public String deleteUser(@Valid DeleteUserForm deleteUserForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            createAndAddUsernames(model);
            return "/deleteuser";
        }

        if (StringUtils.isEmpty(deleteUserForm.getUsername())) {
            createAndAddUsernames(model);
            model.addAttribute("usernameError", true);
            return "/deleteuser";
        }

        List<User> users = userRepository.findByUserName(deleteUserForm.getUsername());
        userRepository.delete(users.get(0));

        model.addAttribute("deleteSuccess", true);

        return "admin";
    }

    private void createAndAddRoles(Model model) {
        List<String> roles = new ArrayList<>();

        roles.add(Role.ADMIN.toString());
        roles.add(Role.USER.toString());

        model.addAttribute("roles", roles);
    }

    private void createAndAddUsernames(Model model) {
        List<User> users = userDetailUtils.sortByUsername((ArrayList<User>) userRepository.findAll());
        List<String> usernames = new ArrayList<>();

        for (User user : users) {
            if (!user.getUserName().equals("superuser")) {
                usernames.add(user.getUserName());
            }
        }

        model.addAttribute("usernames", usernames);
    }
}
