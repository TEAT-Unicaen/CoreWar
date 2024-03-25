package coreWar;

import coreWar.vmcore.virtualMachine.Vm;
import coreWar.vmcore.virtualMachine.ThreadVm;
import coreWar.vmcore.interpreter.Converter;
import coreWar.vmcore.memory.memoryCellData.InstructionEnum;

import java.util.ArrayList;
import java.util.List;

public class Supervisor {

    /*
     * Gestion multithreading sans sortie graphique juste des retours de résultats     
    */

    private List<ThreadVm> threadList;
    
    public Supervisor() {
        this.threadList = new ArrayList<ThreadVm>();
    }

    /**
     * Initialise et execute les vm dans des threads 
     * 
     * @param size taille de la mémoire 
     * @param red1 redcode du programme 1  
     * @param red2 redcode du programme 2 
     */
    public void createVm(int size, String red1, String red2, int uuid1, int uuid2) {
        Vm virtualMachine = new Vm(size,100,new int[] {uuid1, uuid2});
        Converter.RedCodeToMemoryFromString(virtualMachine.getMemory().start, red1, 1, false, virtualMachine);
        Converter.RedCodeToMemoryFromString(virtualMachine.getMemory().mid, red2, 2,false, virtualMachine); 
        ThreadVm thread = new ThreadVm(virtualMachine);
        this.threadList.add(thread);
        thread.start();
    }

    /**
     * @return renvoie une liste de vm executée
     * @throws InterruptedException attend la fin d'execution des programmes (3s max)
     */
    public List<Vm> getValues() throws InterruptedException {
        List<Vm> results = new ArrayList<>(); 
        for (ThreadVm current : this.threadList) {
            long time = System.currentTimeMillis();
            while (current.isAlive()) { //TODO : Find infinite loops without breaking optimisation
                if (System.currentTimeMillis() - time > 1000) {
                    InstructionEnum inst = current.getLastMemCell().getInstruction();
                    int incrementThreshold = 0;
                    while (current.getLastMemCell().getInstruction() == inst) {
                        incrementThreshold++;
                        if (incrementThreshold > 10) {
                            System.err.println(current.getLastMemCell().toStringDebug() + " | " + current + " | " + current.getVm().tick);

                            current.kill();
                            break;
                        }
                        System.out.print("before -- ");
                        Thread.sleep(200);
                        System.out.println("after");
                    }
                }
            }
            current.kill();
            results.add(current.getVm());
            // if (System.currentTimeMillis() - time > 0)
            //     System.out.println("Loop time took : " + (System.currentTimeMillis() - time));

        }
        this.threadList.clear();
        return results; 
    }
}
