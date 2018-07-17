package com.ptconsultancy.tags;

import com.ptconsultancy.entities.UpdateEntity;
import com.ptconsultancy.repositories.UpdateEntityRepository;
import com.ptconsultancy.utilities.UpdateEntitySort;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.util.ArrayList;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TagsController {

    UpdateEntityRepository updateEntityRepository;

    private UpdateEntitySort updateEntitySort;

    private UserDetailUtils userDetailUtils;

    @Autowired
    public TagsController(UpdateEntityRepository updateEntityRepository, UpdateEntitySort updateEntitySort, UserDetailUtils userDetailUtils) {
        this.updateEntityRepository = updateEntityRepository;
        this.updateEntitySort = updateEntitySort;
        this.userDetailUtils = userDetailUtils;
    }

    @GetMapping(value = "/tags")
    public String tags(TagsSearchForm tagsSearchForm, Model model) {

        model.addAttribute("userIsAdmin", userDetailUtils.isAdminUser());
        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", new ArrayList<UpdateEntity>());

        return "tags";
    }

    @PostMapping(value = "/tags/find")
    public String findTags(@Valid TagsSearchForm tagsSearchForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "tags";
        }

        model.addAttribute("userIsAdmin", userDetailUtils.isAdminUser());
        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", updateEntitySort.sortByDate(updateEntityRepository.findByTags(tagsSearchForm.getTags())));


        // Careful not to return /tags (or similar on any other controller) as this will cause the JAR version to fail!!
        return "tags";
    }
}
