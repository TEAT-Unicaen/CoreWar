package coreWar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import coreWar.genetics.Population;
import coreWar.genetics.seed.Seed;
import coreWar.vmcore.virtualMachine.Vm;

public class Training {
    public static void main(String[] args) throws InterruptedException {
        Supervisor sup = new Supervisor();
        int vmSize = 1024;
        int populationNumber = 1;
        int individuNumber = 100;
        List<Population> p = new ArrayList<Population>(populationNumber);
        p.add(new Population(individuNumber));
        for (int i = 0; i < populationNumber; i++) {
            System.out.println("GENERATION : " + i);
            Population acPopulation = p.get(i);
            List<Seed> seedList = new ArrayList<>(acPopulation.keySet());
            for (int j = 0; j < individuNumber; j++) {
                Seed seed1 = seedList.get(j);
                for (int k = j; k < individuNumber; k++) {
                    Seed seed2 = seedList.get(k);
                    sup.createVm(vmSize, seed1.getRedcode(), seed2.getRedcode());
                }
            }
            List<Vm> res = sup.getValues();
            System.out.println(res);
        }
    }
}
