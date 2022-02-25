package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 *  @author
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /** Check that PERM has an ALPHABET whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation perm = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, perm, alpha);
    }

    // FIXME: Add tests here that pass on a correct Permutation and fail on buggy Permutations.
    @Test
    public void testInvertChar() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals('B', p.invert('A'));
        assertEquals('D', p.invert('B'));
        assertEquals('A', p.invert('C'));
        assertEquals('C', p.invert('D'));

        Permutation one = getNewPermutation("(S)", getNewAlphabet("S"));
        assertEquals('S', one.invert('S'));

        Permutation two = getNewPermutation("(BA)", getNewAlphabet("AB"));
        assertEquals( 'B', two.invert('A'));
        assertEquals( 'A', two.invert('B'));
    }

    @Test
    public void testSize() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals(4, p.size());


        Permutation one = getNewPermutation("(S)", getNewAlphabet("S"));
        assertEquals(1, one.size());
    }


    @Test
    public void testPermuteChar() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals( 'C', p.permute('A'));
        assertEquals('A', p.permute('B'));
        assertEquals('D', p.permute('C'));
        assertEquals('B', p.permute('D'));

        Permutation single = getNewPermutation("(B)", getNewAlphabet("B"));
        assertEquals( 'B', single.permute('B'));

        Permutation two = getNewPermutation("(BA)", getNewAlphabet("AB"));
        assertEquals( 'B', two.permute('A'));
        assertEquals( 'A', two.permute('B'));

    }

    @Test
    public void testPermuteInt() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals( 2, p.permute(0));
        assertEquals(0, p.permute(1));
        assertEquals(3, p.permute(2));
        assertEquals(1, p.permute(3));

        Permutation single = getNewPermutation("(B)", getNewAlphabet("B"));
        assertEquals( 0, single.permute(0));

        Permutation two = getNewPermutation("(BA)", getNewAlphabet("AB"));
        assertEquals( 1, two.permute(0));
        assertEquals( 0, two.permute(1));
    }

    @Test
    public void testInvertInt() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertEquals( 1, p.invert(0));
        assertEquals(3, p.invert(1));
        assertEquals(0, p.invert(2));
        assertEquals(2, p.invert(3));

        Permutation single = getNewPermutation("(B)", getNewAlphabet("B"));
        assertEquals( 0, single.invert(0));

        Permutation two = getNewPermutation("(BA)", getNewAlphabet("AB"));
        assertEquals( 1, two.invert(0));
        assertEquals( 0, two.invert(1));
    }

    @Test(expected = EnigmaException.class)
    public void testRepeatLetters() {
        Permutation p = getNewPermutation("(BBACD)", getNewAlphabet("ABCD"));
        p.invert(1);
        p.invert('B');
        p.permute(1);
        p.permute('B');

        Permutation q = getNewPermutation("(BACD)", getNewAlphabet("ABBCD"));
        q.invert(0);
        q.invert('A');
        q.permute(1);
        q.permute('B');

        Permutation r = getNewPermutation("(BA) (BCD)", getNewAlphabet("ABCD"));
        r.invert(1);
        r.invert('B');
        r.permute(1);
        r.permute('B');
    }

    @Test(expected = EnigmaException.class)
    public void testWhiteSpace() {
        Permutation p = getNewPermutation("(BA CD)", getNewAlphabet("ABCD"));
        p.invert(2);
        p.invert('C');
        p.permute(0);
        p.permute('A');

        Permutation q = getNewPermutation("(BACD)", getNewAlphabet("A BCD"));
        q.invert(1);
        q.permute(1);
        q.invert(' ');
        q.permute(' ');

    }

    @Test
    public void testDerangement() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        assertTrue(p.derangement());

        Permutation q = getNewPermutation("(BAC) (D)", getNewAlphabet("ABCD"));
        assertFalse(q.derangement());
    }

    @Test(expected = EnigmaException.class)
    public void testNotInAlphabet() {
        Permutation p = getNewPermutation("(BACD)", getNewAlphabet("ABCD"));
        p.invert('F');
        p.invert(4);
        p.permute(4);
        p.permute('F');
    }
}
