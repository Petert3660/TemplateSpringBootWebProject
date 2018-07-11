package utilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ptconsultancy.users.Role;
import com.ptconsultancy.users.User;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

// This is a good example of a test class using PowerMock + Mockito

@RunWith(PowerMockRunner.class)
@PrepareForTest(fullyQualifiedNames = {"com.ptconsultancy.*", "org.springframework.security.core.context.*"})
@PowerMockIgnore({"javax.management.*", "javax.security.auth.Subject"})
public class TestUserDetailUtils {

    private static final String SUPERUSER_USERNAME = "superuser";

    private static final  String TEST_USERNAME_1 = "petert";
    private static final String TEST_PASS_HASH_1 = "$2a$10$ibkhmcAsf8iEBiQadNpjRO61TXKdfeJc8oZgcqhfkHqIolgVtN5oW";
    private static final Role TEST_ROLE_1 = Role.USER;
    private static final String TEST_FIRSTNAME_1 = "Peter";
    private static final String TEST_LASTTNAME_1 = "Thomson";

    private static final String TEST_USERNAME_2 = "padamson";
    private static final String TEST_PASS_HASH_2 = "$2a$10$ibkhmcAsf8iEBiQadNpjRO61TXKdfeJc8oZgcqhfkHqIolgVtN5oW";
    private static final Role TEST_ROLE_2 = Role.ADMIN;
    private static final String TEST_FIRSTNAME_2 = "Paul";
    private static final String TEST_LASTTNAME_2 = "Adamson";

    private static final String EXCEPTION_CLASS = "class org.springframework.security.core.userdetails.UsernameNotFoundException";

    private List<User> unsortedTestList;

    UserDetailUtils userDetailUtils;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        unsortedTestList = new ArrayList();

        PowerMockito.mockStatic(SecurityContextHolder.class);
        PowerMockito.when(SecurityContextHolder.getContext()).thenReturn(securityContext);
        PowerMockito.when(securityContext.getAuthentication()).thenReturn(authentication);
    }

    @Test
    public void test_getUserFullname_for_ordinary_user() {

        User testUser = new User(TEST_USERNAME_1, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1);
        unsortedTestList.add(testUser);

        PowerMockito.when(authentication.getName()).thenReturn(TEST_USERNAME_1);
        PowerMockito.when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        userDetailUtils = new UserDetailUtils(userRepository);

        assertThat(userDetailUtils.getUserFullname(), is(TEST_FIRSTNAME_1 + " " + TEST_LASTTNAME_1));
    }


    @Test
    public void test_getUserFullname_for_superuser() {

        User testUser = new User(SUPERUSER_USERNAME, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1);
        unsortedTestList.add(testUser);

        PowerMockito.when(authentication.getName()).thenReturn(SUPERUSER_USERNAME);
        PowerMockito.when(userRepository.findByUserName(SUPERUSER_USERNAME)).thenReturn(unsortedTestList);

        userDetailUtils = new UserDetailUtils(userRepository);

        assertThat(userDetailUtils.getUserFullname(), is(SUPERUSER_USERNAME));
    }

    @Test
    public void test_getUserFullname_more_than_one_user_in_list() {

        User testUser = new User(TEST_USERNAME_1, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1);
        unsortedTestList.add(testUser);
        testUser = new User(TEST_USERNAME_2, TEST_PASS_HASH_2, TEST_ROLE_2, TEST_FIRSTNAME_2, TEST_LASTTNAME_2);
        unsortedTestList.add(testUser);

        PowerMockito.when(authentication.getName()).thenReturn(TEST_USERNAME_1);
        PowerMockito.when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        userDetailUtils = new UserDetailUtils(userRepository);

        try {
            userDetailUtils.getUserFullname();
        } catch (Exception e) {
            assertThat(e.getClass().toString(), is(EXCEPTION_CLASS));
            assertThat(e.getMessage(), is(TEST_USERNAME_1));
        }
    }

    @Test
    public void test_isAdminUser_false() {

        User testUser = new User(TEST_USERNAME_1, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1);
        unsortedTestList.add(testUser);

        PowerMockito.when(authentication.getName()).thenReturn(TEST_USERNAME_1);
        PowerMockito.when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        userDetailUtils = new UserDetailUtils(userRepository);

        assertThat(userDetailUtils.isAdminUser(), is(false));
    }

    @Test
    public void test_isAdminUser_true() {

        User testUser = new User(TEST_USERNAME_2, TEST_PASS_HASH_2, TEST_ROLE_2, TEST_FIRSTNAME_2, TEST_LASTTNAME_2);
        unsortedTestList.add(testUser);

        PowerMockito.when(authentication.getName()).thenReturn(TEST_USERNAME_1);
        PowerMockito.when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        userDetailUtils = new UserDetailUtils(userRepository);

        assertThat(userDetailUtils.isAdminUser(), is(true));
    }

    @Test
    public void test_isAdminUser_more_than_one_user_in_list() {

        User testUser = new User(TEST_USERNAME_1, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1);
        unsortedTestList.add(testUser);
        testUser = new User(TEST_USERNAME_2, TEST_PASS_HASH_2, TEST_ROLE_2, TEST_FIRSTNAME_2, TEST_LASTTNAME_2);
        unsortedTestList.add(testUser);

        PowerMockito.when(authentication.getName()).thenReturn(TEST_USERNAME_1);
        PowerMockito.when(userRepository.findByUserName(TEST_USERNAME_1)).thenReturn(unsortedTestList);

        userDetailUtils = new UserDetailUtils(userRepository);

        try {
            userDetailUtils.isAdminUser();
        } catch (Exception e) {
            assertThat(e.getClass().toString(), is(EXCEPTION_CLASS));
            assertThat(e.getMessage(), is(TEST_USERNAME_1));
        }
    }
}
