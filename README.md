# SimPL Interpreter

An *interpreter* for the programming language SimPL (pronounced *simple*).

SimPL is a simplified dialect of ML, which can be used for both *functional* and *imperative* programming.

It's a project for the course *Programming Languages* at *Shanghai Jiao Tong University*.
See [the course website](https://www.cs.sjtu.edu.cn/~kzhu/cs383) for more details.

## Features

- ML-like syntax.
- Algorithm W type inference.
- Call-by-value and call-by-need evaluation strategies.
- Infinite data structures.
- GC based on copy collection.

## Get Started

```bash
# Interpret a SimPL program
$ gradle run --args="doc/examples/gc.spl"
# Run tests
$ gradle test
# Build a standalone jar
$ gradle shadowJar
```

The grammar resides in `src/main/cup/simpl/parser/simpl.cup`.

You may find `src/main/java/simpl/interpreter` useful if you'd like to dig into the evaluation process,
and `src/main/java/simpl/typing` if you're interested in type inference.

Please refer to [Specification](doc/spec.pdf) for mandatory project requirements,
and [Report](doc/report.pdf) for implementation details.

## Examples

See `doc/examples` for some SimPL programs.

## License

This project is licensed under GPLv3.

Please be aware that you are required to include a copy of the full license text
and state all the changes you made to the original source code
if you decide to use whatever part of this code.