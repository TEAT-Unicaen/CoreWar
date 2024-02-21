package vmcore.Supervisor;

import vmcore.Memory.Memory;

public class Supervisor {

    private Memory memory; 
    
    public Supervisor(int size) {
        this.memory = new Memory(size);
    }

    public Memory getMemory() {
        return this.memory;
    }
}
