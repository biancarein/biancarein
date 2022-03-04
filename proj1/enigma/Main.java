package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.*;

import net.sf.saxon.pattern.SchemaNodeTest;
import ucb.util.CommandArgs;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author
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
            String next_line = _input.nextLine();
            if (next_line.startsWith("*")) {
                setUp(m, next_line);
            } else {
                printMessageLine(m.convert(next_line));
            }
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String alpha = _config.next();
            _alphabet = new Alphabet(alpha);
            int num_rotors = _config.nextInt();
            int num_pawls = _config.nextInt();
            Collection<Rotor> _allRotors = new ArrayList<>();
            while (_config.hasNextLine()) {
                _allRotors.add(readRotor());
                _config.nextLine();
            }
            return new Machine(_alphabet, num_rotors, num_pawls, _allRotors);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            String rotor_name = _config.next();
            String type = _config.next();
            String notches = type.substring(1);
            String permutation = "";
            while (_config.hasNext("[^A-Za-z\\d|\\s][A-Z]+[^A-Za-z\\d|\\s]")) {
                permutation += _config.next() + " ";
            }
            Permutation perm = new Permutation(permutation, _alphabet);
            Rotor rotor_return;
            if (type.charAt(0) == 'M') {
                rotor_return = new MovingRotor(rotor_name, perm, notches);
            } else if (type.charAt(0) == 'N') {
                rotor_return = new FixedRotor(rotor_name, perm);
            } else if (type.charAt(0) == 'R'){
                rotor_return = new Reflector(rotor_name, perm);
            } else {
                throw error("This type does not exist!");
            }
            return rotor_return;
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
        String[] s_line = settings.split("\\s+");
        String[] rotor_names = new String[M.numRotors()];
        String settings_line = s_line[M.numRotors() + 1];
        String p_line = "";
        for (int i = 1; i < M.numRotors() + 1; i++) {
            rotor_names[i - 1] = s_line[i];
        }
        for (int j = M.numRotors() + 2; j < s_line.length; j++) {
            p_line += s_line[j];
        }
        Permutation p_perm = new Permutation(p_line, _alphabet);
        M.insertRotors(rotor_names);
        M.setRotors(settings_line);
        M.setPlugboard(p_perm);
    }

    /** Return true iff verbose option specified. */
    static boolean verbose() {
        return _verbose;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        String msg_copy = msg;
        String msg_new = "";
        int div = msg.length() / 5;
        for (int i = 0; i < div; i++) {
            msg_new += msg_copy.substring(0,5) + " ";
            msg_copy = msg_copy.substring(5);
        }
        msg_new += msg_copy;
        _output.println(msg_new);
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
