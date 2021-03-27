package dev.grevend.count;

import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Miscellaneous count command implementation utilities.
 *
 * @since sprint 1
 */
public final class Utils {

    /**
     * Returns a lazily populated {@code Stream} from the given {@code BufferedReader}.
     *
     * @param reader the {@code BufferedReader} providing the text
     *
     * @return a {@code Stream<String>} providing the lines of text until the line is blank or EOF is reached
     *
     * @since sprint 1
     */
    public static Stream<String> lines(BufferedReader reader) {
        return reader != null ? StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<>() {
            private String nextLine = null;

            @Override
            public boolean hasNext() {
                try {
                    return nextLine != null || ((nextLine = reader.readLine()) != null && !nextLine.isBlank());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public String next() {
                if (nextLine != null || hasNext()) {
                    String line = nextLine;
                    nextLine = null;
                    return line;
                } else {
                    throw new NoSuchElementException();
                }
            }
        }, Spliterator.ORDERED | Spliterator.NONNULL), false) : Stream.empty();
    }

    /**
     * Returns whether the given {@code File} exists.
     *
     * @param file the {@code File} to check
     * @param err  the {@code PrintWriter} provided for error output
     *
     * @return if the {@code File} exists
     *
     * @since sprint 2
     */
    public static boolean exists(File file, PrintWriter err) {
        var does = file != null && file.exists() && !file.isDirectory();
        if (!does && file != null) {
            if (file.isDirectory()) {
                err.println("Output path points to a directory!");
                return false;
            } else {
                try {
                    if (!file.exists() && !file.createNewFile()) {
                        throw new IOException();
                    } else {
                        return true;
                    }
                } catch (IOException | SecurityException e) {
                    err.println("Failed to create output file!");
                    return false;
                }
            }
        }
        return does;
    }

    /**
     * Returns whether the given {@code File} is writable.
     *
     * @param file the {@code File} to check
     * @param err  the {@code PrintWriter} provided for error output
     *
     * @return if the {@code File} is writable
     *
     * @since sprint 2
     */
    public static boolean writable(File file, PrintWriter err) {
        var is = exists(file, err) && file != null && Files.isWritable(file.toPath());
        if (!is && file != null && file.exists()) {
            err.println("Output file is read-only!");
        }
        return is;
    }

}
