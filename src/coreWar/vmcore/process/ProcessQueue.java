package coreWar.vmcore.process;

import java.util.ArrayList;

import coreWar.vmcore.memory.MemoryCell;

public class ProcessQueue extends ArrayList<MemoryCell> {
    public ProcessQueue() {};

    public void push(MemoryCell mem) {
        this.add(mem); 
    };

    public MemoryCell pop() {
        int size = this.size() - 1;
        if (size <= 0)
            return null;
        MemoryCell value = this.get(size);
        this.remove(size);
        return value; 
    };

}
