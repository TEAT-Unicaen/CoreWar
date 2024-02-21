package display;

import javax.swing.*;
import java.awt.*;

import vmcore.Memory.Memory;
import vmcore.Memory.MemoryCell;
import vmcore.Supervisor.*;

public class MemoryViewer extends JPanel{
    private Memory memory;

    public MemoryViewer(Supervisor supervisor) {
        this.memory = supervisor.getMemory();
        setLayout(new GridBagLayout());
        displayMemory();
    }

    private void displayMemory() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0; // Permet à la grille de s'étendre horizontalement
        constraints.weighty = 1.0; // Permet à la grille de s'étendre verticalement
        constraints.fill = GridBagConstraints.BOTH; // Permet à chaque cellule de remplir l'espace disponible

        MemoryCell cell = memory.start;

        while (cell != memory.start.GetPrevious()) {
            JPanel cellPanel = new JPanel();
            Color cellColor = determineCellColor(cell);
            cellPanel.setBackground(cellColor);
            add(cellPanel, constraints);
            cell = cell.GetNext();
        }
    }

    private Color determineCellColor(MemoryCell cell) {
        int value = cell.GetB().GetValue();
        if (value == 1) {
            return Color.BLUE;
        } else if (value == 2) {
            return Color.RED;
        } else {
            return Color.WHITE;
        }
    }

    public void updateMemory(Memory newMemory) {
        this.memory = newMemory;
        removeAll();
        displayMemory();
        revalidate();
        repaint();
    }





}
