package vmcore.Supervisor;

import vmcore.Memory.MemoryCell;

public class Instructions {
    
    public static void ApplyInstruction(MemoryCell mem) {
        switch (mem.GetInstruction()) { //surement pas au bon endroit attendre discussion
            case DAT:
                break;
            case MOV:
                break;
            case ADD:
                break;
            case SUB:
                break;
            case JMP:
                break;
            case JMZ:
                break;
            case JMN:
                break;
            case CMP:
                break;
            case SLT: 
                break; 
            case DJN: 
                break; 
            case SPL:
                break;
        }
    }

}
