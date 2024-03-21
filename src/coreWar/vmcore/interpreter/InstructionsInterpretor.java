package coreWar.vmcore.interpreter;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.memory.memoryCellData.AdressingModeEnum;
import coreWar.vmcore.memory.memoryCellData.InstructionEnum;
import coreWar.vmcore.virtualMachine.Vm;

public class InstructionsInterpretor {

    public int[] LoopProtector = {0,0};
    private InstructionEnum lastP1;
    private InstructionEnum lastP2;

    private MemoryCell setIndexForNextCase(MemoryCell mem) {
        MemoryCell nextCase = mem.getNext();
        nextCase.setOwner(mem.getOwner());
        return nextCase; 
    }
    

    public void ApplyInstruction(MemoryCell mem, Vm vm) throws Exception {
        MemoryCell[] adressObj = Adressage.calcul(mem);
        vm.playersInstance[mem.getOwner()] -= 1;
        InstructionEnum instruction = mem.getInstruction();
        System.out.println(instruction);
        if (mem.getOwner() == 1) {
            if (lastP1 == null) {
                lastP1 = instruction;
            } else {
                if (lastP1 == instruction) {
                    this.LoopProtector[0] += 1;
                } else {
                    this.LoopProtector[0] = 0; 
                }
            }
        } else {
            if (lastP2 == null) {
                lastP2 = instruction;
            } else {
                if (lastP2 == instruction) {
                    this.LoopProtector[1] += 1;
                } else {
                    this.LoopProtector[1] = 0;
                }
            }
        }
        if (LoopProtector[0] >= 5 || LoopProtector[1] >= 5) {
            System.out.println("Loop protection");
            throw new LoopException("Infinite loop detected / Wrong REDCODE value"); 
        }

        switch (instruction) {
            case DAT:
                vm.decrementProgramCounter();
                vm.death[mem.getOwner()]++;
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
                //if (adressObj[1] == mem) {
                //    throw new LoopException("Infinite loop detected / Wrong REDCODE value");
                //}
                vm.putInQueueWithOwner(adressObj[0],mem.getOwner());
                break;
            case JMZ:
                if (mem.getB().getValue() == 0) {
                    //if (adressObj[1] == mem) {
                    //    throw new LoopException("Infinite loop detected / Wrong REDCODE value");
                    //}
                    vm.putInQueueWithOwner(adressObj[1],mem.getOwner());
                    break;
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case CMP:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) {
                    if (mem.getA().getValue() == adressObj[1].getB().getValue()) {
                        vm.putInQueue(mem,2);
                        break;
                    }
                }
                if (mem.getA().equals(mem.getB())) {
                    vm.putInQueue(mem, 2);
                    break;
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case JMN:  
                if (mem.getB().getValue() != 0) {
                    vm.putInQueueWithOwner(adressObj[0],mem.getOwner());
                    break;
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case SLT: 
                if (mem.getA().getMode() != AdressingModeEnum.IMMEDIATE) {
                    if (adressObj[0].getB().getValue() < mem.getB().getValue()) {
                        vm.putInQueue(mem,2);
                        break;
                    }
                }
                else if (mem.getA().getValue() < mem.getB().getValue()) {
                    vm.putInQueue(mem,2);
                    break;
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case DJN: 
                int prov = 0; 
                if (mem.getB().getMode() != AdressingModeEnum.IMMEDIATE)
                    prov = adressObj[1].getB().getValue()-1;
                else
                    prov = mem.getB().getValue()-1;
                if (prov != 0)
                    vm.putInQueueWithOwner(adressObj[0],mem.getOwner());
                break; 
            case SPL:
                vm.incrementProgramCounter();
                vm.putInQueue(setIndexForNextCase(mem));
                vm.putInQueue(mem, mem.getA().getValue());
                break; 
        }
    }
}
