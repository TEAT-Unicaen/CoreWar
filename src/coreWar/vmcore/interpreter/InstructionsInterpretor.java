package coreWar.vmcore.interpreter;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.memory.memoryCellData.AdressingModeEnum;
import coreWar.vmcore.virtualMachine.Vm;

public class InstructionsInterpretor {

    private static MemoryCell setIndexForNextCase(MemoryCell mem) {
        MemoryCell nextCase = mem.getNext();
        nextCase.setOwner(mem.getOwner());
        return nextCase; 
    }

    public static void ApplyInstruction(MemoryCell mem, Vm vm) {
        MemoryCell[] adressObj = Adressage.calcul(mem);
        vm.playersInstance[mem.getOwner()] -= 1; 
        switch (mem.getInstruction()) {
            case DAT:
            vm.decrementProgramCounter();
                System.out.println("KILLED");
                break;
            case MOV:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE)
                    adressObj[1].getB().setValue(mem.getA().getValue());
                else
                    adressObj[1].pasteCell(adressObj[0].getInstruction(), adressObj[0].copyA(), adressObj[0].copyB());
                    vm.putInQueue(setIndexForNextCase(mem));
                break;
            case ADD: 
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) 
                    //on prend toujours les adresse pointées et pas les locales (valable pour A & B)
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() + mem.getA().getValue());
                else {
                    adressObj[1].getA().setValue(adressObj[0].getA().getValue() + adressObj[1].getA().getValue());
                    adressObj[1].getB().setValue(adressObj[0].getB().getValue() + adressObj[1].getB().getValue());
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case SUB:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE)
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() - mem.getA().getValue());
                else {
                    adressObj[1].getA().setValue(adressObj[1].getA().getValue() - adressObj[0].getA().getValue());
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() - adressObj[0].getB().getValue());
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case JMP:
            vm.putInQueue(adressObj[0]);
                break;
            case JMZ:
                if (mem.getB().getValue() == 0)
                    vm.putInQueue(adressObj[1]);
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case CMP:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) {
                    if (mem.getA().getValue() == adressObj[1].getB().getValue())
                        vm.putInQueue(mem,2);
                } else {
                    if (mem.getA().equals(mem.getB()))
                        vm.putInQueue(mem, 2);
                }
                break;
            case JMN: //relire 
                if (mem.getB().getValue() != 0)
                    vm.putInQueue(adressObj[0]);
                break;
            case SLT: 
                if (mem.getA().getMode() != AdressingModeEnum.IMMEDIATE) {
                    if (adressObj[0].getB().getValue() < mem.getB().getValue())
                        vm.putInQueue(mem,2);
                } else {
                    if (mem.getA().getValue() < mem.getB().getValue())
                        vm.putInQueue(mem,2);
                }
                break; 
            case DJN: 
                int prov = 0; 
                if (mem.getB().getMode() != AdressingModeEnum.IMMEDIATE)
                    prov = adressObj[1].getB().getValue()-1;
                else
                    prov = mem.getB().getValue()-1;
                if (prov != 0)
                    vm.putInQueue(adressObj[0]);
                break; 
            case SPL:
                vm.incrementProgramCounter();
                vm.putInQueue(setIndexForNextCase(mem));
                vm.putInQueue(mem, mem.getA().getValue());
                break; 
        }
    }
}
