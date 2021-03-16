package dev.grevend.count;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.io.*;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;

/**
 * The count command entry class.
 *
 * @since sprint 1
 */
@Command(name = "count", version = "count 1.0", mixinStandardHelpOptions = true,
    description = "Count the human-readable characters, lines, or words from stdin or a file and write the number to stdout or a file.")
public class Count implements Callable<Integer> {

    @Spec
    private CommandSpec spec;

    /**
     * Main program entry point. Creates a command line instance and delegates the given program args to the count command.
     *
     * @param args the supplied program args
     *
     * @since sprint 1
     */
    public static void main(String[] args) {
        System.exit(new CommandLine(new Count()).execute(args));
    }

    /**
     * Reads text from the selected input stream, computes the character count, and prints the value into the specified output stream.
     *
     * @return the command exit code
     *
     * @throws IOException if the input or output stream cannot be closed
     * @since sprint 1
     */
    @Override
    public Integer call() throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(in())); var out = out()) {
            for (String line = reader.readLine(); line != null && !line.isEmpty(); line = reader.readLine()) {
                out.println(line);
            }
        }
        return 0;
    }

    /**
     * Returns or constructs an input stream based on the selected command options.
     *
     * @return the input stream
     *
     * @since sprint 1
     */
    private InputStream in() {
        return System.in;
    }

    /**
     * Returns or constructs an output stream based on the selected command options.
     *
     * @return the output stream
     *
     * @since sprint 1
     */
    private PrintWriter out() {
        return spec.commandLine().getOut();
    }

}
