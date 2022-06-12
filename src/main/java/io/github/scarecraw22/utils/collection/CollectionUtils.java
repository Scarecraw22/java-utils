package io.github.scarecraw22.utils.collection;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.function.Consumer;

@Slf4j
@UtilityClass
public class CollectionUtils {

    public <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    public <T> boolean hasSize(Collection<T> collection, int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("Argument expectedSize should be greater or equal zero. Actual: " + expectedSize);
        }
        boolean isEmpty = isEmpty(collection);
        boolean expectedSizeEqualsZero = expectedSize == 0;

        if (isEmpty && expectedSizeEqualsZero) {
            return true;
        } else if (isEmpty) {
            return false;
        }
        return collection.size() == expectedSize;
    }

    public <T> void applyForEach(Collection<T> collection, Consumer<T> consumer) {
        if (isEmpty(collection)) {
            log.warn("Given collection is empty. Cannot apply forEach()");
            return;
        }
        collection.forEach(consumer);
    }
}
