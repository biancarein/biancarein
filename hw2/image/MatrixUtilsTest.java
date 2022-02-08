package image;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class MatrixUtilsTest {

    @Test
    public void accumulateVerticalTest(){

    }

    @Test
    public void accumulateTest(){

    }

    @Test
    public void transposeTest(){
        double[][] input_m = new double[][]{{1,2,3},{4,5,6}, {7,8,9}};
        double[][] exp_arr = new double[][]{{1,4,7}, {2,5,8}, {3,6,9}};
        assertArrayEquals(exp_arr, MatrixUtils.transpose(input_m));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
