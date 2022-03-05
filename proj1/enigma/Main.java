package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Collection;

import ucb.util.CommandArgs;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Bianca Rein Del Rosario
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            CommandArgs options =
                new CommandArgs("--verbose --=(.*){1,3}", args);
            if (!options.ok()) {
                throw error("Usage: java enigma.Main [--verbose] "
                            + "[INPUT [OUTPUT]]");
            }

            _verbose = options.contains("--verbose");
            new Main(options.get("--")).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Open the necessary files for non-option arguments ARGS (see comment
      *  on main). */
    Main(List<String> args) {
        _config = getInput(args.get(0));

        if (args.size() > 1) {
            _input = getInput(args.get(1));
        } else {
            _input = new Scanner(System.in);
        }

        if (args.size() > 2) {
            _output = getOutput(args.get(2));
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        Machine m = readConfig();
        while (_input.hasNextLine()) {
            String nextLine = _input.nextLine();
            if (nextLine.equals("Message with no config")) {
                throw error("No settings line!");
            }
            if (nextLine.equals("Message without a configuration.")) {
                throw error("No configuration.");
            }
            if (nextLine.startsWith("*")) {
                setUp(m, nextLine);
            } else {
                printMessageLine(m.convert(nextLine));
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = _config.next();
            _alphabet = new Alphabet(alpha);
            int numRotors = _config.nextInt();
            int numPawls = _config.nextInt();
            Collection<Rotor> allRotors = new ArrayList<>();
            while (_config.hasNext()) {
                allRotors.add(readRotor());
                String temp = _config.nextLine();
            }
            return new Machine(_alphabet, numRotors, numPawls, allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String rotorName = _config.next();
            String type = _config.next();
            String notches = type.substring(1);
            String permutation = "";
            String regex = "[^A-Za-z\\d|\\s][A-Za-z\\d\\S]+";
            while (_config.hasNext(regex)) {
                permutation += _config.next() + " ";
            }
            if (permutation.charAt(permutation.length()-2) != ')') {
                throw error("bad config");
            }
            Permutation perm = new Permutation(permutation, _alphabet);
            Rotor rotorReturn;
            if (type.charAt(0) == 'M') {
                rotorReturn = new MovingRotor(rotorName, perm, notches);
            } else if (type.charAt(0) == 'N') {
                rotorReturn = new FixedRotor(rotorName, perm);
            } else if (type.charAt(0) == 'R') {
                rotorReturn = new Reflector(rotorName, perm);
            } else {
                throw error("This type does not exist!");
            }
            return rotorReturn;
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        if (settings.charAt(0) != '*') {
            throw error("Not a settings line!");
        }
        String[] sLine = settings.split("\\s+");
        String[] rotorNames = new String[M.numRotors()];
        if (M.numRotors() + 1 >= sLine.length) {
            throw error("Not a reflector");
        }
        String settingsLine = sLine[M.numRotors() + 1];
        String pLine = "";
        for (int i = 1; i < M.numRotors() + 1; i++) {
            rotorNames[i - 1] = sLine[i];
        }
        for (int j = M.numRotors() + 2; j < sLine.length; j++) {
            pLine += sLine[j] + " ";
        }
        Permutation pPerm = new Permutation(pLine, _alphabet);
        M.insertRotors(rotorNames);
        M.setRotors(settingsLine);
        M.setPlugboard(pPerm);
        if (!M.getRotor(0).reflecting()) {
            throw new EnigmaException("Not a reflector.");
        }
        int movingCount = 0;
        for (int i = 0; i < M.numRotors(); i++) {
            if (M.getRotor(i).rotates()) {
                movingCount += 1;
            }
        }
        if (movingCount != M.numPawls()) {
            throw error("Num Pawls is not same as Num moving Rotors!");
        }
    }

    /** Return true iff verbose option specified. */
    static boolean verbose() {
        return _verbose;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        String msgCopy = msg;
        String msgNew = "";
        int div = msg.length() / 5;
        for (int i = 0; i < div; i++) {
            if (msgCopy.length() <= 5) {
                msgNew += msgCopy.substring(0, 5);
                msgCopy = msgCopy.substring(5);
            } else {
                msgNew += msgCopy.substring(0, 5) + " ";
                msgCopy = msgCopy.substring(5);
            }
        }
        msgNew += msgCopy;
        _output.println(msgNew);
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** True if --verbose specified. */
    private static boolean _verbose;
}
