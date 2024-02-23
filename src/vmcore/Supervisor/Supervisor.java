package vmcore.Supervisor;

import vmcore.Memory.Memory;
import vmcore.Memory.MemoryCell;

public class Supervisor {

    private Memory memory; 
    private static int PorgramCounter = 0; 

    public static File FileAppel = new File(); 
    
    public static void PutInQueue(MemoryCell mem, int index) {
        MemoryCell prov = mem; 
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                prov = prov.GetNext();
            }
        } else {
            index *= -1;
            for (int i = 0; i < index; i++) {
                prov = prov.GetPrevious();
            }
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

    public static void incrementPorgramCounter() {
        Supervisor.PorgramCounter ++; 
    }

    public static void decrementPorgramCounter() {
        Supervisor.PorgramCounter ++; 
    }

    public static int getProgramCounter() {
        return Supervisor.PorgramCounter; 
    }
}
