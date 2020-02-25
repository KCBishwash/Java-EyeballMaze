package src.leroi_chua_model;

public enum Color {
    BLUE("B"),
    GREEN("G"),
    YELLOW("Y"),
    RED("R");

    private final String color;

    Color(String aColor) {
        this.color = aColor;
    }

    public static Color fromString(String tileColorCode) {
        for (Color aColorCode : Color.values()) {
            if (aColorCode.color.equalsIgnoreCase(tileColorCode)) {
                return aColorCode;
            }
        }
        return null;
    }

    public static String getStringFromColor(Color which) {
        switch (which) {
            case BLUE:
                return "blue";
            case RED:
                return "red";
            case YELLOW:
                return "yellow";
            case GREEN:
                return "green";
            default:
                return "white";
        }
    }
}
  