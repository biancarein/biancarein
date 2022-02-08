package arrays;

import lists.IntList;
import lists.IntListList;
import lists.Lists;
import org.junit.Test;
import static org.junit.Assert.*;

/** arrays tests
 *  @author Bianca Rein Del Rosario
 */

public class ArraysTest {

    @Test
    public void catenateTest(){
        int[] inputA = new int[]{1,2,3,4};
        int[] inputB = new int[]{2,3,4};
        int[] exp = new int[]{1,2,3,4,2,3,4};
        assertArrayEquals(exp, Arrays.catenate(inputA, inputB));
    }

    @Test
    public void removeTest(){
        int[] input_arr = new int[]{1,2,3,4};
        int[] exp_arr = new int[]{1,2,4};
        assertArrayEquals(exp_arr, Arrays.remove(input_arr, 2, 1));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
