package simpl.interpreter;

import org.jetbrains.annotations.NotNull;
import simpl.parser.Parser;
import simpl.parser.SyntaxError;
import simpl.parser.ast.EntryPoint;
import simpl.parser.ast.Expr;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;

import java.io.FileReader;
import java.util.concurrent.atomic.AtomicReference;

public class Interpreter {
    Config config;
    final Expr program;

    private Interpreter(Expr program, Config config) {
        this.config = config;
        this.program = program;
    }

    public static @NotNull Interpreter of(FileReader reader) throws Exception {
        Parser parser = new Parser(reader);
        var parseTree = parser.parse();
        var program = (EntryPoint) parseTree.value;
        return new Interpreter(program.expr(), Config.of(program.pragmas()));
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Type typeCheck() throws TypeError {
        return program.typeCheck(TypeEnv.DEFAULT).ty();
    }

    public Value eval() throws Exception {
        var result = new AtomicReference<Object>(new Exception("Stack Overflow"));

        Runnable f = () -> {
            try {
                result.set(program.eval(new InitialState(config)));
            } catch (RuntimeError e) {
                result.set(e);
            }
        };

        var thread = new Thread(null, f, "Thread-eval", config.stackSize());
        thread.start();
        thread.join();
        if (result.get() instanceof Exception e) {
            throw e;
        } else {
            return (Value) result.get();
        }
    }

    public static void main(String @NotNull [] args) {
        try (var inp = new FileReader(args[0])) {
            var program = Interpreter.of(inp);
            program.typeCheck();
            System.out.println(program.eval());
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
            e.printStackTrace();
        }
    }
}
