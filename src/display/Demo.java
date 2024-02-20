package display;

import display.CorewarGui;
import vmcore.Supervisor.Supervisor;

public class Demo {

    
    
    public static void main(String[] args) {

        
        // This is the main method for the display package
        CorewarGui gui = new CorewarGui(supervisor);
        gui.DisplaySupervisor();
    }
}
