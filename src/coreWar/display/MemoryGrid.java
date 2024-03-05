package coreWar.display;

import javax.swing.*;

import coreWar.vmcore.memory.Memory;
import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.supervisor.*;

import java.awt.*;


public class MemoryGrid extends JPanel{
    
    private Memory memory;


    public MemoryGrid(Supervisor supervisor) {
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
        
        do {
            JPanel cellPanel = new JPanel();
            Color cellColor = determineCellColor(cell.getOwner());
            cellPanel.setBackground(cellColor);
            //cellPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            add(cellPanel, constraints);
            cell = cell.getNext();

            constraints.gridx++;
            if (constraints.gridx >= Math.sqrt(this.memory.getSize())) {
                constraints.gridx = 0;
                constraints.gridy++;
            }

        } while (cell != memory.start);
    }

    public static Color determineCellColor(int ownerIndex) {
        switch (ownerIndex) {
            case 1:
                return Color.BLUE;
            case 2: 
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    //TODO : trouver plus opti pour mettre a jour affichage de la mémoire
    public void updateMemory(Memory newMemory) {
        this.memory = newMemory;
        removeAll();
        displayMemory();
        revalidate();
        repaint();
    }

    public void updateMemoryToIndex(int ownerIndex, int index) {
        Component[] components = getComponents();
        JPanel cellPanel = (JPanel) components[index];      
        cellPanel.setBackground(determineCellColor(ownerIndex));
    }

    public void updateMemoryToIndexWithColor(Color newColor, int index) {
        Component[] components = getComponents();
        JPanel cellPanel = (JPanel) components[index];      
        cellPanel.setBackground(newColor);
    }
}
