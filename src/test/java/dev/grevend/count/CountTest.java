package dev.grevend.count;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CountTest {

    @Test
    public void testHelp() {
        var commandLine = new TestCommandLine("-h");
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).contains("Count the human-readable characters, lines, or" +
            " words from stdin or a file and" + System.lineSeparator() + "write the number to stdout or a file.");
    }

    @ParameterizedTest
    @CsvSource({
        ",0", "\n,0", " ,0", "\t,0",
        "a,1", "ab,2", "a b,2", "ab c,3", "a-b,3", "a_b,3",
        "1,1", "1234567890,10",
        "‰,1", "∠,1", "≅,1", "∑,1", "A⊗B,3", "∄,1",
        "!§$%&/()[]{}`´*+~#'-_:.;<>|^°€µ,31",
        "ü,1", "é,1",
        "тест,4",
        "امتحان,6",
        "汉字,2", "漢字,2", "Hán tự,5", "漢字,2", "\uD876\uDE21倱,3", "한자,2", "漢字,2", "漢字,2", "かんじ,3",
    })
    public void testHumanReadableCount(String input, String count) {
        var commandLine = new TestCommandLine(new String[]{}, new String[]{input});
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).startsWith(count);
    }

}
