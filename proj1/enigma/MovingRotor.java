package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    @Override
    void advance() {
        for (int i = 0; i < notches().length(); i ++) {
            if (notches().charAt(i) == alphabet().toChar(_setting)){
                _setting += 1;
            }
        }
    }

    @Override
    String notches() {
        return _notches;
    }

    // FIXME: ADDITIONAL FIELDS HERE, AS NEEDED
    /** A String of my notches. */
    public String _notches;

}
