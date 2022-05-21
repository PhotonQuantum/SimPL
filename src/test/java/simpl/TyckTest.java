package simpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import simpl.interpreter.Interpreter;
import simpl.typing.TypeError;

import java.io.FileReader;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class TyckTest {
    String typeCheck(String filename) throws Exception {
        var program = Interpreter.of(new FileReader(filename));
        var result = program.typeCheck();
        return result.toString();
    }

    @ParameterizedTest
    @CsvSource(value = {
            "doc/examples/plus.spl:int",
            "doc/examples/factorial.spl:int",
            "doc/examples/gcd1.spl:int",
            "doc/examples/gcd2.spl:int",
            "doc/examples/max.spl:int",
            "doc/examples/sum.spl:int",
            "doc/examples/pcf.sum.spl:(int -> (int -> int))",
            "doc/examples/pcf.even.spl:(int -> bool)",
            "doc/examples/pcf.minus.spl:int",
            "doc/examples/pcf.factorial.spl:int",
            "doc/examples/pcf.fibonacci.spl:int",
            "doc/examples/pcf.twice.spl:int",
            "doc/examples/letpoly.spl:int",
            "doc/examples/effect.spl:(int * int)",
            "doc/examples/stream.spl:int",
    }, delimiter = ':')
    void mustTyck(String filename, String expected) throws Exception {
        var result = typeCheck(filename);
        assertEquals(expected, result);
    }

    @Test
    void mustTyckPoly() throws Exception {
        var result = typeCheck("doc/examples/map.spl");
        var pat = Pattern.compile("\\(\\((.*) -> (.*)\\) -> \\((.*) list -> (.*) list\\)\\)");
        var matcher = pat.matcher(result);
        assertTrue(matcher.matches());
        assertEquals(matcher.group(1), matcher.group(3));
        assertEquals(matcher.group(2), matcher.group(4));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "doc/examples/pcf.lists.spl",
    })
    void mustNotTyck(String filename) {
        assertThrows(TypeError.class, () -> typeCheck(filename));
    }
}
