package coreWar.genetics.seed;

public class RedCode {
    private static String[] instructionL = new String[] { 
        "DAT", "MOV", "ADD", "SUB", "CMP", "SLT", "JMP", "JMZ", "JMN", "DJN", "SPL"
    };

    private static char[][][] adressing = new char[][][] {
        {
            {'#', '#'},
            {'#', '<'},
            {'<', '#'},
            {'<', '<'}
        }, {
            {0, 0},
            {0, '@'},
            {0, '<'},
            {'#', 0},
            {'#', '@'},
            {'#', '<'},
            {'@', 0},
            {'@', '@'},
            {'@', '<'},
            {'<', 0},
            {'<', '@'},
            {'<', '<'}
        }, {
            {0, 0},
            {0, '#'},
            {0, '@'},
            {0, '<'},
            {'@', 0},
            {'@', '#'},
            {'@', '@'},
            {'@', '<'},
            {'<', 0},
            {'<', '#'},
            {'<', '@'},
            {'<', '<'}
        }
    };

    public static String getRecode(Seed seed) {
        StringBuilder sb = new StringBuilder();
        for (SeedLine line : seed) {
            sb.append(RedCode.getRedCodeLine(line));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String getRedCodeLine(SeedLine seed) {
        StringBuilder sb = new StringBuilder();
        char[] mod = RedCode.mods(seed.getInstruction(), seed.getAdressingMode());
        sb.append(RedCode.instructionL[seed.getInstruction()]);
        sb.append(" ");
        sb.append(mod[0]);
        sb.append(seed.getValue1());
        sb.append(", ");
        sb.append(mod[1]);
        sb.append(seed.getValue2());
        return sb.toString();
    }

    private static char[] mods(int instruction, int mod) {
        if (instruction == 0)
            return RedCode.adressing[0][mod];
        if (instruction < 6)
            return RedCode.adressing[1][mod];
        if (instruction < 11)
            return RedCode.adressing[2][mod];
        throw new IllegalArgumentException();
    }
}