package coreWar;

import coreWar.vmcore.interpreter.Converter;
import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.supervisor.Supervisor;
import coreWar.vmcore.interpreter.InstructionsInterpretor; 
import coreWar.display.CorewarGui;

public class main {
    
    public static void main(String args[]) throws InterruptedException {
        int tried = 0; 
        Supervisor supervisor = new Supervisor(1024);
        initPlayersRedcodes(supervisor.getMemory());
        CorewarGui corewarGui = new CorewarGui(supervisor);
        corewarGui.setVisible(true);
        MemoryCell cache = new MemoryCell();
        
        while (Supervisor.getProcessQueue().size() > 1) { // >= si on veut pas kill quand il est solo
            tried++;
            System.out.println("\n----- " + tried + " -----");
            //Thread.sleep(1000);
            MemoryCell nextInst = Supervisor.getNextInstructionCell();
            try {
                System.out.println("Hard index : " + nextInst.hardIndex + " | ID process : " + nextInst.getOwner());
                InstructionsInterpretor.ApplyInstruction(nextInst);
                corewarGui.updateMemoryToIndex(nextInst.getOwner(), nextInst.hardIndex);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(cache.hardIndex + " | " + cache.toStringDebug()); 
                break;
            }
            cache = nextInst; 
        }
        System.out.println("end" + Supervisor.getProgramCounter());
    }

    public static boolean isLinux() {
        System.out.println(System.getProperty("os.name"));
        if (System.getProperty("os.name").equals("Linux")) 
            return true;
        return false;
    }

    private static void initPlayersRedcodes(Memory mem) {
        Converter conv = new Converter();
        String orionPath = "resources\\players\\oblivion.rc";
        String oblivionPath = "resources\\players\\orion.rc";
        if (isLinux()){
            orionPath = "../resources/players/orion.rc";
            oblivionPath = "../resources/players/oblivion.rc";
        }
        
        conv.RedCodeToMemory(mem.start, orionPath, 1, false);
        conv.RedCodeToMemory(mem.getEmptySlot(), oblivionPath, 2, false);
    }
}
