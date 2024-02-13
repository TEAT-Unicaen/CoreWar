package vmcore.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import vmcore.Memory.Instruction;
import vmcore.Memory.Modes;
import vmcore.Memory.MemoryCell;
import vmcore.Memory.Operande;

public class Converter {

    private static Map<String, Modes> StrToMode = new HashMap<String, Modes>(){
        {
            put("#", Modes.IMMEDIATE);
            put("@", Modes.INDIRECT);
            put("<", Modes.PREDEC);
        }
    };
    
    public Converter() {}

    public void RedCodeToMemory(MemoryCell memC, String path, int id) {
        //IO Error handling
        try {
            FileReader fr = new FileReader(new File(path));   
            BufferedReader br = new BufferedReader(fr); 
            String line;
            int count = 0;
            //Memory feeding from file
            while((line = br.readLine()) != null){
                count ++;
                if (count > 16) {
                    throw new IllegalArgumentException("Redcode file is too long");
                }
                String[] parts = line.split(" ");
                Instruction inst = IsValidInstruction(parts[0]);
                if (inst == null) {
                    throw new IllegalArgumentException("Invalid instruction: " + parts[0]);
                }

                NormalizeForOperand(parts[1], parts[2], memC, id);
                memC = memC.GetNext();
            }
            fr.close(); 
        }
        catch(IOException e) { 
            e.printStackTrace();
        }
    }

    public Instruction IsValidInstruction(String instStr) {
        // Invalid enum case handling
        try {
            return Instruction.valueOf(instStr);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void NormalizeForOperand(String opA, String opB, MemoryCell cell, int id) {
        // Operand A normalization
        Operande op = cell.GetA();
        String[] p = opA.split("");

        for (int i = 0; i < 2; i++) {
            if (p.length == 1) {
                op.SetMode(Modes.DIRECT);
            } else {
                op.SetMode(StrToMode.get(p[0]));
                op.SetValue(Integer.parseInt(p[1]));
            }
            op = cell.GetB();
            p = opB.split("");
        }
        cell.SetOwner(id);
    }
}
