package dev.grevend.count;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UtilsTest {

    @Test
    public void testLinesNullReader() {
        assertThat(Utils.lines(null).count()).isZero();
    }

    @Test
    public void testLinesHasNext() throws IOException {
        var reader = mock(BufferedReader.class);
        var values = List.of("first", "second").iterator();
        when(reader.readLine()).thenAnswer(invocation -> values.hasNext() ? values.next() : null);
        var iter = Utils.lines(reader).iterator();
        assertThat(iter.hasNext()).isTrue();
        iter.next();
        assertThat(iter.hasNext()).isTrue();
        iter.next();
        assertThat(iter.hasNext()).isFalse();
    }

    @Test
    public void testLinesHasNextBlankLine() throws IOException {
        var reader = mock(BufferedReader.class);
        var values = List.of("first", " ").iterator();
        when(reader.readLine()).thenAnswer(invocation -> values.hasNext() ? values.next() : null);
        var iter = Utils.lines(reader).iterator();
        assertThat(iter.hasNext()).isTrue();
        iter.next();
        assertThat(iter.hasNext()).isFalse();
    }

    @Test
    public void testLinesHasNextUncheckedIOException() throws IOException {
        var reader = mock(BufferedReader.class);
        when(reader.readLine()).thenThrow(new IOException("test"));
        var iter = Utils.lines(reader).iterator();
        assertThatThrownBy(iter::hasNext).isExactlyInstanceOf(UncheckedIOException.class).describedAs("test");
    }

    @Test
    public void testLinesNext() throws IOException {
        var reader = mock(BufferedReader.class);
        var values = List.of("first", "second").iterator();
        when(reader.readLine()).thenAnswer(invocation -> values.hasNext() ? values.next() : null);
        var iter = Utils.lines(reader).iterator();
        assertThat(iter.next()).isEqualTo("first");
        assertThat(iter.next()).isEqualTo("second");
    }

    @Test
    public void testLinesNextNoSuchElementException() throws IOException {
        var reader = mock(BufferedReader.class);
        var values = List.of("first").iterator();
        when(reader.readLine()).thenAnswer(invocation -> values.hasNext() ? values.next() : null);
        var iter = Utils.lines(reader).iterator();
        iter.next();
        assertThatThrownBy(iter::next).isExactlyInstanceOf(NoSuchElementException.class);
    }

}
