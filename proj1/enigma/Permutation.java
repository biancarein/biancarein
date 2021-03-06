package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Bianca Rein Del Rosario
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cyclesString = cycles.replace("(", "").replace(")", " ");
        _cyclesArray = _cyclesString.split("\\s+");
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {
        String[] newCycleArr = new String[_cyclesArray.length + 1];
        for (int i = 0; i < _cyclesArray.length; i++) {
            newCycleArr[i] = _cyclesArray[i];
        }
        newCycleArr[_cyclesArray.length] = cycle;
        _cyclesArray = newCycleArr;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        char currChar = alphabet().toChar(wrap(p));
        char permChar = alphabet().toChar(wrap(p));
        for (int i = 0; i < _cyclesArray.length; i++) {
            for (int j = 0; j < _cyclesArray[i].length(); j++) {
                if (currChar == _cyclesArray[i].charAt(j)) {
                    if (j == _cyclesArray[i].length() - 1) {
                        permChar = _cyclesArray[i].charAt(0);
                    } else {
                        permChar = _cyclesArray[i].charAt(j + 1);
                    }
                }
            }
        }
        return alphabet().toInt(permChar);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        char currChar = alphabet().toChar(wrap(c));
        char permChar = alphabet().toChar(wrap(c));
        for (int i = 0; i < _cyclesArray.length; i++) {
            for (int j = 0; j < _cyclesArray[i].length(); j++) {
                if (currChar == _cyclesArray[i].charAt(wrap(j))) {
                    if (j == 0) {
                        int p = _cyclesArray[i].length() - 1;
                        permChar = _cyclesArray[i].charAt(p);
                    } else {
                        permChar = _cyclesArray[i].charAt(j - 1);
                    }
                }
            }
        }
        return alphabet().toInt(permChar);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        if (!alphabet().contains(p)) {
            throw error("Character not in alphabet.");
        }
        return alphabet().toChar(permute(alphabet().toInt(p)));
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        if (!alphabet().contains(c)) {
            throw error("Character not in alphabet.");
        }
        return alphabet().toChar(invert(alphabet().toInt(c)));
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        char atElem = 0;
        for (int i = 0; i < alphabet().size(); i++) {
            atElem = alphabet().toChar(i);
            if (atElem == permute(atElem)) {
                return false;
            } else if (atElem == invert(atElem)) {
                return false;
            }
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;

    /** Cycles in array form. */
    private String[] _cyclesArray;

    /** Cycles in String form. */
    private String _cyclesString;
}
