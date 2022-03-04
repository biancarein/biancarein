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

    @Test
    public void testInvertChar() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));
        assertEquals('A', p.invert('C'));
        assertEquals('C', p.invert('D'));

        Alphabet a2 = new Alphabet("S");
        Permutation one = new Permutation("(S)", a2);
        assertEquals('S', one.invert('S'));

        Alphabet a3 = new Alphabet("AB");
        Permutation two = new Permutation("(BA)", a3);
        assertEquals( 'B', two.invert('A'));
        assertEquals( 'A', two.invert('B'));
    }

    @Test
    public void testSize() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        assertEquals(4, p.size());

        Alphabet a2 = new Alphabet("S");
        Permutation one = new Permutation("(S)", a2);
        assertEquals(1, one.size());
    }


    @Test
    public void testPermuteChar() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        assertEquals( 'C', p.permute('A'));
        assertEquals('A', p.permute('B'));
        assertEquals('D', p.permute('C'));
        assertEquals('B', p.permute('D'));

        Alphabet a2 = new Alphabet("B");
        Permutation single = new Permutation("(B)", a2);
        assertEquals( 'B', single.permute('B'));

        Alphabet a3 = new Alphabet("AB");
        Permutation two = new Permutation("(BA)", a3);
        assertEquals( 'B', two.permute('A'));
        assertEquals( 'A', two.permute('B'));

    }

    @Test
    public void testPermuteInt() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        assertEquals( 2, p.permute(0));
        assertEquals(0, p.permute(1));
        assertEquals(3, p.permute(2));
        assertEquals(1, p.permute(3));

        Alphabet a2 = new Alphabet("B");
        Permutation single = new Permutation("(B)", a2);
        assertEquals( 0, single.permute(0));

        Alphabet a3 = new Alphabet("AB");
        Permutation two = new Permutation("(BA)", a3);
        assertEquals( 1, two.permute(0));
        assertEquals( 0, two.permute(1));
    }

    @Test
    public void testInvertInt() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        assertEquals( 1, p.invert(0));
        assertEquals(3, p.invert(1));
        assertEquals(0, p.invert(2));
        assertEquals(2, p.invert(3));

        Alphabet a2 = new Alphabet("B");
        Permutation single = new Permutation("(B)", a2);
        assertEquals( 0, single.invert(0));

        Alphabet a3 = new Alphabet("AB");
        Permutation two = new Permutation("(BA)", a3);
        assertEquals( 1, two.invert(0));
        assertEquals( 0, two.invert(1));
    }

    @Test(expected = EnigmaException.class)
    public void testRepeatLetters() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BBACD)", a1);
        p.invert(1);
        p.invert('B');
        p.permute(1);
        p.permute('B');

        Alphabet a2 = new Alphabet("ABBCD");
        Permutation q = new Permutation("(BACD)", a2);
        q.invert(0);
        q.invert('A');
        q.permute(1);
        q.permute('B');

        Permutation r = new Permutation("(BA) (BCD)", a1);
        r.invert(1);
        r.invert('B');
        r.permute(1);
        r.permute('B');
    }

    @Test
    public void testDerangement() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        assertTrue(p.derangement());

        Permutation q = new Permutation("(BAC) (D)", a1);
        assertFalse(q.derangement());
    }

    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(BACD)", a1);
        p.invert('F');
        p.invert(4);
        p.permute(4);
        p.permute('F');
    }

    @Test
    public void testMapSelf() {
        Alphabet a1 = new Alphabet("ABCD");
        Permutation p = new Permutation("(B) (A) (CD)", a1);
        assertEquals('B', p.permute('B'));
        assertEquals('A', p.permute('A'));

        assertEquals('C', p.permute('D'));
        assertEquals('D', p.permute('C'));

        assertEquals('B', p.invert('B'));
        assertEquals('A', p.invert('A'));

        assertEquals('C', p.invert('D'));
        assertEquals('D', p.invert('C'));

        assertEquals(1, p.permute(1));
        assertEquals(0, p.permute(0));

        assertEquals(1, p.invert(1));
        assertEquals(0, p.invert(0));
    }
}
