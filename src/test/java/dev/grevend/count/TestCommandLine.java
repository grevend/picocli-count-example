package dev.grevend.count;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

import static org.mockito.Mockito.*;

/**
 * A testable CommandLine object.
 *
 * @since sprint 1
 */
public final class TestCommandLine {

    private final String[] args;
    private final CommandLine commandLine;
    private final StringWriter out, err;

    /**
     * Constructs a test CommandLine with the provided args.
     *
     * @param args the test command args
     *
     * @since sprint 1
     */
    protected TestCommandLine(String... args) {
        this.args = args;
        this.commandLine = new CommandLine(new Count());
        this.out = new StringWriter();
        this.err = new StringWriter();
        this.commandLine.setOut(new PrintWriter(this.out));
        this.commandLine.setErr(new PrintWriter(this.err));
    }

    /**
     * Constructs a test CommandLine with the provided args and a mocked input stream.
     *
     * @param args  the test command args
     * @param input the test command input
     *
     * @since sprint 1
     */
    protected TestCommandLine(String[] args, String[] input) {
        this.args = args;
        var reader = mock(BufferedReader.class);
        var countSpy = spy(new Count());
        try {
            when(countSpy.in()).thenReturn(reader);
            var iter = Arrays.asList(input).iterator();
            when(reader.readLine()).thenAnswer(invocation -> iter.hasNext() ? iter.next() : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.commandLine = new CommandLine(countSpy);
        this.out = new StringWriter();
        this.err = new StringWriter();
        this.commandLine.setOut(new PrintWriter(this.out));
        this.commandLine.setErr(new PrintWriter(this.err));
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
     * Returns the constructed StringWriter representing the test error stream.
     *
     * @return the error stream as a StringWriter
     *
     * @since sprint 1
     */
    public StringWriter err() {
        return err;
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
