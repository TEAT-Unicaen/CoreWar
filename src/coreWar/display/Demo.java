package coreWar.display;

import coreWar.vmcore.virtualMachine.Vm;

public class Demo {

    public static void main(String[] args) {
        Vm supervisor = new Vm(1024,100);
        CorewarGui corewarGui = new CorewarGui(supervisor);


        corewarGui.updateMemoryToIndexWithColor(java.awt.Color.RED, 536);
    }
}
