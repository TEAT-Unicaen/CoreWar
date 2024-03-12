package coreWar.display;

import javax.swing.*;

import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.virtualMachine.Vm;

import java.awt.Color;

public class CorewarGui extends JFrame {
    
    private MemoryGrid memoryGrid;

    public CorewarGui(Vm supervisor) {
        setTitle("Corewar");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        memoryGrid = new MemoryGrid(supervisor);
        add(memoryGrid);
        setVisible(true);
    }

    public void updateCompleteMemory(Memory newMemory) {
        memoryGrid.updateMemory(newMemory);
    }

    public void updateMemoryToIndex(int owner, int index) {
        memoryGrid.updateMemoryToIndex(owner, index);
    }

    public void updateMemoryToIndexWithColor(Color color, int index) {
        memoryGrid.updateMemoryToIndexWithColor(color, index);
    }
}
