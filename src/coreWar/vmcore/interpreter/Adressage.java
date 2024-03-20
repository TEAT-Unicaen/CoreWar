package coreWar.vmcore.interpreter;

import coreWar.vmcore.memory.MemoryCell;

public class Adressage {

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
        int value = prov.getA().getValue();
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
        int value = prov.getB().getValue();
        //if (mem.getA().getMode() == AdressingModeEnum.INDIRECT)// Maybe B mode (not A)
        //   value += prov.getA().getValue();
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
        mem.getA().setValue(provTemp.getPrevious().getB().getValue()); //pas sur (voir dans DirectA si c'est un obj (on revient 1 cran en arriere pr reprendre l'index))
        return provTemp.getPrevious(); 
    }

    /*
     *  private static int PredecA(MemoryCell mem) { //Maybe return direct mode here 
        int prov =  Adressage.DirectA(mem) -1;
        mem.getA().setValue(prov);
        return prov; 
    }
     */

    private static MemoryCell PredecB(MemoryCell mem) {
        MemoryCell provTemp = Adressage.DirectB(mem);
        mem.getB().setValue(provTemp.getPrevious().getB().getValue()); //pas sur (voir dans DirectA si c'est un obj (on revient 1 cran en arriere pr reprendre l'index))
        return provTemp.getPrevious(); 
    }

    /*
     * private static int PredecB(MemoryCell mem) {
        int prov =  Adressage.DirectB(mem) -1;
        mem.getB().setValue(prov);
        return prov; 
    }
     */
}