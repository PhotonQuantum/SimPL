package simpl.interpreter;

import kala.collection.immutable.ImmutableCompactSet;
import kala.collection.mutable.MutableList;
import kala.collection.mutable.MutableSinglyLinkedList;
import kala.collection.mutable.MutableStack;
import kala.function.CheckedFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Mem {
    private final MutableCell<MutableList<Value>> storage = MutableCell.of(MutableList.create());
    private final MutableStack<Env> roots = MutableSinglyLinkedList.create();

    public Mem() {
    }

    public Cell allocate(@NotNull State s, Value initialValue) throws RuntimeError {
        updateRoot(s.E);
        var heapUsage = (float) storage.get().size() / s.config.heapSize();
        if (heapUsage >= s.config.gcThreshold()) {
            System.err.printf("[Alloc] Heap used percentage: %.2f >= %.2f, triggering GC\n",
                    heapUsage, s.config.gcThreshold());
            gc();
        }

        var heapSize = storage.get().size();
        if (heapSize >= s.config.heapSize()) {
            throw new RuntimeError("Heap overflow");
        }
        storage.get().append(initialValue);
        return new Cell(storage, MutableCell.of(heapSize));
    }

    public void updateRoot(@NotNull Env E) {
        roots.pop();
        roots.push(E);
    }

    public void pushRoot(@NotNull Env E) {
        roots.push(E);
    }

    public void popRoot() {
        roots.pop();
    }

    /**
     * Perform copy garbage collection.
     */
    public void gc() {
        System.err.println("[GC] Starting GC. Before: " + storage.get().size() + " objects.");

        // Scan for cells that are reachable from the root.
        var reachable = roots.view()
                .flatMap(E -> E.valuesView().flatMap(CheckedFunction.of(Value::collectRefValues)))
                // .flatMap(E -> E.valuesView().filter(v -> v instanceof RefValue).map(v -> (RefValue) v))
                .collect(ImmutableCompactSet.factory())
                .toImmutableSeq();
        var newStorage = MutableList.from(reachable.view().map(Cell::get));
        reachable.view().forEachIndexed((i, v) -> {
            v.storage.set(newStorage);
            v.pointer.set(i);
        });

        System.err.println("[GC] After: " + newStorage.size() + " objects.");
        storage.set(newStorage);
    }

    public static class Cell {
        protected final MutableCell<MutableList<Value>> storage;
        protected final MutableCell<Integer> pointer;

        protected Cell(MutableCell<MutableList<Value>> storage, MutableCell<Integer> pointer) {
            this.storage = storage;
            this.pointer = pointer;
        }

        @Contract(pure = true)
        protected Cell(@NotNull Cell cell) {
            this.storage = cell.storage;
            this.pointer = cell.pointer;
        }

        public Value get() {
            return storage.get().get(pointer.get());
        }

        public void set(Value v) {
            storage.get().set(pointer.get(), v);
        }

        public int unsafeGetPointer() {
            return pointer.get();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return pointer == cell.pointer;
        }
    }
}
