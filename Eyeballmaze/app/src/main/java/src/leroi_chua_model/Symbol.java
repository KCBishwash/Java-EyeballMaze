package src.leroi_chua_model;

public enum Symbol {
    FLOWER("F"),
    DIAMOND("D"),
    CROSS("C"),
    STAR("S");

    private final String aSymbol;

    Symbol(String aSymbol) {
        this.aSymbol = aSymbol;
    }

    public static Symbol fromString(String tileSymbolCode) {
        for (Symbol aSymbolCode : Symbol.values()) {
            if (aSymbolCode.aSymbol.equalsIgnoreCase(tileSymbolCode)) {
                return aSymbolCode;
            }
        }
        return null;
    }

    public static String getStringFromSymbol(Symbol which) {
        switch (which) {
            case STAR:
                return "star";
            case FLOWER:
                return "coral";
            case CROSS:
                return "cross";
            case DIAMOND:
                return "diamond";
            default:
                return "blank";
        }
    }
}
