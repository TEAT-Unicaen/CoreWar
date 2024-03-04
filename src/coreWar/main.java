package coreWar;

import coreWar.vmcore.interpreter.Converter;
import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.display.CorewarGui;
import coreWar.vmcore.supervisor.Supervisor;
import coreWar.vmcore.interpreter.InstructionsInterpretor; 

public class main {
    
    public static void main(String args[]) throws InterruptedException {
        int tried = 0; 
        Supervisor supervisor = new Supervisor(8000);
        initPlayersRedcodes(supervisor.getMemory());
        CorewarGui corewarGui = new CorewarGui(supervisor);
        corewarGui.setVisible(true);
        MemoryCell cache = new MemoryCell();
        
        while (Supervisor.getProcessQueue().size() >= 1) { // >= si on veut pas kill quand il est solo
            tried++;
            System.out.println("NASA TRY APolo : " + tried);
            Thread.sleep(2);
            MemoryCell nextInst = Supervisor.getNextInstructionCell();
            try {
                System.out.println(nextInst.hardIndex);
                InstructionsInterpretor.ApplyInstruction(nextInst);
                corewarGui.updateMemoryToIndexWithColor(java.awt.Color.CYAN,nextInst.hardIndex);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(cache.hardIndex + " | " + cache.toStringDebug()); 
                break;
            }
            cache = nextInst; 
        }
        System.out.println("end" + Supervisor.getProgramCounter());
    }

    private static void initPlayersRedcodes(Memory mem) {
        Converter conv = new Converter();
        conv.RedCodeToMemory(mem.start, "src\\coreWar\\players\\oblivion.rc", 1, false);
        conv.RedCodeToMemory(mem.getEmptySlot(), "src\\coreWar\\players\\orion.rc", 2, false);
    }
}
