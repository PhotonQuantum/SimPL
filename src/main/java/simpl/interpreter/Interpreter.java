package simpl.interpreter;

import org.jetbrains.annotations.NotNull;
import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;

import java.io.FileReader;

public class Interpreter {

    private static void interpret(String filename) {
        Interpreter i = new Interpreter();
        i.run(filename);
    }

    public static void main(String @NotNull [] args) {
        interpret(args[0]);
    }

    public void run(String filename) {
        try (var inp = new FileReader(filename)) {
            Parser parser = new Parser(inp);
            var parseTree = parser.parse();
            Expr program = (Expr) parseTree.value;
            program.typeCheck(TypeEnv.DEFAULT);
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
