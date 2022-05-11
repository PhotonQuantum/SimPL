package simpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import simpl.parser.Parser;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;

import java.io.FileReader;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class TyckTest {
    String typeCheck(String filename) throws Exception {
        var f = new FileReader(filename);
        var parser = new Parser(f);
        var parseTree = parser.parse();
        Expr program = (Expr) parseTree.value;
        var result = program.typeCheck(TypeEnv.DEFAULT).ty();
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
