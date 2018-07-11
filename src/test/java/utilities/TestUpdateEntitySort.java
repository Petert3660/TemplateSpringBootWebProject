package utilities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.ptconsultancy.entities.UpdateEntity;
import com.ptconsultancy.utilities.UpdateEntitySort;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TestUpdateEntitySort {

    private static final String TEST_TAGS_1 = "#test";
    private static final String TEST_TITLE_1 = "Welcome";
    private static final String TEST_USERNAME_1 = "superuser";
    private static final String TEST_DETAILS_1 = "Blah blah blah";

    private static final String TEST_TAGS_2 = "#admin";
    private static final String TEST_TITLE_2 = "Hello";
    private static final String TEST_USERNAME_2 = "anotheruser";
    private static final String TEST_DETAILS_2 = "Yes, yes, yes";

    private List<UpdateEntity> unsortedTestList;
    private UpdateEntity testPost;

    UpdateEntitySort updateEntitySort = new UpdateEntitySort();

    @Before
        public void setup() {
            unsortedTestList = new ArrayList();

        testPost = new UpdateEntity(TEST_TAGS_1, TEST_TITLE_1, TEST_USERNAME_1, TEST_DETAILS_1, LocalDateTime.now().minusYears(1));
        unsortedTestList.add(testPost);
        testPost = new UpdateEntity(TEST_TAGS_2, TEST_TITLE_2, TEST_USERNAME_2, TEST_DETAILS_2, LocalDateTime.now());
        unsortedTestList.add(testPost);
    }

    @Test
    public void test_sortByDate() {

        unsortedTestList = updateEntitySort.sortByDate(unsortedTestList);

        assertThat(unsortedTestList.get(0).getTags(), is(TEST_TAGS_2));
        assertThat(unsortedTestList.get(0).getTitle(), is(TEST_TITLE_2));
        assertThat(unsortedTestList.get(0).getUsername(), is(TEST_USERNAME_2));
        assertThat(unsortedTestList.get(0).getDetails(), is(TEST_DETAILS_2));

        assertThat(unsortedTestList.get(1).getTags(), is(TEST_TAGS_1));
        assertThat(unsortedTestList.get(1).getTitle(), is(TEST_TITLE_1));
        assertThat(unsortedTestList.get(1).getUsername(), is(TEST_USERNAME_1));
        assertThat(unsortedTestList.get(1).getDetails(), is(TEST_DETAILS_1));
    }

    @Test
    public void test_sortByUserName() {

        unsortedTestList = updateEntitySort.sortByUsername(unsortedTestList);

        assertThat(unsortedTestList.get(0).getTags(), is(TEST_TAGS_2));
        assertThat(unsortedTestList.get(0).getTitle(), is(TEST_TITLE_2));
        assertThat(unsortedTestList.get(0).getUsername(), is(TEST_USERNAME_2));
        assertThat(unsortedTestList.get(0).getDetails(), is(TEST_DETAILS_2));

        assertThat(unsortedTestList.get(1).getTags(), is(TEST_TAGS_1));
        assertThat(unsortedTestList.get(1).getTitle(), is(TEST_TITLE_1));
        assertThat(unsortedTestList.get(1).getUsername(), is(TEST_USERNAME_1));
        assertThat(unsortedTestList.get(1).getDetails(), is(TEST_DETAILS_1));
    }
}
