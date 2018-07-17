package security;

import com.ptconsultancy.security.ChangePasswordController;
import com.ptconsultancy.users.UserRepository;
import com.ptconsultancy.utilities.UserDetailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ChangePasswordController.class})
public class TestChangePasswordController {

    @MockBean
    UserRepository userRepository;

    @MockBean
    Environment env;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    UserDetailUtils userDetailUtils;

    @Test
    public void testOldAndNewPasswordPass() {

    }
}
