package coreWar.display;

import coreWar.vmcore.supervisor.Vm;

public class Demo {

    public static void main(String[] args) {
        Vm supervisor = new Vm(1024);
        CorewarGui corewarGui = new CorewarGui(supervisor);


        corewarGui.updateMemoryToIndexWithColor(java.awt.Color.RED, 536);
    }
}
