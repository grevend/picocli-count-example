package dev.grevend.count;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Utils {

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
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<>() {
            private String nextLine = null;

            @Override
            public boolean hasNext() {
                if (nextLine == null) {
                    try {
                        return ((nextLine = reader.readLine()) != null && !nextLine.isBlank());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
                return true;
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
        }, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }

}
