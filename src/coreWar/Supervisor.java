package coreWar;

import coreWar.vmcore.virtualMachine.Vm;
import coreWar.vmcore.virtualMachine.ThreadVm;
import coreWar.vmcore.interpreter.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Supervisor {

    /*
     * Gestion multithreading sans sortie graphique juste des retours de résultats     
    */

    private List<ThreadVm> threadList;
    
    public Supervisor() {
        this.threadList = new ArrayList<ThreadVm>();
    }

    public void createVm(int size, String red1, String red2) {
        Vm virtualMachine = new Vm(size);
        Converter.RedCodeToMemoryFromString(virtualMachine.getMemory().start, red1, 1, false, virtualMachine);
        Converter.RedCodeToMemoryFromString(virtualMachine.getMemory().getEmptySlot(), red2, 2,false, virtualMachine); 
        ThreadVm thread = new ThreadVm(virtualMachine);
        this.threadList.add(thread);
        thread.start();
    }

    public List<Vm> getValues() throws InterruptedException {
        List<Vm> results = new ArrayList<>(); 
        for (ThreadVm current : this.threadList) {
            int waiter = 0;
            while (current.isAlive() && waiter < 10)
                System.out.println("DATA : "+current.isAlive()+" | " + waiter++ );
                Thread.sleep(100);
            if (waiter >= 10) {
                current.kill();
            }
            results.add(current.getVm());
        }
        return results; 
    }
}
