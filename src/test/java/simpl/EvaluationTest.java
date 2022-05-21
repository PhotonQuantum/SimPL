package simpl;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import simpl.interpreter.Config;
import simpl.interpreter.Interpreter;

import java.io.FileReader;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EvaluationTest {
    @ParameterizedTest
    @MethodSource("evalSource")
    void mustEval(String filename, String expected) throws Exception {
        var program = Interpreter.of(new FileReader(filename));
        var result = program.eval();
        assertEquals(expected, result.toString());
    }

    @ParameterizedTest
    @MethodSource("evalSource")
    void mustEvalWithStrict(String filename, String expected, boolean skipWhenStrict) throws Exception {
        if (skipWhenStrict) return;
        var program = Interpreter.of(new FileReader(filename));
        program.config = program.config.join(new Config(true, null, null, null));
        var result = program.eval();
        assertEquals(expected, result.toString());
    }

    @ParameterizedTest
    @MethodSource("evalSource")
    void mustEvalWithAggressiveGC(String filename, String expected) throws Exception {
        var program = Interpreter.of(new FileReader(filename));
        program.config = program.config.join(new Config(null, (float) 0, null, null));
        var result = program.eval();
        assertEquals(expected, result.toString());
    }

    static @NotNull Stream<Arguments> evalSource() {
        return Stream.of(
                Arguments.of("doc/examples/plus.spl", "3", false),
                Arguments.of("doc/examples/factorial.spl", "24", false),
                Arguments.of("doc/examples/gcd1.spl", "1029", false),
                Arguments.of("doc/examples/gcd2.spl", "1029", false),
                Arguments.of("doc/examples/max.spl", "2", false),
                Arguments.of("doc/examples/sum.spl", "6", false),
                Arguments.of("doc/examples/map.spl", "fun", false),
                Arguments.of("doc/examples/pcf.sum.spl", "fun", false),
                Arguments.of("doc/examples/pcf.even.spl", "fun", false),
                Arguments.of("doc/examples/pcf.minus.spl", "46", false),
                Arguments.of("doc/examples/pcf.factorial.spl", "720", false),
                Arguments.of("doc/examples/pcf.fibonacci.spl", "6765", false),
                Arguments.of("doc/examples/pcf.twice.spl", "16", false),
                Arguments.of("doc/examples/letpoly.spl", "0", false),
                Arguments.of("doc/examples/effect.spl", "pair@0@1", true),  // This relies on lazy eval.
                Arguments.of("doc/examples/stream.spl", "10", false)
        );
    }
}
