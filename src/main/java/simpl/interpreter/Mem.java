package simpl.interpreter;

import kala.collection.mutable.MutableMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Mem {
    public static class Cell {
        protected final MutableMap<Integer, Value> storage;
        protected final int pointer;

        protected Cell(MutableMap<Integer, Value> storage, int pointer) {
            this.storage = storage;
            this.pointer = pointer;
        }

        @Contract(pure = true)
        protected Cell(@NotNull Cell cell) {
            this.storage = cell.storage;
            this.pointer = cell.pointer;
        }

        public Value get() {
            assert storage.containsKey(pointer);
            return storage.get(pointer);
        }

        public void set(Value v) {
            assert storage.containsKey(pointer);
            storage.put(pointer, v);
        }

        public int unsafeGetPointer() {
            return pointer;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return pointer == cell.pointer;
        }
    }

    private final MutableMap<Integer, Value> storage = MutableMap.create();
    private final Int head = new Int(0);

    public Cell allocate(Value initialValue) {
        var pointer = head.increment();
        storage.put(pointer, initialValue);
        return new Cell(storage, pointer);
    }
}
