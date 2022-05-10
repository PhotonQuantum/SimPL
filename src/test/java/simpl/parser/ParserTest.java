package simpl.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileReader;

public class ParserTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "doc/examples/plus.spl",
            "doc/examples/factorial.spl",
            "doc/examples/gcd1.spl",
            "doc/examples/gcd2.spl",
            "doc/examples/max.spl",
            "doc/examples/sum.spl",
            "doc/examples/true.spl",
            "doc/examples/pcf.sum.spl",
            "doc/examples/pcf.even.spl",
            "doc/examples/pcf.twice.spl",
            "doc/examples/pcf.minus.spl",
            "doc/examples/pcf.factorial.spl",
            "doc/examples/pcf.fibonacci.spl",
            "doc/examples/pcf.lists.spl",
    })
    void mustParse(String filename) throws Exception {
        var inp = new FileReader(filename);
        Parser parser = new Parser(inp);
        java_cup.runtime.Symbol parseTree = parser.parse();
        System.out.println(filename);
        System.out.println(parseTree.value);
    }
}
