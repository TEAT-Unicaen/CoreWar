package coreWar.vmcore.process;

import java.util.ArrayList;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.supervisor.Supervisor;
public class ProcessQueue extends ArrayList<MemoryCell> {

    private boolean debug = false;  

    public ProcessQueue() {};
    public ProcessQueue(boolean debug) {this.debug = true;};

    public void push(MemoryCell mem) {
        if (debug) { 
            System.out.println("Process " + mem.hardIndex + " pushed. Total : " + Supervisor.playersInstance[0] + " | p1 : " +  Supervisor.playersInstance[1] + " | p2 : " + Supervisor.playersInstance[2]);
            Supervisor.playersInstance[mem.getOwner()]++; 
        } 
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

    public void enbaleDebugMode(boolean enable) {
        this.debug = enable;
    }
}
