package coreWar.vmcore.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import coreWar.vmcore.memory.MemoryCell;
import coreWar.vmcore.memory.memoryCellData.AdressingModeEnum;
import coreWar.vmcore.memory.memoryCellData.InstructionEnum;
import coreWar.vmcore.memory.memoryCellData.Operande;
import coreWar.vmcore.virtualMachine.Vm;

public class Converter {

    private static Map<String, AdressingModeEnum> StrToMode = new HashMap<String, AdressingModeEnum>() {
        {
            put("#", AdressingModeEnum.IMMEDIATE);
            put("@", AdressingModeEnum.INDIRECT);
            put("<", AdressingModeEnum.PREDEC);
        }
    };

    public static void RedCodeToMemory(MemoryCell memC, Reader red, int id, boolean verbose, Vm vm) {
        try (BufferedReader br = new BufferedReader(red)) {
            String line;
            int count = 0;
            //Memory feeding from file
            while((line = br.readLine()) != null) {
                count ++;
                if (count > 16)
                    throw new IllegalArgumentException("Redcode file is too long");
                String[] parts = line.split(" ");
                InstructionEnum inst = IsValidInstruction(parts[0]);
                if (inst == null)
                    throw new IllegalArgumentException("Invalid instruction: " + parts[0]);
                memC.setInstruction(inst);
                memC.setOwner(id);
                NormalizeForOperand(parts[1], parts[2], memC, id);
                if (verbose)
                    System.out.println(memC.getInstruction() + " " + memC.getA() +  memC.getB());
                if (count == 1)
                    vm.putInQueue(memC);
                memC = memC.getNext();
            }
            vm.incrementProgramCounter();
            br.close();
        } catch(IOException e) { 
            e.printStackTrace();
        }
    }

    public static void RedCodeToMemoryFromString(MemoryCell memC, String red, int id, boolean verbose, Vm vm) {
        StringReader sr = new StringReader(red);
        RedCodeToMemory(memC, sr, id, verbose, vm);
        sr.close();
    }

    public static void RedCodeToMemoryFromPath(MemoryCell memC, String path, int id, boolean verbose, Vm vm) {
        //IO Error handling
        try (FileReader fr = new FileReader(new File(path))) {
            RedCodeToMemory(memC, fr, id, verbose, vm);
            fr.close(); 
        } catch(IOException e) { 
            e.printStackTrace();
        }
    }

    public static InstructionEnum IsValidInstruction(String instStr) {
        // Invalid enum case handling
        try {
            return InstructionEnum.valueOf(instStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static void NormalizeForOperand(String opA, String opB, MemoryCell cell, int id) {
        // Operand A normalization
        Operande op = cell.getA();
        String[] p = opA.split("");

        for (int i = 0; i < 2; i++) {
            boolean isNeg = false;
            try {
                Integer.parseInt(p[0]);
                op.setMode(AdressingModeEnum.DIRECT);
            } catch (Exception e) { 
                op.setMode(StrToMode.get(p[0]));
                if (op.getMode() == null) {
                    op.setMode(AdressingModeEnum.DIRECT);
                    isNeg = true;
                }
            }

            String stringValue = "";
            for (int j = 0; j < p.length; j++) {
                try {
                    Integer.parseInt(p[j]);
                    stringValue += p[j];
                } catch (Exception e) {
                    if (p[j].equals("-")) {
                        isNeg = true;
                    }
                }
            }
            int value = Integer.parseInt(stringValue);
            if (isNeg)
                value *= -1;
            op.setValue(value);

            //Setting the new operation
            op = cell.getB();
            p = opB.split("");
        }
        cell.setOwner(id);
    }
}
