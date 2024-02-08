package vmcore.Interpreter;

import java.util.ArrayList;
import vmcore.Memory.MemoryCell;

public class File {
    
    private ArrayList<MemoryCell> file = new ArrayList<>(); 

    public File(){};

    public void Enfile(MemoryCell mem) {
        this.file.add(mem); 
    };

    public MemoryCell Defile() {
        if (this.file == null) {return null;}
        int size = this.file.size() - 1;
        if (size <= 0) {return null;}
        MemoryCell value = this.file.get(size);
        this.file.remove(size);
        return value; 
    };

}
