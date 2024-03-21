package coreWar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import coreWar.genetics.Population;
import coreWar.genetics.seed.Seed;
import coreWar.vmcore.virtualMachine.Vm;

public class TrainingManager {
    private List<Population> populations;
    private Supervisor sup;
    private int vmSize, genCount, individualCount, threadCount, actualGen;

    private TrainingManager(int vmSize, int genCount, int threadCount) {
        this.sup = new Supervisor();
        this.vmSize = vmSize;
        this.genCount = genCount;
        this.individualCount = 0;
        this.threadCount = threadCount;
    }

    public TrainingManager(int vmSize, int genCount, String populationsPath, int threadCount) {
        this(vmSize, genCount, threadCount);
        this.importPopulation(populationsPath);
        this.individualCount = populations.get(0).size();
        this.actualGen = populations.size()-1;
    }

    public TrainingManager(int vmSize, int genCount, int individualCount, int threadCount) {
        this(vmSize, genCount, threadCount);
        this.individualCount = individualCount;
        this.populations = new ArrayList<Population>(genCount);
        this.populations.add(new Population(this.individualCount));
        this.actualGen = 0;
    }

    public void run() throws InterruptedException {
        for (; this.actualGen < this.genCount; this.actualGen++) {
            System.out.println("GENERATION : " + this.actualGen);
            Population actualPopulation = this.populations.get(this.actualGen);
            List<Seed> seedList = new ArrayList<Seed>(actualPopulation.keySet());
            List<Vm> vms = new ArrayList<Vm>();
            int threadAlive = 0;
            for (int i = 0; i < this.individualCount; i++) {
                Seed seed1 = seedList.get(i);
                for (int j = 0; j < this.individualCount; j++) {
                    if (i != j) {
                        Seed seed2 = seedList.get(j);
                        this.sup.createVm(vmSize, seed1.getRedcode(), seed2.getRedcode(), i, j);
                        if (++threadAlive == this.threadCount) {
                            vms.addAll(sup.getValues());
                            threadAlive = 0;
                        }
                    }
                }
            }
            int winnerPoint = 100;
            double deathRatio = -15;
            for (Vm virtualMachine : vms) {
                Seed seed1 = seedList.get(virtualMachine.uuid[0]);
                Seed seed2 = seedList.get(virtualMachine.uuid[1]);
                switch (virtualMachine.winner) {
                    case 1:
                        actualPopulation.addPoint(seed1, winnerPoint);
                        if (virtualMachine.tick < 32) {
                            actualPopulation.addPoint(seed2, -5);
                        }
                        break;
                    case 2:
                        actualPopulation.addPoint(seed2, winnerPoint);
                        if (virtualMachine.tick < 32) {
                            actualPopulation.addPoint(seed2, -5);
                        }
                        break;
                    default:
                        break;
                }
                if (virtualMachine.death[0] != 0) {
                    actualPopulation.addPoint(seed1, (int)(virtualMachine.death[1]/virtualMachine.death[0] * deathRatio));
                    actualPopulation.addPoint(seed1, (int)(virtualMachine.death[2]/virtualMachine.death[0]* deathRatio));
                }
            }
            this.populations.add(actualPopulation.nextPopulation());
            System.gc();
        }
    }

    public void exportPopulation(String populationsPath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(populationsPath))) {
            outputStream.writeObject(this.populations);
            System.out.println("List of Population exported to file successfully.");
        } catch (IOException e) {
            System.err.println("Error exporting List of Population: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void importPopulation(String populationsPath) {
        this.populations = new ArrayList<Population>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(populationsPath))) {
            this.populations = (ArrayList<Population>) inputStream.readObject();
            System.out.println("List of Population imported from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error importing List of Population: " + e.getMessage());
            System.exit(1);
        }
    }
}
