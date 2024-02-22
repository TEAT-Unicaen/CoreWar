package vmcore.Supervisor;

import vmcore.Memory.Memory;
import vmcore.Memory.MemoryCell;

public class Supervisor {

    private Memory memory; 
    public static File FileAppel = new File(); 
    
    public static void PutInQueue(MemoryCell mem, int index) {
        MemoryCell prov = mem; 
        for (int i = 0; i < index; i++) {
            prov = prov.GetNext();
        }
        Supervisor.FileAppel.Enfile(prov);
    }

    public static void PutInQueue(MemoryCell mem) {
        Supervisor.FileAppel.Enfile(mem.GetNext());
    }

    public Supervisor(int size) {
        this.memory = new Memory(size);
    }

    public Memory getMemory() {
        return this.memory;
    }
}
