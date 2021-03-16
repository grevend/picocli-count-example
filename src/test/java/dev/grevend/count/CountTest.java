package dev.grevend.count;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CountTest {

    @Test
    public void testStdin() throws IOException {
        var commandLine = new TestCommandLine(new String[]{}, new String[]{"test"});
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).isEqualTo("test" + System.lineSeparator());
    }

}
