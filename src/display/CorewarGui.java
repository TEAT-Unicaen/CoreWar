package display;

import javax.swing.*;

import java.awt.Color;

import vmcore.memory.Memory;
import vmcore.memory.MemoryCell;
import vmcore.supervisor.Supervisor;

public class CorewarGui extends JFrame {
    
    private MemoryGrid memoryGrid;

    CorewarGui(Supervisor supervisor) {
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

    public void updateMemoryToIndex(MemoryCell cell, int index) {
        memoryGrid.updateMemoryToIndex(cell, index);
    }

    public void updateMemoryToIndexWithColor(Color color, int index) {
        memoryGrid.updateMemoryToIndexWithColor(color, index);
    }
}
