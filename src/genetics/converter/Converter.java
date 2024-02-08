package genetics.converter;


public class Converter {
    private static String[] instruction = new String[] {"DAT", "MOV", "ADD", "SUB", "CMP", "SLT", "JMP", "JMZ", "JMN", "DJN", "SPL"};

    public static void toRedCode(String seed) {
        String[] separated = seed.split("\\|");
        StringBuilder sb = new StringBuilder();
        char[] modes = Converter.modes(Integer.valueOf(separated[0]), Integer.valueOf(separated[1]));
        sb.append(instruction[Integer.valueOf(separated[0])]);
        sb.append(" ");
        sb.append(modes[0]);
        sb.append(separated[2]);
        sb.append(", ");
        sb.append(modes[1]);
        sb.append(separated[3]);
        System.out.println(sb.toString());
    }

    private static char[] modes(int instruction, int mode) {
        if (instruction == 0)
            return Converter.mode1(mode);
        if (instruction < 6)
            return Converter.mode2(mode);
        if (instruction < 11)
            return Converter.mode3(mode);
        throw new IllegalArgumentException();
    }

    private static char[] mode1(int mode) {
        char[][] res = new char[][] {
            {'#', '#'},
            {'#', '<'},
            {'<', '#'},
            {'<', '<'}
        };
        return res[mode];
    }

    private static char[] mode2(int mode) {
        char[][] res = new char[][] {
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
        };
        return res[mode];
    }

    private static char[] mode3(int mode) {
        char[][] res = new char[][] {
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
        };
        return res[mode];
    }
}
