package coreWar;

public class Training {
    public static void main(String[] args) throws InterruptedException {
        TrainingManager tr = new TrainingManager(1024, 100, 100);

        tr.run();
        tr.export();
    }
}
