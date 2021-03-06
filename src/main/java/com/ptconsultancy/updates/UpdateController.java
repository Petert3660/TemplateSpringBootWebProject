package com.ptconsultancy.updates;

import com.ptconsultancy.entities.UpdateEntity;
import com.ptconsultancy.repositories.UpdateEntityRepository;
import com.ptconsultancy.utilities.Constants;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.time.LocalDateTime;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UpdateController {

    private UpdateEntityRepository updateEntityRepository;

    private UserDetailUtils userDetailUtils;

    @Autowired
    public UpdateController(UpdateEntityRepository updateEntityRepository, UserDetailUtils userDetailUtils) {
        this.updateEntityRepository = updateEntityRepository;
        this.userDetailUtils = userDetailUtils;
    }

    @GetMapping(value = "/newupdate")
    public String newupdate(NewUpdateForm newUpdateForm, Model model) {

        // Note: have to pass the form to here otherwise ThymeLeaf can't see the getters and setters for it!
        // This causes the th:field and th:errors calls to fail.

        model.addAttribute("userIsAdmin", userDetailUtils.isAdminUser());
        model.addAttribute("userName", userDetailUtils.getUserName());

        return "newupdate";
    }

    @PostMapping(value = "/newupdate/save")
    public String save(@Valid NewUpdateForm newUpdateForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "newupdate";
        }

        if (!userDetailUtils.getUserName().equals(Constants.SUPERUSER_USERNAME) && newUpdateForm.getTags().equals(Constants.SUPERUSER_TAG)) {

            model.addAttribute("userIsAdmin", userDetailUtils.isAdminUser());
            model.addAttribute("userName", userDetailUtils.getUserName());
            model.addAttribute("usernameTagError", true);

            return "newupdate";
        }

        UpdateEntity updateEntity = new UpdateEntity(newUpdateForm.getTags(), newUpdateForm.getTitle(), userDetailUtils.getUserFullname(), newUpdateForm.getDetails(), LocalDateTime.now());
        updateEntityRepository.save(updateEntity);

        return "redirect:/home";
    }
}
