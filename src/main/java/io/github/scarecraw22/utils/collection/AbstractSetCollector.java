package io.github.scarecraw22.utils.collection;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

@RequiredArgsConstructor
public abstract class AbstractSetCollector<T, U> implements Collector<T, Set<T>, U> {

    protected final Function<Set<T>, U> finisher;

    @Override
    public Supplier<Set<T>> supplier() {
        return HashSet::new;
    }

    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        return Set::add;
    }

    @Override
    public BinaryOperator<Set<T>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }

    @Override
    public Function<Set<T>, U> finisher() {
        return finisher;
    }
}
