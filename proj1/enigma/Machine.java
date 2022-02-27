package enigma;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

import static enigma.EnigmaException.*;

/** Class that represents a complete enigma machine.
 *  @author
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors. */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _allRotors = allRotors;
        _CopyAllRotors = allRotors;
        if (numRotors < 0) {
            throw error("Error: Number of rotors cannot be less than 0.");
        }
        if (pawls < 0) {
            throw  error("Error: Number of pawls cannot be less than 0.");
        }
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return  _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Return Rotor #K, where Rotor #0 is the reflector, and Rotor
     *  #(numRotors()-1) is the fast Rotor.  Modifying this Rotor has
     *  undefined results. */
    Rotor getRotor(int k) {
        _CopyAllRotors = _allRotors;
        for (int i = 0; i <= k; i++){
            if(_CopyAllRotors.iterator().hasNext()) {
                _CopyAllRotors.iterator().next();
            }
        }
        return _CopyAllRotors.iterator().next();
    }

    Alphabet alphabet() {
        return _alphabet;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        //for loop through the iterator
        // rotors.charat(i)
        // add the string index to the rotor array
        _CopyAllRotors = _allRotors;
        for (int i = 0; i < numRotors(); i++) {
            _allRotors.iterator().next();
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 characters in my alphabet. The first letter refers
     *  to the leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        // FIXME
    }

    /** Return the current plugboard's permutation. */
    Permutation plugboard() {
        return null; // FIXME
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        // FIXME
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing
     *  the machine. */
    int convert(int c) {
        advanceRotors();
        if (Main.verbose()) {
            System.err.printf("[");
            for (int r = 1; r < numRotors(); r += 1) {
                System.err.printf("%c",
                        alphabet().toChar(getRotor(r).setting()));
            }
            System.err.printf("] %c -> ", alphabet().toChar(c));
        }
        c = plugboard().permute(c);
        if (Main.verbose()) {
            System.err.printf("%c -> ", alphabet().toChar(c));
        }
        c = applyRotors(c);
        c = plugboard().permute(c);
        if (Main.verbose()) {
            System.err.printf("%c%n", alphabet().toChar(c));
        }
        return c;
    }

    /** Advance all rotors to their next position. */
    private void advanceRotors() {
        // FIXME
    }

    /** Return the result of applying the rotors to the character C (as an
     *  index in the range 0..alphabet size - 1). */
    private int applyRotors(int c) {
        return c; // FIXME
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        return ""; // FIXME
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;

    /** Number of Rotors. */
    public int _numRotors;

    /** Number of Pawls. */
    public int _pawls;

    /** Collection of all Rotors. */
    public Collection<Rotor> _allRotors;

    /** Copy Collection of all Rotors. */
    public Collection<Rotor> _CopyAllRotors;

    /** Array List of all Rotors that will be used/inserted. */
    public String[] _StringAllRotors;
}
