package io.github.scarecraw22.utils.string

import spock.lang.Specification
import spock.lang.Unroll

class StringUtilsTest extends Specification {

    @Unroll
    def 'getFirstNChars("#input", "#n") should return "#expected"'() {
        when:
        def result = StringUtils.getFirstNChars(input, n)

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
        StringUtils.getFirstNChars(input, n)

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
        StringUtils.getFirstNChars(input, n)

        then:
        thrown(NullPointerException.class)

        where:
        input | n
        null  | 1
    }

    @Unroll
    def 'getLastNChars("#input", "#n") should return "#expected"'() {
        when:
        def result = StringUtils.getLastNChars(input, n)

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
        StringUtils.getLastNChars(input, n)

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
        StringUtils.getLastNChars(input, n)

        then:
        thrown(NullPointerException.class)

        where:
        input | n
        null  | 1
    }

    @Unroll
    def 'getFirstChar("#input") should return "#expected"'() {
        when:
        def result = StringUtils.getFirstChar(input)

        then:
        result == expected

        where:
        input         || expected
        "long string" || "l"
    }

    @Unroll
    def 'getFirstChar("#input") should return throw exception'() {
        when:
        StringUtils.getFirstChar(input)

        then:
        thrown(IllegalArgumentException.class)

        where:
        input || _
        ""    || _
    }

    @Unroll
    def 'getFirstChar("#input") should return throw NPE'() {
        when:
        StringUtils.getFirstChar(input)

        then:
        thrown(NullPointerException.class)

        where:
        input || _
        null  || _
    }

    @Unroll
    def 'getLastChar("#input") should return "#expected"'() {
        when:
        def result = StringUtils.getLastChar(input)

        then:
        result == expected

        where:
        input         || expected
        "long string" || "g"
    }

    @Unroll
    def 'getLastNChars("#input") should return throw exception'() {
        when:
        StringUtils.getLastChar(input)

        then:
        thrown(IllegalArgumentException.class)

        where:
        input | n
        ""    | 0
    }

    @Unroll
    def 'getLastNChars("#input") should return throw NPE'() {
        when:
        StringUtils.getLastChar(input)

        then:
        thrown(NullPointerException.class)

        where:
        input | n
        null  | 1
    }
}
