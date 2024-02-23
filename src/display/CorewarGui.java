package display;

import javax.swing.*;

import vmcore.Memory.Memory;
import vmcore.Supervisor.Supervisor;

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

    public void updateMemory(Memory newMemory) {
        memoryGrid.updateMemory(newMemory);
    }
}
