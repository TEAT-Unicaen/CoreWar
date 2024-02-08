package vmcore.Interpreter;

import vmcore.Memory.MemoryCell;

public class Interpreter {
    
    private File file; 

    public Interpreter() {
        this.file = new File(); 
    }

    public void Enfile(MemoryCell mem) {
        this.file.Enfile(mem);
    } 

    public void CalculNextInstruction() {
        MemoryCell inst = this.file.Defile();
        if (inst != null) {
            System.out.println("aled");
        }
    }
}
