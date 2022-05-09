package simpl.interpreter;

import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.Expr;
import simpl.typing.DefaultTypeEnv;
import simpl.typing.TypeError;

import java.io.FileInputStream;
import java.io.InputStream;

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
        try (InputStream inp = new FileInputStream(filename)) {
            Parser parser = new Parser(inp);
            java_cup.runtime.Symbol parseTree = parser.parse();
            Expr program = (Expr) parseTree.value;
            System.out.println(program.typecheck(new DefaultTypeEnv()).t);
            System.out.println(program.eval(new InitialState()));
        } catch (SyntaxError e) {
            System.out.println("syntax error");
        } catch (TypeError e) {
            System.out.println("type error");
        } catch (RuntimeError e) {
            System.out.println("runtime error");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
