package dev.grevend.count;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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

    @Test
    public void testExistsWithoutFile() {
        assertThat(Utils.exists(null, null)).isFalse();
    }

    @Test
    public void testExistsWithNonexistentFile() {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.exists()).thenReturn(false);
        assertThat(Utils.exists(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Failed to create output file!");
    }

    @Test
    public void testExistsWithDirectory() {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        assertThat(Utils.exists(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Output path points to a directory!");
    }

    @Test
    public void testExistsFailureOnCreation() throws IOException {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.createNewFile()).thenReturn(false);
        assertThat(Utils.exists(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Failed to create output file!");
    }

    @Test
    public void testExistsSuccessOnCreation() throws IOException {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.createNewFile()).thenReturn(true);
        assertThat(Utils.exists(file, new PrintWriter(writer))).isTrue();
        assertThat(writer.toString().strip()).isBlank();
    }

    @Test
    public void testWritableWithoutFile() {
        assertThat(Utils.writable(null, mock(PrintWriter.class))).isFalse();
    }

    @Test
    public void testWritableWithNonexistentFile() {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.exists()).thenReturn(false);
        assertThat(Utils.writable(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Failed to create output file!");
    }

    @Test
    public void testWritableWithDirectory() {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.isDirectory()).thenReturn(true);
        assertThat(Utils.writable(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Output path points to a directory!");
    }

    @Test
    public void testWritableFailureOnCreation() throws IOException {
        var writer = new StringWriter();
        var file = mock(File.class);
        when(file.exists()).thenReturn(false);
        when(file.createNewFile()).thenReturn(false);
        assertThat(Utils.writable(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Failed to create output file!");
    }

    @Test
    public void testWritableSuccessOnCreation() {
        var writer = new StringWriter();
        var file = new File("./test-writable.txt");
        assertThat(Utils.writable(file, new PrintWriter(writer))).isTrue();
        assertThat(writer.toString().strip()).isBlank();
        assertThat(file.delete()).isTrue();
        file.deleteOnExit();
    }

    @Test
    public void testWritableReadOnlyFile() throws IOException {
        var writer = new StringWriter();
        var file = new File("./test-read-only.txt");
        assertThat(file.createNewFile()).isTrue();
        assertThat(file.setReadOnly()).isTrue();
        assertThat(Utils.writable(file, new PrintWriter(writer))).isFalse();
        assertThat(writer.toString().strip()).isEqualTo("Output file is read-only!");
        assertThat(file.delete()).isTrue();
        file.deleteOnExit();
    }

}
