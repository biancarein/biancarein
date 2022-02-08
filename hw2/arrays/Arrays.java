package arrays;

/* NOTE: The file Arrays/Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2 */

import static java.lang.System.*;

/** Array utilities.
 *  @author
 */
class Arrays {

    /* C1. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int[] return_arr = new int[A.length + B.length];
        arraycopy(A, 0, return_arr, 0, A.length);
        arraycopy(B, 0, return_arr, A.length, B.length);
        return return_arr;
    }

    /* C2. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. If the start + len is out of bounds for our array, you
     *  can return null.
     *  Example: if A is [0, 1, 2, 3] and start is 1 and len is 2, the
     *  result should be [0, 3]. */
    static int[] remove(int[] A, int start, int len) {
        int[] return_array = new int[A.length - len];
        if (return_array.length == 0){
            return new int[]{};
        }else if(return_array.length == 1){
            arraycopy(A, 0, return_array, 0, start);
            return return_array;
        } else {
            arraycopy(A, 0, return_array, 0, start);
            arraycopy(A, start + len, return_array, start, len - 1);
            return return_array;
        }
    }

}
