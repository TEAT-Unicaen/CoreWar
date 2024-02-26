package coreWar.vmcore.interpreter.tests;

import coreWar.vmcore.interpreter.Converter;
import coreWar.vmcore.memory.Memory;

public class Demo {
    public static void main(String[] args) {
        Memory mem = new Memory(25);
        Converter conv = new Converter();
        conv.RedCodeToMemory(mem.start, "src\\vmcore\\Interpreter\\Tests\\redcode.rc", 1, true);
    }
}
