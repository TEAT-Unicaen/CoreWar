package coreWar.vmcore.virtualMachine;

import coreWar.vmcore.interpreter.InstructionsInterpretor;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.interpreter.LoopException;

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
        while (this.vm.getProcessQueue().size() > 1 && this.vm.tick < 10000) {
            this.vm.tick++;
            MemoryCell nextInst = this.vm.getNextInstructionCell();
            try {
                InstructionsInterpretor.ApplyInstruction(nextInst,this.vm);
            } catch (LoopException e) {
                this.kill();
                this.vm.winner = 0;  //TODO : faire winner bien hein pcq la wtf
            } catch (Exception e) {
                System.err.println("Error while executing program " + nextInst.getOwner() + " (" + nextInst.toStringDebug() + ")");
                this.kill();
            }
            this.cache = nextInst; 

            if (Thread.interrupted()) {
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
