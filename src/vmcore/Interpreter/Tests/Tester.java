package vmcore.Interpreter.Tests;

import vmcore.Interpreter.Converter;
import vmcore.Memory.Memory;

public class Tester {
    public static void main(String[] args) {
        Memory mem = new Memory(25);
        Converter conv = new Converter();
        conv.RedCodeToMemory(mem.start, "src\\vmcore\\Interpreter\\Tests\\redcode.txt", 1, true);
    }
}
