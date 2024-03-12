package coreWar.vmcore.virtualMachine;

import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.process.ProcessQueue;

public class Vm {

    private Memory memory; 

    public int[] playersInstance = {0,0,0};
    public int winner = -1;  

    private ProcessQueue callQueue = new ProcessQueue(this); 

    public Vm(int size) {
        this.memory = new Memory(size);
    }
    
    public void putInQueue(MemoryCell mem, int index) {
        if (this.playersInstance[0] < 100) {
            MemoryCell prov = mem; 
            if (index > 0) {
                for (int i = 0; i < index; i++)
                    prov = prov.getNext();
            } else {
                index *= -1;
                for (int i = 0; i < index; i++)
                    prov = prov.getPrevious();
            }
            this.callQueue.push(prov);
        } else {
            System.out.println(mem.hardIndex + " refusé de la file (file pleine)");
        }
    }

    public void putInQueue(MemoryCell mem) {
        this.callQueue.push(mem);
    }

    public Memory getMemory() {
        return this.memory;
    }

    public void incrementProgramCounter() {
        this.playersInstance[0]++;
    }

    public void decrementProgramCounter() {
        this.playersInstance[0]--;
    }

    public int getProgramCounter() {
        return this.playersInstance[0];
    }

    public MemoryCell getNextInstructionCell() {
        return this.callQueue.pop();
    }

    public ProcessQueue getProcessQueue() {
        return this.callQueue;
    }
}
