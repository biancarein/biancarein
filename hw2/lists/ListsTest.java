package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** IntList tests
 *
 *  @author Bianca Del Rosario
 */

public class ListsTest {

    @Test
    public void basicRunsTest() {
        IntList input = IntList.list(1, 2, 3, 1, 2);
        IntList run1 = IntList.list(1, 2, 3);
        IntList run2 = IntList.list(1, 2);
        IntListList result = IntListList.list(run1, run2);
        assertEquals(result, Lists.naturalRuns(input));
    }

    @Test
    public void multipleRunsTest() {
        IntList input1 = IntList.list(1, 2, 3, 2, 1);
        IntList run1 = IntList.list(1, 2, 3);
        IntList run2 = IntList.list(2);
        IntList run3 = IntList.list(1);
        IntListList result1 = IntListList.list(run1, run2, run3);
        assertEquals(result1, Lists.naturalRuns(input1));
    }


    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
