package coreWar.vmcore.virtualMachine;

import coreWar.vmcore.interpreter.InstructionsInterpretor;
import coreWar.vmcore.memory.MemoryCell;

public class ThreadVm extends Thread {

    private Vm vm;
    private MemoryCell cache;

    public ThreadVm(Vm vm) {
        this.vm = vm;
        this.cache = new MemoryCell();
    }

    /**
     * lance l'execution de la vm
     */
    @Override
    public void run() {
        while (this.vm.getProcessQueue().size() > 1) {
            this.vm.tick++;
            MemoryCell nextInst = this.vm.getNextInstructionCell();
            try {
                InstructionsInterpretor.ApplyInstruction(nextInst,this.vm);
            } catch (Exception e) {
                if (e.getMessage() == "SPL") {
                    System.out.println("Removed !");
                    this.kill();
                } else {
                    System.err.println("Error while executing program " + nextInst.getOwner() + " (" + nextInst.toStringDebug() + ")");
                    break;
                }
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
        return this.vm.tick;
    }

    public int getWinner() {
        return this.vm.winner;
    }

    public Vm getVm() {
        return this.vm;
    }
}
