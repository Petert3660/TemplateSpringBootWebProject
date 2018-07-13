package security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ptconsultancy.security.CurrentUser;
import com.ptconsultancy.security.CurrentUserDetailsService;
import com.ptconsultancy.users.Role;
import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mock;

public class TestCurrentUserDetailsService {

    @Mock
    UserRepository userRepository = mock(UserRepository.class);

    private static final String TEST_USERNAME_1 = "alisonK";
    private static final String TEST_PASS_HASH_1 = "$2a$10$ibkhmcAsf8iEBiQadNpjRO61TXKdfeJc8oZgcqhfkHqIolgVtN5oW";
    private static final Role TEST_ROLE_1 = Role.USER;
    private static final String TEST_FIRSTNAME_1 = "Alison";
    private static final String TEST_LASTTNAME_1 = "King";

    private static final String TEST_USERNAME_2 = "johnH";
    private static final String TEST_PASS_HASH_2 = "$2a$10$ibkhmcAsf8iEBiQadNpjRO61TXKdfeJc8oZgcqhfkHqIolgVtN5oW";
    private static final Role TEST_ROLE_2 = Role.ADMIN;
    private static final String TEST_FIRSTNAME_2 = "John";
    private static final String TEST_LASTTNAME_2 = "Hunter";

    private static final String EXCEPTION_CLASS = "class org.springframework.security.core.userdetails.UsernameNotFoundException";

    private List<User> unsortedTestList;

    private void setUp(int mode) {

        unsortedTestList = new ArrayList();

        User testUser = new User(TEST_USERNAME_1, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1, false);
        unsortedTestList.add(testUser);
        if (mode > 0) {
            testUser = new User(TEST_USERNAME_2, TEST_PASS_HASH_2, TEST_ROLE_2, TEST_FIRSTNAME_2, TEST_LASTTNAME_2, false);
            unsortedTestList.add(testUser);
        }
    }

    @Test
    public void test_loadUerByName() {

        setUp(0);
        CurrentUserDetailsService currentUserDetailsService = new CurrentUserDetailsService(userRepository);

        when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        CurrentUser userDetails = (CurrentUser) currentUserDetailsService.loadUserByUsername(TEST_USERNAME_1);

        assertThat(userDetails.getUsername(), is(TEST_USERNAME_1));
        assertThat(userDetails.getPassword(), is(TEST_PASS_HASH_1));
        assertThat(userDetails.getUser().getUserName(), is(TEST_USERNAME_1));
        assertThat(userDetails.getUser().getPasswordHash(), is(TEST_PASS_HASH_1));
        assertThat(userDetails.getUser().getFirstname(), is(TEST_FIRSTNAME_1));
        assertThat(userDetails.getUser().getLastname(), is(TEST_LASTTNAME_1));
        assertThat(userDetails.getUser().getRole(), is(TEST_ROLE_1));
    }

    @Test
    public void test_loadUerByName_multiple_users() {

        setUp(1);
        CurrentUserDetailsService currentUserDetailsService = new CurrentUserDetailsService(userRepository);

        when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        CurrentUser userDetails;

        try {
            userDetails = (CurrentUser) currentUserDetailsService.loadUserByUsername(TEST_USERNAME_1);
        } catch (Exception e) {
            assertThat(e.getClass().toString(), is(EXCEPTION_CLASS));
            assertThat(e.getMessage(), is(TEST_USERNAME_1));
        }
    }
}
