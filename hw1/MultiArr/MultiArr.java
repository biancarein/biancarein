import java.sql.SQLOutput;

/** Multidimensional array
 *  @author Zoe Plaxco
 */

public class MultiArr {

    /**
    {{"hello","you","world"} ,{"how","are","you"}} prints:
    Rows: 2
    Columns: 3

    {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
    Rows: 4
    Columns: 4
    */
    public static void printRowAndCol(int[][] arr) {
        System.out.println("Rows:" + arr.length);
        int num_cols = 0;
        for (int row = 0; row < arr.length; row++){
            num_cols = Math.max(num_cols, arr[row].length);
        }
        System.out.println("Columns:" + num_cols);
    }

    /**
    @param arr: 2d array
    @return maximal value present anywhere in the 2d array
    */
    public static int maxValue(int[][] arr) {
        int max_num = 0;
        for (int row = 0; row < arr.length; row++){
            for(int col=0; col < arr[row].length; col ++){
                max_num = Math.max(max_num, arr[row][col]);
            }
        }
        return max_num;
    }

    /**Return an array where each element is the sum of the
    corresponding row of the 2d array*/
    public static int[] allRowSums(int[][] arr) {
        int[] sum_array = {};
        for (int row = 0; row < arr.length; row++){
            int sum_row = 0;
            for(int col=0; col < arr[row].length; col ++){
                sum_row += arr[row][col];
            }
            sum_array[row] += sum_row;
        }
        return sum_array;
    }
}
