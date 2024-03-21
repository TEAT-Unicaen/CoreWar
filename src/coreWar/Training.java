package coreWar;

public class Training {
    public static void main(String[] args) throws InterruptedException {
        long time = System.currentTimeMillis();
        TrainingManager tr = new TrainingManager(1024, 2, "Trainning.bin", 100);
        
        tr.run();
        tr.exportPopulation("Trainning.bin");
        System.out.println("TEMPS : " + (System.currentTimeMillis() - time)/1000);
    }
}
