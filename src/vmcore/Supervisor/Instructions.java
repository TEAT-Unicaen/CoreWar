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
                Supervisor.PutInQueue(mem);
                break;
            case ADD: 
                if (mem.GetA().GetMode() == Modes.IMMEDIATE) {
                    //on prend toujours les adresse pointées et pas les locales (valable pour A & B)
                    adressObj[1].GetB().SetValue(adressObj[1].GetB().GetValue() + mem.GetA().GetValue());
                } else {
                    adressObj[1].GetA().SetValue(mem.GetA().GetValue() + adressObj[1].GetA().GetValue());
                    adressObj[1].GetB().SetValue(mem.GetB().GetValue() + adressObj[1].GetB().GetValue());
                }
                Supervisor.PutInQueue(mem);
                break;
            case SUB:
                if (mem.GetA().GetMode() == Modes.IMMEDIATE) {
                    adressObj[1].GetB().SetValue(adressObj[1].GetB().GetValue() - mem.GetA().GetValue());
                } else {
                    adressObj[1].GetA().SetValue(adressObj[1].GetA().GetValue() - mem.GetA().GetValue());
                    adressObj[1].GetB().SetValue(adressObj[1].GetB().GetValue() - mem.GetB().GetValue());
                }
                Supervisor.PutInQueue(mem);
                break;
            case JMP:
                Supervisor.FileAppel.Enfile(adressObj[0]);
                break;
            case JMZ:
                if (mem.GetB().GetValue() == 0) {
                    Supervisor.FileAppel.Enfile(adressObj[1]);
                }
                Supervisor.PutInQueue(mem);
                break;
            case CMP:
                if (mem.GetA().GetMode() == Modes.IMMEDIATE) {
                    if (mem.GetA().GetValue() == adressObj[1].GetB().GetValue()) {
                        Supervisor.PutInQueue(mem,2);
                    } 
                } else {
                    if (mem.GetA().equals(mem.GetB())) {
                        Supervisor.PutInQueue(mem, 2);
                    }
                }
                break;
            case JMN: //relire 
                if (mem.GetB().GetValue() != 0) {
                    Supervisor.PutInQueue(adressObj[0]);
                }
                break;
            case SLT: 
                if (mem.GetA().GetMode() != Modes.IMMEDIATE) {
                    if (adressObj[0].GetB().GetValue() < mem.GetB().GetValue()) {
                        Supervisor.PutInQueue(mem,2);
                    }
                } else {
                    if (mem.GetA().GetValue() < mem.GetB().GetValue()) {
                        Supervisor.PutInQueue(mem,2);
                    }
                }
                break; 
            case DJN: 
                int prov = 0; 
                if (mem.GetB().GetMode() != Modes.IMMEDIATE) {
                    prov = adressObj[1].GetB().GetValue()-1;
                    adressObj[1].GetB().SetValue(prov);
                } else {
                    prov = mem.GetB().GetValue()-1;
                    mem.GetB().SetValue(prov);
                }
                if (prov != 0) {
                    Supervisor.PutInQueue(adressObj[0]);
                }
                break; 
            case SPL:
                Supervisor.incrementPorgramCounter();
                Supervisor.PutInQueue(mem);
                Supervisor.PutInQueue(mem,mem.GetA().GetValue());
        }
    }

}
