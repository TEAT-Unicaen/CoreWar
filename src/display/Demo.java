package display;

import vmcore.supervisor.Supervisor;

public class Demo {

    public static void main(String[] args) {
        Supervisor supervisor = new Supervisor(1024);
        CorewarGui corewarGui = new CorewarGui(supervisor);


        corewarGui.updateMemoryToIndexWithColor(java.awt.Color.RED, 536);
    }
}
