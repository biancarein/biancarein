package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void testPermutation() {
        System.out.println(UPPER);
        Permutation testPerm = new Permutation("(ACG)", UPPER);
        assertEquals(26, testPerm.size());

        assertEquals(2, testPerm.permute(0));
        assertEquals(6, testPerm.permute(2));
        assertEquals(0, testPerm.permute(6));

        assertEquals(6, testPerm.invert(0));
        assertEquals(0, testPerm.invert(2));
        assertEquals(2, testPerm.invert(6));

        assertEquals('C', testPerm.permute('A'));
        assertEquals('G', testPerm.permute('C'));
        assertEquals('A', testPerm.permute('G'));

        assertEquals('G', testPerm.invert('A'));
        assertEquals('A', testPerm.invert('C'));
        assertEquals('C', testPerm.invert('G'));

        assertFalse(testPerm.derangement());

        Alphabet nums = new Alphabet("12345");
        Permutation testPerm2 = new Permutation("(135)", nums);
        assertEquals(5, testPerm2.size());

        assertEquals(2, testPerm2.permute(0));
        assertEquals(4, testPerm2.permute(2));
        assertEquals(0, testPerm2.permute(4));

    }

}
