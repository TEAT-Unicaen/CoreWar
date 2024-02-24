package vmcore.interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import vmcore.memory.memoryCellData.InstructionEnum;
import vmcore.memory.memoryCellData.AdressingModeEnum;
import vmcore.memory.memoryCellData.Operande;
import vmcore.memory.MemoryCell;


public class Converter {

    private static Map<String, AdressingModeEnum> StrToMode = new HashMap<String, AdressingModeEnum>() {
        {
            put("#", AdressingModeEnum.IMMEDIATE);
            put("@", AdressingModeEnum.INDIRECT);
            put("<", AdressingModeEnum.PREDEC);
        }
    };

    public Converter() {}

    public void RedCodeToMemory(MemoryCell memC, String path, int id, boolean verbose) {
        //IO Error handling
        try (FileReader fr = new FileReader(new File(path))) {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                int count = 0;
                //Memory feeding from file
                while((line = br.readLine()) != null){
                    count ++;
                    if (count > 16)
                        throw new IllegalArgumentException("Redcode file is too long");
                    String[] parts = line.split(" ");
                    InstructionEnum inst = IsValidInstruction(parts[0]);
                    if (inst == null)
                        throw new IllegalArgumentException("Invalid instruction: " + parts[0]);
                    memC.setInstruction(inst);
                    NormalizeForOperand(parts[1], parts[2], memC, id);
                    if (verbose)
                        System.out.println(memC.getInstruction() + " " + memC.getA() +  memC.getB());
                    memC = memC.getNext();
                }
                br.close();
            }
            fr.close(); 
        }
        catch(IOException e) { 
            e.printStackTrace();
        }
    }

    public InstructionEnum IsValidInstruction(String instStr) {
        // Invalid enum case handling
        try {
            return InstructionEnum.valueOf(instStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void NormalizeForOperand(String opA, String opB, MemoryCell cell, int id) {
        // Operand A normalization
        Operande op = cell.getA();
        String[] p = opA.split("");

        for (int i = 0; i < 2; i++) {
            int l = p.length;
            if (l == 1) {
                op.setMode(AdressingModeEnum.DIRECT);
                op.setValue(Integer.parseInt(p[0]));
            } else {
                int idx = 1;
                try {
                    Integer.parseInt(p[0]);
                    op.setMode(AdressingModeEnum.DIRECT);
                    idx = 0;
                } catch (Exception e) { 
                    op.setMode(StrToMode.get(p[0]));
                }

                try {
                    op.setValue(Integer.parseInt(p[idx]+p[idx+1]));
                } catch (Exception e) {
                    op.setValue(Integer.parseInt(p[idx]));
                }
            }
            op = cell.getB();
            p = opB.split("");
        }
        cell.setOwner(id);
    }
}
