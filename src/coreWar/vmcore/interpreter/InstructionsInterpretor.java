package coreWar.vmcore.interpreter;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.memory.memoryCellData.AdressingModeEnum;
import coreWar.vmcore.supervisor.Supervisor;

public class InstructionsInterpretor {

    public static void ApplyInstruction(MemoryCell mem) {
        MemoryCell[] adressObj = Adressage.calcul(mem);
        switch (mem.getInstruction()) {
            case DAT:
                Supervisor.decrementProgramCounter();
                break;
            case MOV:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE)
                    adressObj[1].getB().setValue(mem.getA().getValue());
                else
                    adressObj[1].pasteCell(adressObj[0].getInstruction(), adressObj[0].copyA(), adressObj[0].copyB());
                Supervisor.putInQueue(mem.getNext());
                break;
            case ADD: 
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE)
                    //on prend toujours les adresse pointées et pas les locales (valable pour A & B)
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() + mem.getA().getValue());
                else {
                    adressObj[1].getA().setValue(mem.getA().getValue() + adressObj[1].getA().getValue());
                    adressObj[1].getB().setValue(mem.getB().getValue() + adressObj[1].getB().getValue());
                }
                Supervisor.putInQueue(mem.getNext());
                break;
            case SUB:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE)
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() - mem.getA().getValue());
                else {
                    adressObj[1].getA().setValue(adressObj[1].getA().getValue() - mem.getA().getValue());
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() - mem.getB().getValue());
                }
                Supervisor.putInQueue(mem.getNext());
                break;
            case JMP:
                Supervisor.putInQueue(adressObj[0]);
                break;
            case JMZ:
                if (mem.getB().getValue() == 0)
                    Supervisor.putInQueue(adressObj[1]);
                Supervisor.putInQueue(mem.getNext());
                break;
            case CMP:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) {
                    if (mem.getA().getValue() == adressObj[1].getB().getValue())
                        Supervisor.putInQueue(mem,2);
                } else {
                    if (mem.getA().equals(mem.getB()))
                        Supervisor.putInQueue(mem, 2);
                }
                break;
            case JMN: //relire 
                if (mem.getB().getValue() != 0)
                    Supervisor.putInQueue(adressObj[0]);
                break;
            case SLT: 
                if (mem.getA().getMode() != AdressingModeEnum.IMMEDIATE) {
                    if (adressObj[0].getB().getValue() < mem.getB().getValue())
                        Supervisor.putInQueue(mem,2);
                } else {
                    if (mem.getA().getValue() < mem.getB().getValue())
                        Supervisor.putInQueue(mem,2);
                }
                break; 
            case DJN: 
                int prov = 0; 
                if (mem.getB().getMode() != AdressingModeEnum.IMMEDIATE) {
                    prov = adressObj[1].getB().getValue()-1;
                    adressObj[1].getB().setValue(prov);
                } else {
                    prov = mem.getB().getValue()-1;
                    mem.getB().setValue(prov);
                }
                if (prov != 0)
                    Supervisor.putInQueue(adressObj[0]);
                break; 
            case SPL:
                Supervisor.incrementProgramCounter();
                Supervisor.putInQueue(mem.getNext());
                Supervisor.putInQueue(mem, mem.getA().getValue());
                break; 
        }
    }
}
