package display;

import javax.swing.*;
import vmcore.Supervisor.*;

public class CorewarGui{
    
    public Supervisor supervisor;
    JFrame frame = new JFrame("CoreWar");

    CorewarGui(Supervisor supervisor){

        this.supervisor = supervisor;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    void DisplaySupervisor(Supervisor supervisor){
        // This method will display the supervisor's memory
        
    }
}
