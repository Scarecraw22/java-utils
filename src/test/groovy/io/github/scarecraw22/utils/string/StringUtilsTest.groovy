package io.github.scarecraw22.utils.string

import spock.lang.Specification
import spock.lang.Unroll

class StringUtilsTest extends Specification {

    @Unroll
    def 'getFirstNChars("#input", "#n") should return "#expected"'() {
        when:
        def result = StringUtils.of(input).getFirstNChars(n)

        then:
        result == expected

        where:
        input         | n || expected
        "long string" | 4 || "long"
        "long string" | 6 || "long s"
    }

    @Unroll
    def 'getFirstNChars("#input", "#n") should return throw exception'() {
        when:
        StringUtils.of(input).getFirstNChars(n)

        then:
        thrown(IllegalArgumentException.class)

        where:
        input             | n
        ""                | 0
        "string"          | 0
        "string"          | -1
        "too long string" | 20
    }

    @Unroll
    def 'getFirstNChars("#input", "#n") should return throw NPE'() {
        when:
        StringUtils.of(input).getFirstNChars(n)

        then:
        thrown(NullPointerException.class)

        where:
        input | n
        null  | 1
    }

    @Unroll
    def 'getLastNChars("#input", "#n") should return "#expected"'() {
        when:
        def result = StringUtils.of(input).getLastNChars(n)

        then:
        result == expected

        where:
        input         | n || expected
        "long string" | 4 || "ring"
        "long string" | 7 || " string"
    }

    @Unroll
    def 'getLastNChars("#input", "#n") should return throw exception'() {
        when:
        StringUtils.of(input).getLastNChars(n)

        then:
        thrown(IllegalArgumentException.class)

        where:
        input             | n
        ""                | 0
        "string"          | 0
        "string"          | -1
        "too long string" | 20
    }

    @Unroll
    def 'getLastNChars("#input", "#n") should return throw NPE'() {
        when:
        StringUtils.of(input).getLastNChars(n)

        then:
        thrown(NullPointerException.class)

        where:
        input | n
        null  | 1
    }

    @Unroll
    def 'getFirstChar("#input") should return "#expected"'() {
        when:
        def result = StringUtils.of(input).getFirstChar()

        then:
        result == expected

        where:
        input         || expected
        "long string" || "l"
    }

    @Unroll
    def 'getFirstChar("#input") should return throw exception'() {
        when:
        StringUtils.of(input).getFirstChar()

        then:
        thrown(IllegalArgumentException.class)

        where:
        input || _
        ""    || _
    }

    @Unroll
    def 'getFirstChar("#input") should return throw NPE'() {
        when:
        StringUtils.of(input).getFirstChar()

        then:
        thrown(NullPointerException.class)

        where:
        input || _
        null  || _
    }

    @Unroll
    def 'getLastChar("#input") should return "#expected"'() {
        when:
        def result = StringUtils.of(input).getLastChar()

        then:
        result == expected

        where:
        input         || expected
        "long string" || "g"
    }

    @Unroll
    def 'getLastNChars("#input") should return throw exception'() {
        when:
        StringUtils.of(input).getLastChar()

        then:
        thrown(IllegalArgumentException.class)

        where:
        input | n
        ""    | 0
    }

    @Unroll
    def 'getLastNChars("#input") should return throw NPE'() {
        when:
        StringUtils.of(input).getLastChar()

        then:
        thrown(NullPointerException.class)

        where:
        input | n
        null  | 1
    }
}
