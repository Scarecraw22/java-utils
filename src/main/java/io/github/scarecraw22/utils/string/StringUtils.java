package io.github.scarecraw22.utils.string;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class StringUtils {

    @NotNull
    public String getFirstNChars(@NotNull(value = "input cannot be null", exception = StringUtilsException.class) String input, int n) {
        if (input.length() < n) {
            throw new StringUtilsException("Cannot retrieve first " + n + " chars from string: \"" + input + "\". String is too short.");
        } else if (n <= 0) {
            throw new StringUtilsException("Given n must be positive. Current value of n: " + n);
        }
        return input.substring(0, n);
    }

    @NotNull
    public String getFirstChar(@NotNull(value = "input cannot be null", exception = StringUtilsException.class) String input) {
        return getFirstNChars(input, 1);
    }

    @NotNull
    public String getLastNChars(@NotNull(value = "input cannot be null", exception = StringUtilsException.class) String input, int n) {
        if (input.length() < n) {
            throw new StringUtilsException("Cannot retrieve last " + n + " chars from string: \"" + input + "\". String is too short.");
        } else if (n <= 0) {
            throw new StringUtilsException("Given n must be positive. Current value of n: " + n);
        }
        return input.substring(input.length() - n);
    }

    @NotNull
    public String getLastChar(@NotNull(value = "input cannot be null", exception = StringUtilsException.class) String input) {
        return getLastNChars(input, 1);
    }
}
