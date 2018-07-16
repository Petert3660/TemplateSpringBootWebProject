package com.ptconsultancy.utilities;

import com.ptconsultancy.users.Role;
import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailUtils {

    private Authentication authentication;
    private String username;
    private UserRepository userRepository;

    @Autowired
    public UserDetailUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailUtils() {
    }

    private void init() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        username = authentication.getName();
    }

    public String getUserName() {
        init();
        return username;
    }

    public String getUserFullname() throws UsernameNotFoundException {

        init();
        List<User> users = userRepository.findByUserName(username);

        if (users.size() == 1) {
            if (!username.equals("superuser")) {
                return users.get(0).getFirstname() + " " + users.get(0).getLastname();
            } else {
                return username;
            }
        }
        throw new UsernameNotFoundException(username);
    }

    public boolean isAdminUser() throws UsernameNotFoundException {

        init();
        List<User> users = userRepository.findByUserName(username);

        if (users.size() == 1) {
            if (users.get(0).getRole().equals(Role.ADMIN)) {
                return true;
            } else {
                return false;
            }
        }
        throw new UsernameNotFoundException(username);
    }

    public List<User> sortByUsername(List<User> unsortedList) {

        unsortedList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });

        return unsortedList;
    }
}
