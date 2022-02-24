package enigma;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;

import static enigma.TestUtils.*;

/** The suit of all JUnit tests for the Alphabet class.
 * @author Bianca Del Rosario
 */
public class AlphabetTest {
    /* TESTS */

    @Test
    public void testAlphabet() {
        Alphabet test = new Alphabet("ABCD");

        assertEquals(4, test.size());

        assertTrue(test.contains('A'));
        assertFalse(test.contains('Z'));

        assertEquals(0, test.toInt('A'));
        assertEquals(3, test.toInt('D'));

        assertEquals('B', test.toChar(1));
        assertEquals('A', test.toChar(0));
    }

    @Test
    public void testMoreComplicated() {
       String testString = "abcdefzyxw1092";
       Alphabet ctest = new Alphabet(testString);
       assertEquals(14, ctest.size());

       for (int i = 0; i < testString.length(); i++) {
           char curr = testString.charAt(i);
           assertEquals(curr, ctest.toChar(i));
           assertEquals(i, ctest.toInt(curr));
           assertTrue(ctest.contains(curr));
       }
       assertFalse(ctest.contains('A'));
    }
}
