package dev.grevend.count;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

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

    private final Pattern humanReadable = Pattern.compile("[^\\p{C}\\p{Z}]+", Pattern.UNICODE_CHARACTER_CLASS);

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
        try (var reader = in(); var out = out()) {
            var count = 0;
            for (String line = reader.readLine(); line != null && !line.isBlank(); line = reader.readLine()) {
                count += humanReadable.matcher(line.strip()).results()
                    .map(MatchResult::group)
                    .map(String::length)
                    .reduce(0, Integer::sum);
            }
            out.println(count);
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
    protected BufferedReader in() {
        return new BufferedReader(new InputStreamReader(System.in));
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
