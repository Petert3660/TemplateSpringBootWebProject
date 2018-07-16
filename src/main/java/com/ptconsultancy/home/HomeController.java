package com.ptconsultancy.home;

import com.ptconsultancy.entities.UpdateEntity;
import com.ptconsultancy.repositories.UpdateEntityRepository;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.UpdateEntitySort;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UpdateEntityRepository updateEntityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UpdateEntitySort updateEntitySort;

    @Autowired
    private UserDetailUtils userDetailUtils;

    @GetMapping(value = "/home")
    public String tags(Model model) {

        if (!userRepository.findByUserName(userDetailUtils.getUserName()).get(0).isLoggedIn()) {
            System.out.println("This user has never logged in - needs to change password");
        }

        model.addAttribute("userIsAdmin", userDetailUtils.isAdminUser());
        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", updateEntitySort.sortByDate((List<UpdateEntity>) updateEntityRepository.findAll()));

        return "home";
    }

    @PostMapping(value = "/home")
    public void closeDown() {
        System.exit(0);
    }
}
