package vmcore.Interpreter.adressage;

import vmcore.Memory.MemoryCell;
import vmcore.Memory.Modes;

public class Adressage {

    public static Object[] calcul(MemoryCell mem, boolean type) { //type true = obj | false = index 
        Object[] value = new Object[] {};
        switch (mem.GetA().GetMode()) {
            case DIRECT:
            case INDIRECT:
                value[0] = Adressage.DirectA(mem, type);
                break;
            case IMMEDIATE:
                value[0] = Adressage.ImmediateA(mem, type);
                break;
            case PREDEC: 
                value[0] = Adressage.PredecA(mem, type);
                break;
        }
        switch (mem.GetB().GetMode()) {
            case DIRECT:
            case INDIRECT:
                value[1] = Adressage.DirectB(mem, type);
                break;
            case IMMEDIATE:
                value[1] = Adressage.ImmediateB(mem, type);
                break;
            case PREDEC: 
                value[1] = Adressage.PredecB(mem, type);
                break;
        }
        return value; 
    }

    private static Object DirectA(MemoryCell mem, boolean type) {
        MemoryCell prov = mem; 
        int value = prov.GetA().GetValue();
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.GetNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.GetPrevious();
        }
        if (type) {
            return prov.GetB();
        } 
        return prov.GetB().GetValue();
    }

    private static Object DirectB(MemoryCell mem, boolean type) {
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
        if (type) {
            return prov.GetB();
        }
        return prov.GetB().GetValue();
    }

    private static Object ImmediateA(MemoryCell mem, boolean type) {
        if (type) {
            return mem.GetA();
        }
        return mem.GetA().GetValue();    
    }

    private static Object ImmediateB(MemoryCell mem, boolean type) {
        if (type) {
            return mem.GetB();
        }
        return mem.GetB().GetValue();
    }

    private static Object PredecA(MemoryCell mem, boolean type) { //Maybe return direct mode here
        if (!type) {
            int prov = (Integer) Adressage.DirectA(mem, type) - 1;
            mem.GetA().SetValue(prov);
            return prov; 
        } else {
            MemoryCell provTemp = (MemoryCell) Adressage.DirectA(mem, type);
            mem.GetA().SetValue(provTemp.GetPrevious().GetB().GetValue()); //pas sur (voir dans DirectA si c'est un obj (on revient 1 cran en arriere pr reprendre l'index))
            return provTemp.GetPrevious(); 
        }
    }

    /*
     *  private static int PredecA(MemoryCell mem) { //Maybe return direct mode here 
        int prov =  Adressage.DirectA(mem) -1;
        mem.GetA().SetValue(prov);
        return prov; 
    }
     */

    private static Object PredecB(MemoryCell mem, boolean type) {
        if (!type) {
            int prov = (Integer) Adressage.DirectB(mem, type) - 1;
            mem.GetB().SetValue(prov);
            return prov; 
        } else {
            MemoryCell provTemp = (MemoryCell) Adressage.DirectB(mem, type);
            mem.GetB().SetValue(provTemp.GetPrevious().GetB().GetValue()); //pas sur (voir dans DirectA si c'est un obj (on revient 1 cran en arriere pr reprendre l'index))
            return provTemp.GetPrevious(); 
        }
    }

    /*
     * private static int PredecB(MemoryCell mem) {
        int prov =  Adressage.DirectB(mem) -1;
        mem.GetB().SetValue(prov);
        return prov; 
    }
     */
}


