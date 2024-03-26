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
        vm.playersInstance[mem.getOwner()] -= 1;
        MemoryCell[] adressObj = {null, null}; 
        InstructionEnum instruction = mem.getInstruction();
        if (mem.getOwner() == 1) {
            if (lastP1 == null) {
                lastP1 = instruction;
            } else {
                if (lastP1 == instruction) {
                    this.LoopProtector[0] += 1;
                } else {
                    this.LoopProtector[0] = 0;
                    lastP1 = instruction;
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
                    lastP2 = instruction;
                }
            }
        }
        if (LoopProtector[0] >= 5 || LoopProtector[1] >= 5) {
            //System.out.println("Loop protection");
            throw new LoopException("Infinite loop detected / Wrong REDCODE value"); 
        }

        switch (instruction) {
            case DAT:
                vm.decrementProgramCounter();
                vm.death[mem.getOwner()]++;
                break;
            case MOV:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) {
                    adressObj[1] = Adressage.calculB(mem);
                    adressObj[1].getB().setValue(mem.getA().getValue());
                } else {
                    adressObj = Adressage.calcul(mem);
                    adressObj[1].pasteCell(adressObj[0].getInstruction(), adressObj[0].copyA(), adressObj[0].copyB());
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case ADD: 
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) {
                    adressObj[1] = Adressage.calculB(mem);
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() + mem.getA().getValue());
                } else {
                    adressObj = Adressage.calcul(mem);
                    adressObj[1].getA().setValue(adressObj[0].getA().getValue() + adressObj[1].getA().getValue());
                    adressObj[1].getB().setValue(adressObj[0].getB().getValue() + adressObj[1].getB().getValue());
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case SUB:
                if (mem.getA().getMode() == AdressingModeEnum.IMMEDIATE) {
                    adressObj[1] = Adressage.calculB(mem);
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() - mem.getA().getValue());
                } else {
                    adressObj = Adressage.calcul(mem);
                    adressObj[1].getA().setValue(adressObj[1].getA().getValue() - adressObj[0].getA().getValue());
                    adressObj[1].getB().setValue(adressObj[1].getB().getValue() - adressObj[0].getB().getValue());
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case JMP:
                adressObj = Adressage.calcul(mem);
                if (adressObj[1] == mem) {
                   throw new LoopException("Infinite loop detected / Wrong REDCODE value");
                }
                vm.putInQueueWithOwner(adressObj[0],mem.getOwner());
                break;
            case JMZ:
                adressObj[1] = Adressage.calculB(mem);
                if (mem.getB().getValue() == 0) {
                    if (adressObj[1] == mem) {
                       throw new LoopException("Infinite loop detected / Wrong REDCODE value");
                    }
                    vm.putInQueueWithOwner(adressObj[1],mem.getOwner());
                    break;
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case CMP:
                adressObj[1] = Adressage.calculB(mem);
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
                adressObj[0] = Adressage.calculA(mem);
                if (mem.getB().getValue() != 0) {
                    vm.putInQueueWithOwner(adressObj[0],mem.getOwner());
                    break;
                }
                vm.putInQueue(setIndexForNextCase(mem));
                break;
            case SLT: 
                adressObj[0] = Adressage.calculA(mem);
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
                if (mem.getB().getMode() != AdressingModeEnum.IMMEDIATE) {
                    adressObj[1] = Adressage.calculB(mem);
                    prov = adressObj[1].getB().getValue()-1;
                } else
                    prov = mem.getB().getValue()-1;
                if (prov != 0) {
                    adressObj[0] = Adressage.calculA(mem);
                    vm.putInQueueWithOwner(adressObj[0],mem.getOwner());
                }
                break; 
            case SPL:
                vm.incrementProgramCounter();
                vm.putInQueue(setIndexForNextCase(mem));
                int tmp = mem.getA().getValue();
                if (tmp > Adressage.memorySize || tmp < -Adressage.memorySize) 
                    tmp = tmp%Adressage.memorySize;
                vm.putInQueue(mem, tmp);
                break; 
        }
    }
}
