package image;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class MatrixUtilsTest {

    @Test
    public void accumulateVerticalTest(){
        MatrixUtils.accumulateVertical(new double[][] {{10, 4, 5, 6}, {3, 10, 18, 6}, {8, 5, 19, 6}});
    }

    @Test
    public void accumulateTest(){
        double[][] input_a = new double[][]{{10, 4, 5}, {3, 10, 18}, {8, 5, 19}};
        double[][] exp_ar = new double[][]{{10, 7, 5}, {3, 10, 18}, {8, 5, 19}};
        assertArrayEquals(exp_ar,MatrixUtils.accumulate(input_a, MatrixUtils.Orientation.HORIZONTAL));

        MatrixUtils.accumulate(new double[][] {{10, 4, 5, 6}, {3, 10, 18, 6}, {8, 5, 19, 6}}, MatrixUtils.Orientation.HORIZONTAL);
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
