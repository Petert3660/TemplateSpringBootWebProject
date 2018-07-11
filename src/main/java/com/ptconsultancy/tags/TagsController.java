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

    @Autowired
    UpdateEntityRepository updateEntityRepository;

    @Autowired
    private UpdateEntitySort updateEntitySort;

    @Autowired
    private UserDetailUtils userDetailUtils;

    @GetMapping(value = "/tags")
    public String tags(TagsSearchForm tagsSearchForm, Model model) {

        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", new ArrayList<UpdateEntity>());

        return "tags";
    }

    @PostMapping(value = "/tags/find")
    public String findTags(@Valid TagsSearchForm tagsSearchForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "tags";
        }

        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", updateEntitySort.sortByDate(updateEntityRepository.findByTags(tagsSearchForm.getTags())));


        // Careful not to return /tags as this will cause the JAR version to fail!!
        return "tags";
    }
}
