package utilities;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.ptconsultancy.users.Role;
import com.ptconsultancy.users.User;
import com.ptconsultancy.utilities.UserDetailUtils;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class TestUserDetailUtilsSort {

    private static final String TEST_USERNAME_1 = "petert";
    private static final String TEST_PASS_HASH_1 = "$2a$10$ibkhmcAsf8iEBiQadNpjRO61TXKdfeJc8oZgcqhfkHqIolgVtN5oW";
    private static final Role TEST_ROLE_1 = Role.USER;
    private static final String TEST_FIRSTNAME_1 = "Peter";
    private static final String TEST_LASTTNAME_1 = "Thomson";

    private static final String TEST_USERNAME_2 = "padamson";
    private static final String TEST_PASS_HASH_2 = "$2a$10$ibkhmcAsf8iEBiQadNpjRO61TXKdfeJc8oZgcqhfkHqIolgVtN5oW";
    private static final Role TEST_ROLE_2 = Role.ADMIN;
    private static final String TEST_FIRSTNAME_2 = "Paul";
    private static final String TEST_LASTTNAME_2 = "Adamson";

    UserDetailUtils userDetailUtils = new UserDetailUtils();

    private List<User> unsortedTestList;

    public void setup(int mode) {
        unsortedTestList = new ArrayList();

        User testUser = new User(TEST_USERNAME_1, TEST_PASS_HASH_1, TEST_ROLE_1, TEST_FIRSTNAME_1, TEST_LASTTNAME_1);
        unsortedTestList.add(testUser);
        if (mode > 0) {
            testUser = new User(TEST_USERNAME_2, TEST_PASS_HASH_2, TEST_ROLE_2, TEST_FIRSTNAME_2, TEST_LASTTNAME_2);
            unsortedTestList.add(testUser);
        }
    }

    @Test
    public void test_sortByUsername() {

        setup(1);

        userDetailUtils.sortByUsername(unsortedTestList);

        assertThat(unsortedTestList.get(0).getUserName(), is(TEST_USERNAME_2));
        assertThat(unsortedTestList.get(0).getPasswordHash(), is(TEST_PASS_HASH_2));
        assertThat(unsortedTestList.get(0).getRole(), is(TEST_ROLE_2));
        assertThat(unsortedTestList.get(0).getFirstname(), is(TEST_FIRSTNAME_2));
        assertThat(unsortedTestList.get(0).getLastname(), is(TEST_LASTTNAME_2));

        assertThat(unsortedTestList.get(1).getUserName(), is(TEST_USERNAME_1));
        assertThat(unsortedTestList.get(1).getPasswordHash(), is(TEST_PASS_HASH_1));
        assertThat(unsortedTestList.get(1).getRole(), is(TEST_ROLE_1));
        assertThat(unsortedTestList.get(1).getFirstname(), is(TEST_FIRSTNAME_1));
        assertThat(unsortedTestList.get(1).getLastname(), is(TEST_LASTTNAME_1));
    }
}
