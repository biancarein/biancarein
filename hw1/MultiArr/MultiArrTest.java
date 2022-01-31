import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int[][] sample_arr = {{1,3,4},{1},{5,6,7,8},{7,9}};
        assertEquals(9, MultiArr.maxValue(sample_arr));

        int[][] sample_arr1 = {{1,1,1},{1},{1,1},{1}};
        assertEquals(1, MultiArr.maxValue(sample_arr1));

        int[][] sample_arr2 = {{1,3,4},{ },{5,6,7,8},{7,9}};
        assertEquals(9, MultiArr.maxValue(sample_arr2));
    }

    @Test
    public void testAllRowSums() {
        int[][] sample_arr = {{1,3,4},{1},{5,6,7,8},{7,9}};
        assertEquals(new int[]{8, 1, 26, 16}, MultiArr.allRowSums(sample_arr));
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
