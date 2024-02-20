package vmcore.Supervisor;

import vmcore.Memory.MemoryCell;
import vmcore.Memory.Modes;
import vmcore.Interpreter.adressage.Adressage;

public class Instructions {

    public static void ApplyInstruction(MemoryCell mem) {
        MemoryCell[] adressObj = (MemoryCell[]) Adressage.calcul(mem, true);
        //Integer[] adress = (Integer[]) Adressage.calcul(mem, false); 
        switch (mem.GetInstruction()) {
            case DAT:
                break;
            case MOV:
                if (mem.GetA().GetMode() == Modes.IMMEDIATE) {
                    adressObj[1].GetB().SetValue(mem.GetA().GetValue()); 
                } else {
                    adressObj[1].PasteCell(adressObj[0].GetInstruction(), adressObj[0].CopyA(), adressObj[0].CopyB());
                }
            case ADD:
                if (mem.GetA().GetMode() == Modes.IMMEDIATE) {
                    adressObj[1].GetB().SetValue(adressObj[1].GetB().GetValue() + mem.GetA().GetValue());
                } else {
                    // TODO : handle cases
                }
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
