package vmcore.Supervisor;

import vmcore.Memory.Instruction;
import vmcore.Memory.MemoryCell;
import vmcore.Memory.Modes;
import vmcore.Interpreter.adressage.Adressage;

public class Instructions {

    private void SetDataAtRelativeIndex(MemoryCell mem, int index, Instruction inst) {
        MemoryCell prov = mem;
        if (index > 0) {
            for (int i = 0; i < index; i ++) {
                prov = prov.GetNext();
            }
        } else {
            index *= -1;
            for (int i = 0; i < index; i++) {
                prov = prov.GetPrevious();
            }
        }
        prov.SetInstruction(inst);
    }
    
    public static void ApplyInstruction(MemoryCell mem) {
        switch (mem.GetInstruction()) { //surement pas au bon endroit attendre discussion
            MemoryCell[] adressObj = (MemoryCell[]) Adressage.calcul(mem, true);
            //Integer[] adress = (Integer[]) Adressage.calcul(mem, false); 
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
                    
                } else {

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
