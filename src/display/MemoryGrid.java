package display;

import javax.swing.*;

import java.awt.*;

import vmcore.Memory.Memory;
import vmcore.Memory.MemoryCell;
import vmcore.Supervisor.*;


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
            Color cellColor = determineCellColor(cell);
            cellPanel.setBackground(cellColor);
            cellPanel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
            add(cellPanel, constraints);
            cell = cell.GetNext();

            constraints.gridx++;
            if (constraints.gridx >= this.memory.getSize()/100) { //TODO : A modifier pour que la taille de la grille soit dynamique
                constraints.gridx = 0;
                constraints.gridy++;
            }

        } while (cell != memory.start);
    }


    //TODO : A modifier pour ne plus avoir de fonction de détermination de couleur
    private Color determineCellColor(MemoryCell cell) {
        int owner = cell.GetOwner();
        if (owner== 1) {
            return Color.BLUE;
        } else if (owner == 2) {
            return Color.RED;
        } else {
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
}
