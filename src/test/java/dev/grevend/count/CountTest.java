package dev.grevend.count;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.of;

public class CountTest {

    @Test
    public void testHelp() {
        var commandLine = new TestCommandLine("-h");
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).contains("Count the human-readable characters, lines, or" +
            " words from stdin or a file and" + System.lineSeparator() + "write the number to stdout or a file.");
    }

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
    @ParameterizedTest
    public void testHumanReadableCount(String input, String count) {
        var commandLine = new TestCommandLine(new String[]{"-m", "chars"}, new String[]{input});
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).startsWith(count);
    }

    @ParameterizedTest
    @ValueSource(strings = {"\u0000", "\u0003", "\u001C", "\u0080", "\u0091", "\u0020", "\u2072", "\uFB07", "\uFB10"})
    public void testNonHumanReadableCount(String input) {
        var commandLine = new TestCommandLine(new String[]{"-m", "chars"}, new String[]{input});
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).startsWith("0");
    }

    private static Stream<Arguments> testLines() {
        return Stream.of(
            of(new String[]{}, "0"),
            of(new String[]{""}, "0"),
            of(new String[]{" "}, "0"),
            of(new String[]{"first"}, "1"),
            of(new String[]{"first", "second"}, "2")
        );
    }

    @MethodSource(value = "testLines")
    @ParameterizedTest
    public void testLineCount(String[] input, String count) {
        var commandLine = new TestCommandLine(new String[]{"-m", "lines"}, input);
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).startsWith(count);
    }

    @CsvSource({
        ",0", "\n,0", " ,0", "\t,0",
        "a,1", "ab,1", "a b,2", "ab c,2", "a-b,2", "a_b,1",
        "1,1", "1234567890,1", "-0,1",
        "‰,0", "∠,0", "≅,0", "∑,0", "∄,0",
        "!§$%&/()[]{}`´*+~#':.;<>|^°€,0",
        "ü,1", "é,1",
        "тест,1",
        "امتحان,1",
        "汉字,1", "漢字,1", "Hán tự,2", "漢字,1", "\uD876\uDE21倱,1", "한자,1", "漢字,1", "漢字,1", "かんじ,1",
    })
    @ParameterizedTest
    public void testWordCount(String input, String count) {
        var commandLine = new TestCommandLine(new String[]{"-m", "words"}, new String[]{input});
        assertThat(commandLine.execute()).isZero();
        assertThat(commandLine.out().toString()).startsWith(count);
    }

}
