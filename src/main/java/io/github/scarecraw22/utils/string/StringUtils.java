package io.github.scarecraw22.utils.string;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class StringUtils {

    @NotNull
    public String getFirstNChars(@NonNull @NotNull(value = "input cannot be null") String input, int n) {
        if (input.length() < n) {
            throw new IllegalArgumentException("Cannot retrieve first " + n + " chars from string: \"" + input + "\". String is too short.");
        } else if (n <= 0) {
            throw new IllegalArgumentException("Given n must be positive. Current value of n: " + n);
        }
        return input.substring(0, n);
    }

    @NotNull
    public String getFirstChar(@NonNull @NotNull(value = "input cannot be null") String input) {
        return getFirstNChars(input, 1);
    }

    @NotNull
    public String getLastNChars(@NonNull @NotNull(value = "input cannot be null") String input, int n) {
        if (input.length() < n) {
            throw new IllegalArgumentException("Cannot retrieve last " + n + " chars from string: \"" + input + "\". String is too short.");
        } else if (n <= 0) {
            throw new IllegalArgumentException("Given n must be positive. Current value of n: " + n);
        }
        return input.substring(input.length() - n);
    }

    @NotNull
    public String getLastChar(@NonNull @NotNull(value = "input cannot be null") String input) {
        return getLastNChars(input, 1);
    }
}
