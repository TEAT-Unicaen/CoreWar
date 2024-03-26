package coreWar.trainingManager;

import java.io.Serializable;

import coreWar.genetics.Population;

public class TrainingData implements Serializable {
    Population population;
    int gen;

    public TrainingData(Population popu, int gen) {
        this.population = popu;
        this.gen = gen;
    }
}
