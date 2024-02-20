package vmcore.Supervisor;

import vmcore.Memory.Memory;

public class Supervisor {

    private Memory memory; 
    
    public Supervisor(int size) {
        this.memory = new Memory(size);
    }

    Memory displayMemory() {
        return this.memory;
    }
}
