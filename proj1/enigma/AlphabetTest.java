package enigma;

import org.junit.Test;
import static org.junit.Assert.*;

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
}
