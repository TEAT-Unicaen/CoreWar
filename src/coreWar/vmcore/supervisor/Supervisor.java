package coreWar.vmcore.supervisor;

import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.process.ProcessQueue;

public class Supervisor {

    private Memory memory; 
    private static int PorgramCounter = 0; 

    private static ProcessQueue callQueue = new ProcessQueue(); 
    
    public static void putInQueue(MemoryCell mem, int index) {
        MemoryCell prov = mem; 
        if (index > 0) {
            for (int i = 0; i < index; i++)
                prov = prov.getNext();
        } else {
            index *= -1;
            for (int i = 0; i < index; i++)
                prov = prov.getPrevious();
        }
        Supervisor.callQueue.push(prov);
    }

    public static void putInQueue(MemoryCell mem) {
        Supervisor.callQueue.push(mem);
    }

    public Supervisor(int size) {
        this.memory = new Memory(size);
    }

    public Memory getMemory() {
        return this.memory;
    }

    public static void incrementPorgramCounter() {
        Supervisor.PorgramCounter++;
    }

    public static void decrementPorgramCounter() {
        Supervisor.PorgramCounter--;
    }

    public static int getProgramCounter() {
        return Supervisor.PorgramCounter;
    }
}
