package dev.grevend.count;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public enum CountingMethods implements Function<String, IntStream> {

    chars() {

        /**
         * Computes the character counts / lengths of all matched groups based on the humanReadable pattern.
         *
         * @param line the current text chunk
         * @return an int stream of counts
         * @since sprint 1
         */
        @Override
        public IntStream apply(String line) {
            return humanReadable.matcher(line).results().map(MatchResult::group).mapToInt(String::length);
        }

    }, words() {

        @Override
        public IntStream apply(String line) {
            return IntStream.empty();
        }

    }, lines() {

        @Override
        public IntStream apply(String line) {
            return IntStream.of(line.isBlank() ? 0 : 1);
        }

    };

    private static final Pattern humanReadable = Pattern.compile("[^\\p{C}\\p{Z}]+", Pattern.UNICODE_CHARACTER_CLASS);

}
