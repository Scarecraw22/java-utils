package io.github.scarecraw22.utils.string;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtils {

    public String getFirstNChars(@NonNull String input, int n) {
        if (input.length() < n) {
            throw new IllegalArgumentException("Cannot retrieve first " + n + " chars from string: \"" + input + "\". String is too short.");
        } else if (n <= 0) {
            throw new IllegalArgumentException("Given n must be positive. Current value of n: " + n);
        }
        return input.substring(0, n);
    }

    public String getFirstChar(@NonNull String input) {
        return getFirstNChars(input, 1);
    }

    public String getLastNChars(@NonNull String input, int n) {
        if (input.length() < n) {
            throw new IllegalArgumentException("Cannot retrieve last " + n + " chars from string: \"" + input + "\". String is too short.");
        } else if (n <= 0) {
            throw new IllegalArgumentException("Given n must be positive. Current value of n: " + n);
        }
        return input.substring(input.length() - n);
    }

    public String getLastChar(@NonNull String input) {
        return getLastNChars(input, 1);
    }

    public boolean isEmptyOrBlank(String input) {
        return input == null || input.isEmpty() || input.isBlank();
    }

    public boolean isValueSet(String input) {
        return !isEmptyOrBlank(input);
    }
}
