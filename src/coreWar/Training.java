package coreWar;

import java.io.IOException;

public class Training {
    public static void main(String[] args) throws InterruptedException {
        long time = System.currentTimeMillis();
        String nameFile = "Training.bin";
        TrainingManager tr;
        
        try {
            tr = new TrainingManager(1024, 100, nameFile, 100);
        } catch (IOException e) {
            tr = new TrainingManager(1024, 100, 100, 100);
        }

        tr.run();
        tr.exportPopulation("Training.bin");
        System.out.println("TEMPS : " + (System.currentTimeMillis() - time)/1000);
    }
}
