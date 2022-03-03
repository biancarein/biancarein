package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
        // we need to process the .conf file to build out the machine
            // call readConfig for this
        // parse the .in file [need to be a for loop of some sort]
            // use setUp to process each settings line of the .in file
            // use printMessageLine to encrypt each message line of the .in file
        // there can be multiple settings lines and messages in one .in file
        readConfig();
        // for loop through the .in file
        // somehow grab the line
        // call set up on that string line
        while (_input.hasNextLine()) {
            String str = _input.next();
            printMessageLine(str);
        }
    }

    /** Return an Enigma machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            // error check
            // FIXME
            //helper method - in order to break down the parsing of the .conf file into individual parts
            // parsing thorugh the entire .conf file
            // you should call readRotor

            // pull the alphabet, number of rotors, and number of pawls from the .conf file
            // start to reach each rotor description
                // this can be abstracted by calling readRotor for each rotor line in .conf file
                // think about how you can use .hasNect or hasNextline to come up with a loop condition that allows you to call readRotor on every rotor description line
            // return a new machine object containing all of the components that you just parsed from the .conf file
            _alphabet = new Alphabet();
            return new Machine(_alphabet, 2, 1, null);
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            return null; // FIXME
            // helper method that was created in order to break down the parsing of the .conf file into individual parts
            // designed to read one "rotor line" from the .conf file and return a rotor object with the specified cycles, notches, etcs.
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        // FIXME
        // designed to parse through the .in file
        // set up a machine object that we created in readconfig with the settings line in .in file
    }

    /** Return true iff verbose option specified. */
    static boolean verbose() {
        return _verbose;
    }

    /** Print MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private void printMessageLine(String msg) {
        // FIXME
        // designed to parse through the .in file
        String msg_new = "";
        for (int i = 0; i < msg.length(); i++) {

        }
        // for each line of message input in .in file
        // encrypt the line
        // store it in the _output printstream variable
        // look at Printstream API for methods that you can use to print something to the _output PrintStream
    }

    /** Alphabet used in this machine. */
    private Alphabet _alphabet;

    /** Source of input messages. */
    private Scanner _input;
    // traverses the .in file that was passed into the program
    // what we want to encrypt

    /** Source of machine configuration. */
    private Scanner _config;
    // traverses the .conf file that was passed into the program
    // the settings and info of the enigma machine

    /** File for encoded/decoded messages. */
    private PrintStream _output;
    // represents the cursor in the .out file, which can be written to
    // the file of the encrypted message

    /** True if --verbose specified. */
    private static boolean _verbose;

    // use scanner objects to parse through the .conf and .in files
    // methods suchs as .next .hasnext and .nextline
}
