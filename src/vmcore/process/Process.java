package vmcore.process;

import vmcore.memory.MemoryCell;

public class Process {
    private MemoryCell pointer;
    private int playerId;

    public Process(MemoryCell pointer, int playerId) {
        this.pointer = pointer;
        this.playerId = playerId;
    }

    public MemoryCell getPointer() {
        return this.pointer;
    }

    public int getPlayerId() {
        return this.playerId;
    }
}
