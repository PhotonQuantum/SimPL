package simpl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import simpl.interpreter.Config;
import simpl.interpreter.Interpreter;

import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class EvaluationTest {
    @ParameterizedTest
    @CsvSource(value = {
            "doc/examples/plus.spl:3",
            "doc/examples/factorial.spl:24",
            "doc/examples/gcd1.spl:1029",
            "doc/examples/gcd2.spl:1029",
            "doc/examples/max.spl:2",
            "doc/examples/sum.spl:6",
            "doc/examples/map.spl:fun",
            "doc/examples/pcf.sum.spl:fun",
            "doc/examples/pcf.even.spl:fun",
            "doc/examples/pcf.minus.spl:46",
            "doc/examples/pcf.factorial.spl:720",
            "doc/examples/pcf.fibonacci.spl:6765",
            "doc/examples/pcf.twice.spl:16",
            "doc/examples/letpoly.spl:0",
            "doc/examples/effect.spl:pair@0@1",
    }, delimiter = ':')
    void mustEval(String filename, String expected) throws Exception {
        var program = Interpreter.of(new FileReader(filename));
        var result = program.eval();
        assertEquals(expected, result.toString());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "doc/examples/plus.spl:3",
            "doc/examples/factorial.spl:24",
            "doc/examples/gcd1.spl:1029",
            "doc/examples/gcd2.spl:1029",
            "doc/examples/max.spl:2",
            "doc/examples/sum.spl:6",
            "doc/examples/map.spl:fun",
            "doc/examples/pcf.sum.spl:fun",
            "doc/examples/pcf.even.spl:fun",
            "doc/examples/pcf.minus.spl:46",
            "doc/examples/pcf.factorial.spl:720",
            "doc/examples/pcf.fibonacci.spl:6765",
            "doc/examples/pcf.twice.spl:16",
            "doc/examples/letpoly.spl:0",
            "doc/examples/effect.spl:pair@0@1",
    }, delimiter = ':')
    void mustEvalWithAggressiveGC(String filename, String expected) throws Exception {
        var program = Interpreter.of(new FileReader(filename));
        program.config = program.config.join(new Config(null, (float) 0, null, null));
        var result = program.eval();
        assertEquals(expected, result.toString());
    }
}
