package coreWar;

import coreWar.vmcore.interpreter.Converter;
import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.virtualMachine.Vm;
import coreWar.vmcore.interpreter.InstructionsInterpretor; 
import coreWar.display.CorewarGui;

public class RunSingle {
    
    private static Vm supervisor = new Vm(1024,100);

    public static void main(String args[]) throws InterruptedException {
        int tried = 0; 
        InstructionsInterpretor interpretor = new InstructionsInterpretor();
        initPlayersRedcodes(supervisor.getMemory());
        CorewarGui corewarGui = new CorewarGui(supervisor);
        corewarGui.setVisible(true);
        MemoryCell cache = new MemoryCell();
        
        while (supervisor.getProcessQueue().size() > 1) { // >= si on veut pas kill quand il est solo
            tried++;
            System.out.println("\n----- " + tried + " -----");
            Thread.sleep(1);
            MemoryCell nextInst = supervisor.getNextInstructionCell();
            try {
                System.out.println("Hard index : " + nextInst.hardIndex + " | ID process : " + nextInst.getOwner());
                interpretor.ApplyInstruction(nextInst,supervisor);
                corewarGui.updateMemoryToIndex(nextInst.getOwner(), nextInst.hardIndex);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(cache.hardIndex + " | " + cache.toStringDebug()); 
                break;
            }
            cache = nextInst; 
        }
        System.out.println("end" + supervisor.getProgramCounter());
    }

    public static boolean isLinux() {
        System.out.println(System.getProperty("os.name"));
        if (System.getProperty("os.name").equals("Linux")) 
            return true;
        return false;
    }

    private static void initPlayersRedcodes(Memory mem) {
        String orionPath = "resources\\players\\oblivion.rc";
        String oblivionPath = "resources\\players\\orion.rc";
        if (isLinux()){
            orionPath = "../resources/players/orion.rc";
            oblivionPath = "../resources/players/oblivion.rc";
        }
        
        Converter.RedCodeToMemoryFromPath(mem.start, orionPath, 1, false, supervisor);
        Converter.RedCodeToMemoryFromPath(mem.mid, oblivionPath, 2, false, supervisor);
    }
}
