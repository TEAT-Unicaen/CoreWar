package coreWar;

import java.io.IOException;

import coreWar.trainingManager.TrainingManager;

public class Training {
    public static void main(String[] args) throws InterruptedException {
        long time = System.currentTimeMillis();
        TrainingManager tr;

        int vmSize = 1024;
        int genCount = 1000;
        int individualCount = 100;
        int threadCount = 100;
        
        try {
            tr = new TrainingManager(vmSize, genCount, threadCount);
        } catch (IOException e) {
            tr = new TrainingManager(vmSize, genCount, individualCount, threadCount);
        }
        tr.training();

        System.out.println("TEMPS : " + (System.currentTimeMillis() - time)/1000);
    }
}
