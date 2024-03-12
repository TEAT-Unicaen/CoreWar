package coreWar.vmcore.process;

import java.util.ArrayList;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.virtualMachine.Vm;

public class ProcessQueue extends ArrayList<MemoryCell> {

    private boolean debug = false;  
    private Vm vm; 

    public ProcessQueue(Vm vm) {
        this.vm = vm; 
    };
    
    public ProcessQueue(Vm vm, boolean debug) {this.debug = true;};

    public void push(MemoryCell mem) {
        if (debug) 
            System.out.println("Process " + mem.hardIndex + " pushed. Total : " + this.vm.playersInstance[0] + " | p1 : " +  this.vm.playersInstance[1] + " | p2 : " + this.vm.playersInstance[2]);
        this.vm.playersInstance[mem.getOwner()]++; 
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
