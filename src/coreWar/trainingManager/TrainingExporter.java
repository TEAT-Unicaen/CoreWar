package coreWar.trainingManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import coreWar.genetics.Population;
import coreWar.genetics.seed.Seed;

public class TrainingExporter {
    public static void exportPopulation(Population population, int gen) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("LastPopulation.bin"))) {
            outputStream.writeObject(new TrainingData(population, gen));
        } catch (IOException e) {
            System.err.println("Error exporting Population: " + e.getMessage());
        }
    }

    public static void exportScore(List<List<Integer>> scores) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Scores.bin"))) {
            outputStream.writeObject(scores);
        } catch (IOException e) {
            System.err.println("Error exporting Population: " + e.getMessage());
        }
    }

    public static void exportChallenger(Seed challenger) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("Challenger.bin"))) {
            outputStream.writeObject(challenger);
        } catch (IOException e) {
            System.err.println("Error exporting Population: " + e.getMessage());
        }
    }
}
