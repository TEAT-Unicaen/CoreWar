package coreWar.vmcore.virtualMachine;

import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.process.ProcessQueue;

public class Vm {

    private Memory memory; 

    /* data for genetics */
    public int[] playersInstance = {0,0,0}; //global | p1 | p2
    public int winner = -1;                 //-1 init | 0 egal |1 p1 |2 p2
    public int tick = 0; 
    public int[] uuid;                      // tracçabilité du redcode
    public int[] death = {0,0,0}; 
    public boolean loopProtectioEnabled = false;
    /* end */

    private ProcessQueue callQueue = new ProcessQueue(this); 
    private int maxInstance; 

    /**
     * @param size : size of memory 
     * @param maxInst : max process in queue (default : 100) 
     */
    public Vm(int size,  int maxInst) {
        this.memory = new Memory(size);
        this.maxInstance = maxInst;
    }

    public Vm(int size,  int maxInst, int[] uuid) { 
        this(size,maxInst);
        this.uuid = uuid;
    }

    public void putInQueue(MemoryCell mem, int index) {
        if (this.playersInstance[0] < this.maxInstance) {
            MemoryCell prov = mem; 
            if (index > 0) {
                for (int i = 0; i < index; i++)
                    prov = prov.getNext();
            } else {
                index *= -1;
                for (int i = 0; i < index; i++)
                    prov = prov.getPrevious();
            }
            prov.setOwner(mem.getOwner());
            this.callQueue.push(prov);
        } else {
            this.decrementProgramCounter();
            //System.out.println(mem.hardIndex + " refusé de la file (file pleine)");
        }
    }

    /**
     * @return int : 0 / 1 / 2 en fonction du nombre d'instances encore en file (0 = égalitée)
     */
    public int getMVP() {
        if (winner == -1) {
            if (this.playersInstance[1] == this.playersInstance[2]) return 0;
            return (this.playersInstance[1] > this.playersInstance[2]) ? 1 : 2;  
        }
        return winner;
    }

    public void putInQueue(MemoryCell mem) {
        this.putInQueue(mem, 1);
    }

    public void putInQueueWithOwner(MemoryCell mem, int owner) {
        mem.setOwner(owner);
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
        this.death[0]++;
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
