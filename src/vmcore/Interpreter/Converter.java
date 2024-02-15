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

    public void RedCodeToMemory(MemoryCell memC, String path, int id, boolean verbose) {
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

                memC.SetInstruction(inst);
                NormalizeForOperand(parts[1], parts[2], memC, id);
                if (verbose) {System.out.println(memC.GetInstruction() + " " + memC.GetA() +  memC.GetB());}
                memC = memC.GetNext();
            }
            br.close();
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
            int l = p.length;
            if (l == 1) {
                op.SetMode(Modes.DIRECT);
                op.SetValue(Integer.parseInt(p[0]));
            } else {
                int idx = 1;
                try {
                    Integer.parseInt(p[0]);
                    op.SetMode(Modes.DIRECT);
                    idx = 0;
                } catch (Exception e) { 
                    op.SetMode(StrToMode.get(p[0]));
                }

                try {
                    op.SetValue(Integer.parseInt(p[idx]+p[idx+1]));
                } catch (Exception e) {
                    op.SetValue(Integer.parseInt(p[idx]));
                }
            }
            op = cell.GetB();
            p = opB.split("");
        }
        cell.SetOwner(id);
    }
}
