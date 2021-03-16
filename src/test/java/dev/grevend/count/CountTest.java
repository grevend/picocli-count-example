package dev.grevend.count;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountTest {

    @Test
    public void testHelp() {
        var commandLine = new TestCommandLine("-h");
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).contains("Count the human-readable characters, lines, or" +
            " words from stdin or a file and" + System.lineSeparator() + "write the number to stdout or a file.");
    }

    @Test
    public void testHumanReadableCount() {
        var commandLine = new TestCommandLine(new String[]{}, new String[]{"test"});
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).startsWith("4");
    }

}
