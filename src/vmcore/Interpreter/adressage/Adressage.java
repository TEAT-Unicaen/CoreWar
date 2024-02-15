package vmcore.Interpreter.adressage;

import vmcore.Memory.MemoryCell;
import vmcore.Memory.Modes;

public class Adressage {

    public static int[] calcul(MemoryCell mem) {
        int[] value = new int [] {};
        switch (mem.GetA().GetMode()) {
            case DIRECT:
            case INDIRECT:
                value[0] = Adressage.DirectA(mem);
                break;
            case IMMEDIATE:
                value[0] = Adressage.ImmediateA(mem);
                break;
            case PREDEC: 
                value[0] = Adressage.PredecA(mem);
                break;
        }
        switch (mem.GetB().GetMode()) {
            case DIRECT:
            case INDIRECT:
                value[1] = Adressage.DirectB(mem);
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

    private static int DirectA(MemoryCell mem) {
        MemoryCell prov = mem; 
        int value =  prov.GetA().GetValue();
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.GetNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.GetPrevious();
        }
        return prov.GetB().GetValue();
    }

    private static int DirectB(MemoryCell mem) {
        MemoryCell prov = mem; 
        int value = prov.GetB().GetValue();
        if (mem.GetA().GetMode() == Modes.INDIRECT)// Maybe B mode (not A)
            value += prov.GetA().GetValue();
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.GetNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.GetPrevious();
        }
        return prov.GetB().GetValue();
    }

    private static int ImmediateA(MemoryCell mem) {
        return mem.GetA().GetValue();
    }

    private static int ImmediateB(MemoryCell mem) {
        return mem.GetB().GetValue();
    }

    private static int PredecA(MemoryCell mem) { //Maybe return direct mode here 
        int prov =  Adressage.DirectA(mem) -1;
        mem.GetA().SetValue(prov);
        return prov; 
    }

    private static int PredecB(MemoryCell mem) {
        int prov =  Adressage.DirectB(mem) -1;
        mem.GetB().SetValue(prov);
        return prov; 
    }

}
