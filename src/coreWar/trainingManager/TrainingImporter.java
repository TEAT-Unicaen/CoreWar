package coreWar.trainingManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import coreWar.genetics.seed.Seed;

public class TrainingImporter {

    public static TrainingData importTraining() throws IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("LastPopulation.bin"))) {
            return (TrainingData) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("File not found");
        }
    }

    @SuppressWarnings("unchecked")
    public static List<List<Integer>> importScores() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Scores.bin"))) {
            return (List<List<Integer>>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<List<Integer>>();
        }
    }

    public static Seed importChallenger() throws IOException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("Challenger.bin"))) {
            return (Seed) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("File not found");
        }
    }
}
