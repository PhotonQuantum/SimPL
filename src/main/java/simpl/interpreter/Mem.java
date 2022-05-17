package simpl.interpreter;

import kala.collection.mutable.MutableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Mem {
    public Mem() {}

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

    private final MutableCell<MutableList<Value>> storage = MutableCell.of(MutableList.create());

    public Cell allocate(@NotNull State s, Value initialValue) throws RuntimeError {
        if (s.config.gc() && (float) storage.get().size() / s.config.heapSize() >= 0.8) {
            System.err.println("[Alloc] Heap is almost full, triggering GC");
            gcWithEnv(s.E);
        }

        var heapSize = storage.get().size();
        if (heapSize >= s.config.heapSize()) {
            throw new RuntimeError("Heap overflow");
        }
        storage.get().append(initialValue);
        return new Cell(storage, MutableCell.of(heapSize));
    }

    /**
     * Perform copy garbage collection.
     */
    public void gcWithEnv(@NotNull Env E) {
        System.err.println("[GC] Starting GC. Before: " + storage.get().size() + " objects.");

        // Scan for cells that are reachable from the root.
        var reachable = E.valuesView().filter(v -> v instanceof RefValue).map(v -> (RefValue) v).toImmutableSeq();
        var newStorage = MutableList.from(reachable.view().map(Cell::get));
        reachable.forEachIndexed((i, v) -> {
            v.storage.set(newStorage);
            v.pointer.set(i);
        });

        System.err.println("[GC] After: " + newStorage.size() + " objects.");
        storage.set(newStorage);
    }
}
