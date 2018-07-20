package com.ptconsultancy.home;

import com.ptconsultancy.entities.UpdateEntity;
import com.ptconsultancy.repositories.UpdateEntityRepository;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.CommonUtils;
import com.ptconsultancy.utilities.Constants;
import com.ptconsultancy.utilities.UpdateEntitySort;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private UpdateEntityRepository updateEntityRepository;

    private UserRepository userRepository;

    private UpdateEntitySort updateEntitySort;

    private UserDetailUtils userDetailUtils;

    @Autowired
    public HomeController(UpdateEntityRepository updateEntityRepository, UserRepository userRepository, UpdateEntitySort updateEntitySort,
        UserDetailUtils userDetailUtils) {
        this.updateEntityRepository = updateEntityRepository;
        this.userRepository = userRepository;
        this.updateEntitySort = updateEntitySort;
        this. userDetailUtils = userDetailUtils;
    }

    @GetMapping(value = "/home")
    public String tags(Model model) {

        if (!userRepository.findByUserName(userDetailUtils.getUserName()).get(0).isLoggedIn()) {
            return "redirect:changepassword";
        }

        List<UpdateEntity> sortedEntities = updateEntitySort.sortByDate((List<UpdateEntity>) updateEntityRepository.findAll());
        List<UpdateEntity> todaysList = updateEntitySort.getTodaysList(sortedEntities);

        LocalDateTime today = LocalDateTime.now();
        if (todaysList.size() == 0) {
            String message = Constants.STANDARD_TITLE + " on " + CommonUtils.getDateString(today);
            UpdateEntity firstPost = new UpdateEntity(Constants.SUPERUSER_TAG, Constants.STANDARD_TITLE, Constants.SUPERUSER_USERNAME, message, today);
            updateEntityRepository.save(firstPost);
            sortedEntities = updateEntitySort.sortByDate((List<UpdateEntity>) updateEntityRepository.findAll());
            todaysList = updateEntitySort.getTodaysList(sortedEntities);
        }

        List<UpdateEntity> olderList = removeStandardMessageIfPresent(sortedEntities, today);

        model.addAttribute("userIsAdmin", userDetailUtils.isAdminUser());
        model.addAttribute("userName", userDetailUtils.getUserName());
        model.addAttribute("updates", todaysList);
        model.addAttribute("olderupdates", olderList);

        return "home";
    }

    private List<UpdateEntity> removeStandardMessageIfPresent(List<UpdateEntity> sortedEntities, LocalDateTime today) {

        for (UpdateEntity entity : sortedEntities) {
            if (!CommonUtils.getDateString(today).equals(CommonUtils.getDateString(entity.getCreatedAt())) && entity.getTags().equals(Constants.SUPERUSER_TAG)
                && entity.getTitle().equals(Constants.STANDARD_TITLE)) {
                updateEntityRepository.delete(entity);
                break;
            }
        }
        sortedEntities = updateEntitySort.getOlderList(sortedEntities);

        return sortedEntities;
    }

    @PostMapping(value = "/home")
    public void closeDown() {
        System.exit(0);
    }
}
