package io.github.scarecraw22.utils.string;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public static StringOperations of(@NonNull String input) {
        return new StringOperations(input);
    }

    @RequiredArgsConstructor
    public static final class StringOperations {

        private final String input;

        public String getFirstNChars(int n) {
            if (input.length() < n) {
                throw new IllegalArgumentException("Cannot retrieve first " + n + " chars from string: \"" + input + "\". String is too short.");
            } else if (n <= 0) {
                throw new IllegalArgumentException("Given n must be positive. Current value of n: " + n);
            }
            return input.substring(0, n);
        }

        public String getFirstChar() {
            return getFirstNChars(1);
        }

        public String getLastNChars(int n) {
            if (input.length() < n) {
                throw new IllegalArgumentException("Cannot retrieve last " + n + " chars from string: \"" + input + "\". String is too short.");
            } else if (n <= 0) {
                throw new IllegalArgumentException("Given n must be positive. Current value of n: " + n);
            }
            return input.substring(input.length() - n);
        }

        public String getLastChar() {
            return getLastNChars(1);
        }

        public boolean isEmptyOrBlank() {
            return input.isEmpty() || input.isBlank();
        }

        public boolean isValueSet() {
            return !isEmptyOrBlank();
        }
    }
}
