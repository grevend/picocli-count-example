package dev.grevend.count;

import java.util.function.ToIntFunction;

public enum CountingMethods implements ToIntFunction<String> {

    chars() {

        @Override
        public int applyAsInt(String line) {
            return 0;
        }

    },
    words() {

        @Override
        public int applyAsInt(String value) {
            return 0;
        }

    },
    lines() {
        
        @Override
        public int applyAsInt(String value) {
            return 0;
        }

    }

}
