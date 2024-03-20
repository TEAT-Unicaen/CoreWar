package coreWar;

import java.util.ArrayList;
import java.util.List;

import coreWar.genetics.Population;
import coreWar.genetics.seed.Seed;
import coreWar.vmcore.virtualMachine.Vm;

public class Training {
    private List<Population> populations;
    private Supervisor sup;
    private int vmSize, populationNumber, individuNumber;

    public Training(int vmSize, int populationNumber, int individuNumber) {
        this.sup = new Supervisor();
        this.vmSize = vmSize;
        this.populationNumber = populationNumber;
        this.individuNumber = individuNumber;
        this.populations = new ArrayList<Population>(populationNumber);
        this.populations.add(new Population(this.individuNumber));
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < this.populationNumber; i++) {
            System.out.println("GENERATION : " + i);
            Population acPopulation = this.populations.get(i);
            List<Seed> seedList = new ArrayList<>(acPopulation.keySet());
            for (int j = 0; j < this.individuNumber; j++) {
                Seed seed1 = seedList.get(j);
                for (int k = j; k < this.individuNumber; k++) {
                    Seed seed2 = seedList.get(k);
                    this.sup.createVm(vmSize, seed1.getRedcode(), seed2.getRedcode(),j,k);
                }
            }
            List<Vm> res = sup.getValues();
            System.out.println(res);
        }
    }

    public void export() {}
}
