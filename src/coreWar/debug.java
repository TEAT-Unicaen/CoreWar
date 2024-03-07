package coreWar;

import coreWar.vmcore.interpreter.Adressage;
import coreWar.vmcore.interpreter.Converter;
import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.memory.memoryCellData.Operande;
import coreWar.vmcore.process.ProcessQueue;
import coreWar.vmcore.supervisor.Supervisor;
import coreWar.vmcore.interpreter.InstructionsInterpretor;

import java.util.Scanner;

import coreWar.display.CorewarGui;

public class debug {

    private static int sleep = 1000; 
    private static boolean debugMode = true; 
    
    public static void main(String args[]) throws InterruptedException {
        int tried = 0; 
        Supervisor supervisor = new Supervisor(1024);
        initPlayersRedcodes(supervisor.getMemory());
        CorewarGui corewarGui = new CorewarGui(supervisor);
        corewarGui.setVisible(true);
        MemoryCell cache = new MemoryCell();
        Scanner scanner = new Scanner(System.in);
        while (Supervisor.getProcessQueue().size() >= 1) { // >= si on veut pas kill quand il est solo
            tried++;
            System.out.println("\n----- " + tried + " -----");

            if (!debugMode) {
                Thread.sleep(sleep);
            } else {
                String debugMemoryToDisplay = scanner.nextLine();
                while (debugMemoryToDisplay != "") {
                    if (debugMemoryToDisplay.length() > 0) {
                        try {
                            int MemoryIndex = Integer.parseInt(debugMemoryToDisplay);
                            MemoryCell prov = supervisor.getMemory().start;
                            if (MemoryIndex > 0) {
                                for (int i = 0; i < MemoryIndex; i++) {
                                    prov = prov.getNext(); 
                                }
                            } else {
                                MemoryIndex *= -1;
                                for (int i = 0; i < MemoryIndex; i++) {
                                    prov = prov.getPrevious(); 
                                }
                            }
                            MemoryCell[] adressObj = Adressage.calcul(prov);
                            System.out.println("Details (" + prov.hardIndex + "): " + prov.toStringDebug());
                            System.out.println("Resultat des calculs d'operandes : " + adressObj[0].hardIndex + " | " + adressObj[1].hardIndex);
                        } catch (Exception e) {
                            System.out.println("Pass (invalid args)");
                        }
                        debugMemoryToDisplay = scanner.nextLine();
                    }
                }
            }

            MemoryCell nextInst = Supervisor.getNextInstructionCell(); //instruction executée

            try {
                ProcessQueue file = Supervisor.getProcessQueue();
                String display = "[";
                int size = file.size(); 
                if (size > 10) {
                    for (int i = 0; i < 10; i++) {
                        display += file.get(i).hardIndex + ", "; 
                    }
                    display+= "... " + (size-10) + " others "; 
                } else {
                    for (int i = 0; i < size-1; i++) {
                        display += file.get(i).hardIndex + ", "; 
                    }
                    display += file.get(size-1).hardIndex; 
                }

                display += "]"; 
                System.out.println(display);

                System.out.println("Hard index : " + nextInst.hardIndex + " | ID process : " + nextInst.getOwner());
                InstructionsInterpretor.ApplyInstruction(nextInst);
                corewarGui.updateMemoryToIndex(nextInst.getOwner(), nextInst.hardIndex);

            } catch (Exception e) { //gestion erreur 
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
        conv.RedCodeToMemory(mem.start, "src\\coreWar\\players\\debug\\debug1.rc", 1, false);
        conv.RedCodeToMemory(mem.getEmptySlot(), "src\\coreWar\\players\\debug\\debug2.rc", 2, false);
    }
}
