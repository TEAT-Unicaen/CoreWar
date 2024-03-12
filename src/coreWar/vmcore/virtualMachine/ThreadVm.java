package coreWar.vmcore.virtualMachine;

import coreWar.vmcore.interpreter.InstructionsInterpretor;
import coreWar.vmcore.memory.MemoryCell;

public class ThreadVm extends Thread {

    private Vm vm;
    private int tick;
    private MemoryCell cache;
    public ThreadVm(Vm vm) {
        this.vm = vm;
        this.tick = 0;
        this.cache = new MemoryCell();
    }

    @Override
    public void run() {
        while (this.vm.getProcessQueue().size() > 1) {
            this.tick++;
            MemoryCell nextInst = this.vm.getNextInstructionCell();
            try {
                InstructionsInterpretor.ApplyInstruction(nextInst,this.vm);
            } catch (Exception e) {
                System.out.println("Error while executing program " + nextInst.getOwner());
                break;
            }
            this.cache = nextInst; 

            if (Thread.interrupted()) {
                this.vm.winner = 0; 
                break;
            }
        }
    }

    public void kill() {
        interrupt();
    }

    public MemoryCell getLastMemCell() {
        return this.cache;
    }

    public int getTicks() {
        return this.tick;
    }

    public int getWinner() {
        return this.vm.winner;
    }

    public Vm getVm() {
        return this.vm;
    }
}
