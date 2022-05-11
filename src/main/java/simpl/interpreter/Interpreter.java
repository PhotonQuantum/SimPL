package simpl.interpreter;

import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;

import java.io.FileReader;

public class Interpreter {

    private static void interpret(String filename) {
        Interpreter i = new Interpreter();
        System.out.println(filename);
        i.run(filename);
    }

    public static void main(String[] args) {
        interpret("doc/examples/plus.spl");
        interpret("doc/examples/factorial.spl");
        interpret("doc/examples/gcd1.spl");
        interpret("doc/examples/gcd2.spl");
        interpret("doc/examples/max.spl");
        interpret("doc/examples/sum.spl");
        interpret("doc/examples/map.spl");
        interpret("doc/examples/pcf.sum.spl");
        interpret("doc/examples/pcf.even.spl");
        interpret("doc/examples/pcf.minus.spl");
        interpret("doc/examples/pcf.factorial.spl");
        interpret("doc/examples/pcf.fibonacci.spl");
        // interpret("doc/examples/pcf.twice.spl");
        // interpret("doc/examples/pcf.lists.spl");
    }

    public void run(String filename) {
        try (var inp = new FileReader(filename)) {
            Parser parser = new Parser(inp);
            var parseTree = parser.parse();
            Expr program = (Expr) parseTree.value;
            System.out.println(program.typeCheck(TypeEnv.DEFAULT).ty());
            System.out.println(program.eval(new InitialState()));
        } catch (SyntaxError e) {
            System.out.println("syntax error");
            System.err.println(e.getMessage());
        } catch (TypeError e) {
            System.out.println("type error");
            System.err.println(e.getMessage());
            System.err.println("Trace:" + e.getCheckTrace());
        } catch (RuntimeError e) {
            System.out.println("runtime error");
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
