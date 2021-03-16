package dev.grevend.count;

import picocli.CommandLine;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * A testable CommandLine object.
 *
 * @since sprint 1
 */
public class TestCommandLine {

    private final String[] args;
    private final CommandLine commandLine;
    private final StringWriter out;

    /**
     * Constructs a test CommandLine with the provided args.
     *
     * @param args the test command args
     *
     * @since sprint 1
     */
    protected TestCommandLine(String[] args) {
        this.args = args;
        this.commandLine = new CommandLine(new Count());
        this.out = new StringWriter();
        this.commandLine.setOut(new PrintWriter(out));
    }

    /**
     * Executes the count command with the stored test args.
     *
     * @return the resulting exit code
     *
     * @since sprint 1
     */
    public int execute() {
        return commandLine.execute(args);
    }

    /**
     * Returns the constructed StringWriter representing the test output stream.
     *
     * @return the output stream as a StringWriter
     *
     * @since sprint 1
     */
    public StringWriter out() {
        return out;
    }

    /**
     * Returns the test CommandLine object.
     *
     * @return the test CommandLine
     *
     * @since sprint 1
     */
    public CommandLine commandLine() {
        return commandLine;
    }

    /**
     * Returns a joined version of the test args.
     *
     * @return the test display name / args
     *
     * @since sprint 1
     */
    @Override
    public String toString() {
        return String.join(" ", args);
    }

}
