package io.github.scarecraw22.utils.collection

import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class CollectionUtilsTest extends Specification {

    @Unroll
    def 'isEmpty() should return #expected for given list: #input'() {
        when:
        def result = CollectionUtils.of(input).isEmpty()

        then:
        result == expected

        where:
        input || expected
        null  || true
        []    || true
        [1]   || false
    }

    @Unroll
    def 'isNotEmpty() should return #expected for given list: #input'() {
        when:
        def result = CollectionUtils.of(input).isNotEmpty()

        then:
        result == expected

        where:
        input || expected
        null  || false
        []    || false
        [1]   || true
    }

    @Unroll
    def 'hasSize() should return #expected for given size: #size, and list: #input'() {
        when:
        def result = CollectionUtils.of(input).hasSize(size)

        then:
        result == expected

        where:
        input     | size || expected
        null      | 0    || true
        null      | 1    || false
        []        | 0    || true
        [1]       | 1    || true
        [1, 2]    | 1    || false
        [1, 2, 3] | 3    || true
    }

    @Unroll
    def 'hasSize() should throw exception for given size: #size, and list: #input'() {
        when:
        def result = CollectionUtils.of(input).hasSize(size)

        then:
        thrown(IllegalArgumentException.class)

        where:
        input | size
        null  | -1
        null  | -2
    }

    def 'applyForEach() should apply forEach loop when collection is not empty'() {
        when:
        List<AtomicInteger> input = [new AtomicInteger(1), new AtomicInteger(2)]
        CollectionUtils.of(input).applyForEach(i -> i.set(0))

        then:
        input.get(0).get() == 0
        input.get(1).get() == 0
    }

    def 'applyForEach() should not apply forEach loop when collection is empty'() {
        when:
        List<AtomicInteger> input = []
        CollectionUtils.of(input).applyForEach(i -> i.set(0))

        then:
        input.isEmpty()
    }

    def 'mapThenToList() should not apply function when collection is not empty'() {
        when:
        def result = CollectionUtils.of(input).mapThenToList(i -> String.valueOf(i.get()))

        then:
        result == expected

        where:
        input                                               || expected
        null                                                || []
        []                                                  || []
        [new AtomicBoolean(true), new AtomicBoolean(false)] || ["true", "false"]
    }

    def 'mapThenToSet() should not apply function when collection is not empty'() {
        when:
        def result = CollectionUtils.of(input).mapThenToSet(i -> String.valueOf(i.get()))

        then:
        result == expected

        where:
        input                                               || expected
        null                                                || Collections.emptySet()
        []                                                  || Collections.emptySet()
        [new AtomicBoolean(true), new AtomicBoolean(false)] || Set.of("true", "false")
    }
}
