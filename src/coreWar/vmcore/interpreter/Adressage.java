package coreWar.vmcore.interpreter;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.memory.memoryCellData.AdressingModeEnum;

public class Adressage {

    public static Object[] calcul(MemoryCell mem, boolean type) { //type true = obj | false = index 
        Object[] value = new Object[] {};                         //TODO : remove value if not used 
        switch (mem.getA().getMode()) {
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
        switch (mem.getB().getMode()) {
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
        int value = prov.getA().getValue();
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.getNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.getPrevious();
        }
        if (type)
            return prov.getB();
        return prov.getB().getValue();
    }

    private static Object DirectB(MemoryCell mem, boolean type) {
        MemoryCell prov = mem; 
        int value = prov.getB().getValue();
        if (mem.getA().getMode() == AdressingModeEnum.INDIRECT)// Maybe B mode (not A)
            value += prov.getA().getValue();
        if (value > 0) {
            for (int i = 0; i < value; i++)
                prov = prov.getNext();
        } else {
            value *= -1; 
            for (int i = 0; i < value; i++)
                prov = prov.getPrevious();
        }
        if (type)
            return prov.getB();
        return prov.getB().getValue();
    }

    private static Object ImmediateA(MemoryCell mem, boolean type) {
        if (type)
            return mem.getA();
        return mem.getA().getValue();
    }

    private static Object ImmediateB(MemoryCell mem, boolean type) {
        if (type)
            return mem.getB();
        return mem.getB().getValue();
    }

    private static Object PredecA(MemoryCell mem, boolean type) { //Maybe return direct mode here
        if (!type) {
            int prov = (Integer) Adressage.DirectA(mem, type) - 1;
            mem.getA().setValue(prov);
            return prov; 
        } else {
            MemoryCell provTemp = (MemoryCell) Adressage.DirectA(mem, type);
            mem.getA().setValue(provTemp.getPrevious().getB().getValue()); //pas sur (voir dans DirectA si c'est un obj (on revient 1 cran en arriere pr reprendre l'index))
            return provTemp.getPrevious(); 
        }
    }

    /*
     *  private static int PredecA(MemoryCell mem) { //Maybe return direct mode here 
        int prov =  Adressage.DirectA(mem) -1;
        mem.getA().setValue(prov);
        return prov; 
    }
     */

    private static Object PredecB(MemoryCell mem, boolean type) {
        if (!type) {
            int prov = (Integer) Adressage.DirectB(mem, type) - 1;
            mem.getB().setValue(prov);
            return prov; 
        } else {
            MemoryCell provTemp = (MemoryCell) Adressage.DirectB(mem, type);
            mem.getB().setValue(provTemp.getPrevious().getB().getValue()); //pas sur (voir dans DirectA si c'est un obj (on revient 1 cran en arriere pr reprendre l'index))
            return provTemp.getPrevious(); 
        }
    }

    /*
     * private static int PredecB(MemoryCell mem) {
        int prov =  Adressage.DirectB(mem) -1;
        mem.getB().setValue(prov);
        return prov; 
    }
     */
}


