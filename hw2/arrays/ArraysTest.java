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
        int[] input_arr = new int[]{1,2,3,4,5};
        int[] exp_arr = new int[]{1,2,5};
        assertArrayEquals(exp_arr, Arrays.remove(input_arr, 2, 2));
        int[] exp_arr2 = new int[]{1};
        assertArrayEquals(exp_arr2, Arrays.remove(input_arr, 1, 4));
        int[] exp_arr3 = new int[]{};
        assertArrayEquals(exp_arr3, Arrays.remove(input_arr, 0, 5));

        int[] input2 = new int[]{6,7};
        int[] expected = new int[]{};
        assertArrayEquals(expected, Arrays.remove(input2, 0, 2));

    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
