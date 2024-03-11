package coreWar.vmcore.supervisor;

import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.process.ProcessQueue;

public class Supervisor {

    private Memory memory; 

    public static int[] playersInstance = {0,0,0};

    private static ProcessQueue callQueue = new ProcessQueue(); 
    
    public static void putInQueue(MemoryCell mem, int index) {
        if (Supervisor.playersInstance[0] < 100) {
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
        } else {
            System.out.println(mem.hardIndex + " refusé de la file (file pleine)");
        }
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

    public static void incrementProgramCounter() {
        Supervisor.playersInstance[0]++;
    }

    public static void decrementProgramCounter() {
        Supervisor.playersInstance[0]--;
    }

    public static int getProgramCounter() {
        return Supervisor.playersInstance[0];
    }

    public static MemoryCell getNextInstructionCell() {
        return Supervisor.callQueue.pop();
    }

    public static ProcessQueue getProcessQueue() {
        return Supervisor.callQueue;
    }
}
