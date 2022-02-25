package enigma;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;

import static enigma.TestUtils.*;

/** The suit of all JUnit tests for the Rotor class.
 * @author Bianca Del Rosario
 */

public class RotorTest {

    @Test
    public void testRotor() {
        Permutation p = new Permutation("(BACD)", UPPER);
        Rotor r = new Rotor("I", p);
    }
}
