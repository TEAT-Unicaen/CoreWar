package coreWar.trainingManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import coreWar.Supervisor;
import coreWar.genetics.Population;
import coreWar.genetics.seed.Seed;
import coreWar.vmcore.virtualMachine.Vm;

public class TrainingManager {
    private Population population;
    private Supervisor sup;
    private int vmSize, genCount, individualCount, threadCount, actualGen;


    private List<List<Integer>> trainingScores;

    private void init(int vmSize, int genCount, int threadCount) {
        this.sup = new Supervisor();
        this.vmSize = vmSize;
        this.genCount = genCount;
        this.individualCount = 0;
        this.threadCount = threadCount;
        this.trainingScores = TrainingImporter.exportScores();
    }

    public TrainingManager(int vmSize, int genCount, int threadCount) throws IOException {
        this.init(vmSize, genCount, threadCount);
        TrainingData data = TrainingImporter.importTraining();
        population = data.population.nextPopulation();
        this.individualCount = population.size();
        this.actualGen = data.gen+1;
    }

    public TrainingManager(int vmSize, int genCount, int individualCount, int threadCount) {
        this.init(vmSize, genCount, threadCount);
        this.individualCount = individualCount;
        this.population = new Population(this.individualCount);
        this.actualGen = 0;
    }

    public void training() {
        for(; this.actualGen < this.genCount; this.actualGen++) {
            System.out.println("Generation : " + this.actualGen);
            List<Seed> seedList = new ArrayList<Seed>(this.population.keySet());
            List<Vm> vms = new ArrayList<Vm>();
            int threadAlive = 0;
            for (int i = 0; i < this.individualCount; i++) {
                String red1 = seedList.get(i).getRedcode();
                for (int j = 0; j < this.individualCount; j++) {
                    String red2 = seedList.get(j).getRedcode();
                    this.sup.createVm(vmSize, red1, red2, i, j);
                    if (++threadAlive == this.threadCount) {
                        vms.addAll(sup.getValues());
                        threadAlive = 0;
                    }
                }
            }
            int minTick = 32; // minimum tick for a Vm to add Point to the winner otherwise decrese the looser
            for (Vm virtualMachine : vms) {
                Seed seed1 = seedList.get(virtualMachine.uuid[0]);
                Seed seed2 = seedList.get(virtualMachine.uuid[1]);
                switch (virtualMachine.getMVP()) {
                    case 1:
                        if (virtualMachine.tick < minTick) {
                            this.population.addPoint(seed2, -1);
                        } else
                            this.population.addPoint(seed1, 1);
                        break;
                    case 2:
                        if (virtualMachine.tick < minTick) {
                            this.population.addPoint(seed2, -1);
                        } else
                            this.population.addPoint(seed2, 1);
                        break;
                    default:
                        break;
                }
            }
            this.trainingScores.add(new ArrayList<Integer>(this.population.values()));
            this.exportPopulation();
        }
        //System.out.println(population);
        //System.out.println(this.trainingScores);
        System.out.println(this.population.get(this.population.getTheWinner()));
        System.out.println(this.population.getTheWinner().getRedcode());
    }

    public void exportPopulation() {
        TrainingExporter.exportPopulation(this.population, this.actualGen, this.trainingScores);
    }
}
