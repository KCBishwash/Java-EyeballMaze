package src.leroi_chua_model;

public class Tile {
    public int x;
    public int y;
    public String tileInfo;
    public Color color;
    public Symbol symbol;
    public boolean isGoal;
    public boolean isBlankTile;
    public boolean isBlankGoal;
    public boolean isPlayer;
    public boolean isSolution;

    public Tile(int x, int y, String tileInfo) {
        this.x = x;
        this.y = y;
        this.tileInfo = tileInfo;
    }

    public void buildMyTile() {
        String[] tileProperties = this.tileInfo.split("");
        String symbolCode = tileProperties[1];
        this.addSymbol(symbolCode);
        String colorCode = tileProperties[2];
        this.addColor(colorCode);
        String goalCode = tileProperties[3];
        this.hasGoal(goalCode);
        String playerCode = tileProperties[4];
        this.hasPlayer(playerCode);
        String solutionCode = tileProperties[5];
        this.hasSolution(solutionCode);
        this.isBlankTile = this.checkIfBlankTile(tileProperties[1]);
    }

    public boolean isPlayerOnGoal() {
        boolean playerIsOnGoal = false;
        if (this.isGoal & this.isPlayer) {
            this.isBlankGoal = true;
            this.isGoal = false;
            playerIsOnGoal = true;
        }
        return playerIsOnGoal;
    }

    private boolean checkIfBlankTile(String symbol) {
        boolean isBlankTile = false;
        if (symbol.equalsIgnoreCase(" ")) {
            isBlankTile = true;
        }
        return isBlankTile;
    }

    private void hasGoal(String goal) {
        this.isGoal = goal.equalsIgnoreCase("X");
    }

    private void hasPlayer(String player) {
        this.isPlayer = player.equalsIgnoreCase("@");
    }

    private void hasSolution(String solution) {
        this.isSolution = solution.equalsIgnoreCase("*");
    }

    private void addColor(String colorCode) {
        Color aColor = Color.fromString(colorCode);
        this.color = aColor;
    }

    private void addSymbol(String symbolCode) {
        Symbol aSymbol = Symbol.fromString(symbolCode);
        this.symbol = aSymbol;
    }

    public String toString() {
        return ("My Coord y:" + this.y + " x: " + this.x + "  Player Tile:" + this.isPlayer + "  Symbol:" +
                this.symbol + "  Color:" + this.color + "  Goal tile:" + this.isGoal +
                "  Blank Goal Tile:" + this.isBlankGoal + "  Solution tile:" + this.isSolution +
                " emptyTile: " + this.isBlankTile);
    }
}
