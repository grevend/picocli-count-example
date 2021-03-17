package dev.grevend.count;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.LongStream;

public enum CountingMethods implements Function<String, LongStream> {

    chars() {

        /**
         * Computes the character counts / lengths of all matched groups based on the humanReadable pattern.
         *
         * @param line the current text chunk
         * @return a long stream of counts
         *
         * @since sprint 1
         */
        @Override
        public LongStream apply(String line) {
            return humanReadable.matcher(line).results().map(MatchResult::group).mapToLong(String::length);
        }

    }, words() {

        /**
         * Computes the word count of the given text.
         *
         * @param line the current text chunk
         * @return a long stream containing the word count
         *
         * @since sprint 1
         */
        @Override
        public LongStream apply(String line) {
            return LongStream.of(word.matcher(line).results().count());
        }

    }, lines() {

        /**
         * Computes the line count by checking if the text is blank.
         *
         * @param line the current text chunk
         * @return a long stream that contains zero if the chunk is blank or else 1
         *
         * @since sprint 1
         */
        @Override
        public LongStream apply(String line) {
            return LongStream.of(line.isBlank() ? 0 : 1);
        }

    };

    //strict word check: (\S|)[^\p{C}\p{Z}\p{S}\p{Ps}\p{Pe}\p{Pi}\p{Pf}\p{Pc}\p{Po}\p{N}]+(\S|)
    private static final Pattern humanReadable = Pattern.compile("[^\\p{C}\\p{Z}]+", Pattern.UNICODE_CHARACTER_CLASS),
        word = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS);

}
