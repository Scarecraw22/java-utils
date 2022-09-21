package io.github.scarecraw22.utils.thread;

import lombok.experimental.UtilityClass;

import java.time.Duration;

@UtilityClass
public class ThreadUtils {

    public void silentSleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new IllegalStateException("Error while silent sleep", e);
        }
    }
}