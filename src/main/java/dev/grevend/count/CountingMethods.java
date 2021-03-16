package dev.grevend.count;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public enum CountingMethods implements Function<String, IntStream> {

    chars() {

        @Override
        public IntStream apply(String line) {
            return humanReadable.matcher(line.strip())
                .results()
                .map(MatchResult::group)
                .mapToInt(String::length);
        }

    },
    words() {

        @Override
        public IntStream apply(String line) {
            return IntStream.empty();
        }

    },
    lines() {

        @Override
        public IntStream apply(String line) {
            return IntStream.empty();
        }

    };

    private static final Pattern humanReadable = Pattern.compile("[^\\p{C}\\p{Z}]+", Pattern.UNICODE_CHARACTER_CLASS);

}
