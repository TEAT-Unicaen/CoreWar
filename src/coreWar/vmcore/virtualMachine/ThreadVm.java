package coreWar.vmcore.virtualMachine;

import coreWar.vmcore.interpreter.InstructionsInterpretor;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.interpreter.LoopException;

public class ThreadVm extends Thread {

    private Vm vm;
    private MemoryCell cache;
    private Boolean shouldStop = false;
    public InstructionsInterpretor interpretor;
    public ThreadVm(Vm vm) {
        this.vm = vm;
        this.cache = new MemoryCell();
    }

    /**
     * lance l'execution de la vm
     */
    @Override
    public void run() {
        this.interpretor = new InstructionsInterpretor();
        while (this.vm.getProcessQueue().size() > 1 && this.vm.tick < 10000 && !this.shouldStop) {
            this.vm.tick++;
            MemoryCell nextInst = this.vm.getNextInstructionCell();
            try {
                interpretor.ApplyInstruction(nextInst,this.vm);
            } catch (LoopException e) {
                this.vm.winner = nextInst.getOwner()==1 ? 2:1;
                this.vm.loopProtectioEnabled = true;
                break;
            } catch (Exception e) {
                System.err.println("Error while executing program " + nextInst.getOwner() + " (" + nextInst.toStringDebug() + ")");
                break;
            }
            this.cache = nextInst;
        }
        this.interrupt();
        if (this.shouldStop) {
            System.err.println("Cutted while executing program " + cache.getOwner() + " (" + cache.toStringDebug() + ") ID THREAD : " + this + " | " + this.vm.tick);
        }
    }

    public void kill() {
        this.shouldStop = true;
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
