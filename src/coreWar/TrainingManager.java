package coreWar;

import java.util.ArrayList;
import java.util.List;

import coreWar.genetics.Population;
import coreWar.genetics.seed.Seed;
import coreWar.vmcore.virtualMachine.Vm;

public class TrainingManager {
    private List<Population> populations;
    private Supervisor sup;
    private int vmSize, populationNumber, individuNumber;

    public TrainingManager(int vmSize, int populationNumber, int individuNumber) {
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
            List<Vm> vms = new ArrayList<Vm>();
            for (int j = 0; j < this.individuNumber; j++) {
                Seed seed1 = seedList.get(j);
                for (int k = j; k < this.individuNumber; k++) {
                    Seed seed2 = seedList.get(k);
                    this.sup.createVm(vmSize, seed1.getRedcode(), seed2.getRedcode(),j,k);
                }
                vms.addAll(this.sup.getValues());
            }
            int winnerPoint = 100;
            double deathRatio = -15;
            for (Vm virtualMachine : vms) {
                Seed seed1 = seedList.get(virtualMachine.uuid[0]);
                Seed seed2 = seedList.get(virtualMachine.uuid[1]);
                switch (virtualMachine.winner) {
                    case 1:
                        this.populations.get(i).addPoint(seed1, winnerPoint);
                        break;
                    case 2:
                        this.populations.get(i).addPoint(seed2, winnerPoint);
                        break;
                    default:
                        break;
                }
                if (virtualMachine.death[0] != 0) {
                    this.populations.get(i).addPoint(seed1, (int)(virtualMachine.death[1]/virtualMachine.death[0] * deathRatio));
                    this.populations.get(i).addPoint(seed1, (int)(virtualMachine.death[2]/virtualMachine.death[0]* deathRatio));
                }
            }
            this.populations.add(this.populations.get(i).nextPopulation());
            System.gc();
        }
    }

    public void export() {
        System.out.println(this.populations.get(this.populations.size()-1).getTheWinner().getRedcode());
    }
}
