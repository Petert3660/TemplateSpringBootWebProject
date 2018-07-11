package com.ptconsultancy.updates;

import com.ptconsultancy.admin.RemovalForm;
import com.ptconsultancy.repositories.UpdateEntityRepository;
import com.ptconsultancy.utilities.UpdateEntitySort;
import com.ptconsultancy.utilities.UserDetailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;

@Controller
public class MyUpdatesController {

    @Autowired
    private UpdateEntityRepository updateEntityRepository;

    @Autowired
    private UpdateEntitySort updateEntitySort;

    @Autowired
    private UserDetailUtils userDetailUtils;

    @GetMapping(value = "/myupdates")
    public String myupdates(Model model) {

        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", updateEntitySort.sortByDate(updateEntityRepository.findByUsername(userDetailUtils.getUserFullname())));

        return "myupdates";
    }

    @PostMapping(value = "/myupdates/removepost")
    public String remove(RemovalForm removalForm, Model model) {

        System.out.println("In the post removal method now, with buttonId - " + removalForm.getButtonId());

        if (!StringUtils.isEmpty(removalForm.getButtonId())) {
            updateEntityRepository.deleteById(Long.parseLong(removalForm.getButtonId()));
        }

        return "redirect:/home";
    }
}
