package coreWar.vmcore.process;

import java.util.ArrayList;

import coreWar.vmcore.memory.MemoryCell;

public class ProcessQueue extends ArrayList<MemoryCell> {
    public ProcessQueue() {};

    public void push(MemoryCell mem) {
        System.out.println(mem.hardIndex + " pushed");  
        this.add(mem); 
    };

    public MemoryCell pop() {
        if (this.isEmpty()) {
            System.out.println("Fin de la file !");
            return null;    
        }
        MemoryCell value = this.get(0);//this.getFirst();
        this.remove(0);//removeFirst();
        return value; 
    };
}
