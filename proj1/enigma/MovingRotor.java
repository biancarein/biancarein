package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Bianca Rein Del Rosario
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
    boolean rotates() {
        return true;
    }

    @Override
    void advance() {
        _setting += 1;
        if (_setting == alphabet().size()) {
            _setting = 0;
        }
    }
    // ask in OH about this method

    @Override
    boolean atNotch() {
        for (int i = 0; i < notches().length(); i++) {
            if (notches().charAt(i) == alphabet().toChar(setting())){
                return true;
            }
        }
        return false;
    }

    @Override
    String notches() {
        return _notches;
    }

    /** A String of my notches. */
    public String _notches;

}
