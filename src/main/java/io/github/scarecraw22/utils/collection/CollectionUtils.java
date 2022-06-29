package io.github.scarecraw22.utils.collection;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@UtilityClass
public class CollectionUtils {

    public <T> CollectionOperations<T> of(Collection<T> collection) {
        return new CollectionOperations<>(collection);
    }

    @RequiredArgsConstructor
    public static final class CollectionOperations<T> {

        private final Collection<T> collection;

        public boolean isEmpty() {
            return collection == null || collection.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public boolean hasSize(int expectedSize) {
            if (expectedSize < 0) {
                throw new IllegalArgumentException("Argument expectedSize should be greater or equal zero. Actual: " + expectedSize);
            }
            boolean isEmpty = isEmpty();
            boolean expectedSizeEqualsZero = expectedSize == 0;

            if (isEmpty && expectedSizeEqualsZero) {
                return true;
            } else if (isEmpty) {
                return false;
            }
            return collection.size() == expectedSize;
        }

        public void applyForEach(Consumer<T> consumer) {
            if (isEmpty()) {
                log.warn("Given collection is empty. Cannot apply forEach()");
                return;
            }
            collection.forEach(consumer);
        }

        public <OUT> List<OUT> mapThenToList(Function<T, OUT> mapFunction) {
            return Optional.ofNullable(collection)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(mapFunction)
                    .collect(Collectors.toList());
        }

        public <OUT> Set<OUT> mapThenToSet(Function<T, OUT> mapFunction) {
            return Optional.ofNullable(collection)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(mapFunction)
                    .collect(Collectors.toSet());
        }

        public Optional<T> findFirst() {
            return Optional.ofNullable(collection)
                    .orElse(Collections.emptyList())
                    .stream()
                    .findFirst();
        }
    }
}
