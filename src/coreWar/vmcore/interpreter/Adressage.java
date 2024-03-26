package coreWar.vmcore.interpreter;

import coreWar.vmcore.memory.MemoryCell;

public class Adressage {

    public static int memorySize = 8000;

    public static MemoryCell calculA(MemoryCell mem) {
        MemoryCell value = mem; 
        switch (mem.getA().getMode()) {
            case DIRECT:
                value = Adressage.DirectA(mem);
                break;
            case INDIRECT:
                value = Adressage.InDirectA(mem);
                break;
            case IMMEDIATE:
                value = Adressage.ImmediateA(mem);
                break;
            case PREDEC: 
                value = Adressage.PredecA(mem);
                break;
        }
        return value;
    }

    public static MemoryCell calculB(MemoryCell mem) {
        MemoryCell value = mem;
        switch (mem.getB().getMode()) {
            case DIRECT:
                value = Adressage.DirectB(mem);
                break;
            case INDIRECT:
                value = Adressage.InDirectB(mem);
                break;
            case IMMEDIATE:
                value = Adressage.ImmediateB(mem);
                break;
            case PREDEC: 
                value = Adressage.PredecB(mem);
                break;
        }
        return value; 
    }

    public static MemoryCell[] calcul(MemoryCell mem) { //type true = obj | false = index 
        MemoryCell[] value = new MemoryCell[] {mem, mem};                         
        switch (mem.getA().getMode()) {
            case DIRECT:
                value[0] = Adressage.DirectA(mem);
                break;
            case INDIRECT:
                value[0] = Adressage.InDirectA(mem);
                break;
            case IMMEDIATE:
                value[0] = Adressage.ImmediateA(mem);
                break;
            case PREDEC: 
                value[0] = Adressage.PredecA(mem);
                break;
        }
        switch (mem.getB().getMode()) {
            case DIRECT:
                value[1] = Adressage.DirectB(mem);
                break;
            case INDIRECT:
                value[1] = Adressage.InDirectB(mem);
                break;
            case IMMEDIATE:
                value[1] = Adressage.ImmediateB(mem);
                break;
            case PREDEC: 
                value[1] = Adressage.PredecB(mem);
                break;
        }
        return value; 
    }

    private static MemoryCell DirectA(MemoryCell mem) {
        MemoryCell prov = mem; 
        int value = mem.getA().getValue();
        if (value > Adressage.memorySize || value < -Adressage.memorySize)
            value = value%Adressage.memorySize;
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.getNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.getPrevious();
        }
        return prov; //.getB();
    }

    private static MemoryCell InDirectA(MemoryCell mem) {
        return DirectB(DirectA(mem));
    }

    private static MemoryCell DirectB(MemoryCell mem) {
        MemoryCell prov = mem; 
        int value = mem.getB().getValue();
        if (value > Adressage.memorySize || value < -Adressage.memorySize)
            value = value%Adressage.memorySize;
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.getNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.getPrevious();
        }
        return prov; //.getB();
    }

    private static MemoryCell InDirectB(MemoryCell mem) {
        return DirectB(DirectB(mem));
    }

    private static MemoryCell ImmediateA(MemoryCell mem) {
        return mem; //.getA();
    }

    private static MemoryCell ImmediateB(MemoryCell mem) {
        return mem; //.getB();
    }

    private static MemoryCell PredecA(MemoryCell mem) { //Maybe return direct mode here
        MemoryCell provTemp = Adressage.DirectA(mem);
        int vlaueTemp = provTemp.getB().getValue();
        provTemp.getB().setValue(vlaueTemp-1);
        MemoryCell res = Adressage.DirectB(provTemp);
        provTemp.getB().setValue(vlaueTemp);
        return res; 
    }

    private static MemoryCell PredecB(MemoryCell mem) {
        MemoryCell provTemp = Adressage.DirectB(mem);
        int vlaueTemp = provTemp.getB().getValue();
        provTemp.getB().setValue(vlaueTemp-1);
        MemoryCell res = Adressage.DirectB(provTemp);
        provTemp.getB().setValue(vlaueTemp);
        return res; 
    }
}