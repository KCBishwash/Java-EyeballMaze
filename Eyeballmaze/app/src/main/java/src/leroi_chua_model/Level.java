package src.leroi_chua_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Level {
    public Player myPlayer;
    public int moveCounter = 0;
    public boolean levelPassed = false;
    public int[] previousPlayerPosition = new int[2];
    private String name;
    private String[][] allMyTiles;
    private int myLevelSize;
    private int myRowSize;
    private int myColumnSize;
    private ArrayList<Tile> myLevel = new ArrayList<Tile>();


    public Level(String levelName, String[][] aLevel) {
        this.name = levelName;
        this.allMyTiles = aLevel;
    }

    public void buildMyLevel() {
        this.addTiles();
        this.addPlayer();
        this.printMyLevel();
    }

    public int getRemainingGoalsCount() {
        int output = 0;
        for (Tile aTile : this.myLevel) {
            if (aTile.isGoal && !aTile.isBlankGoal) {
                output++;
            }
        }
        return output;
    }

    public String[][] getAllMyTiles() {
        return allMyTiles;
    }

    public ArrayList<Tile> getMyLevel() {
        return myLevel;
    }

    public boolean updateMyLevel(int[] nextMove) {
        this.myPlayer.setLastDirection();
        String playerDirection = this.myPlayer.orientPlayer(nextMove);
        this.myPlayer.setCurrentDirection(playerDirection);
        boolean isInvalidMove = this.checkIfValidMove(nextMove, playerDirection);
        if (!isInvalidMove) {
            this.moveCounter += 1;
            System.out.println("\nYour total moves so far: " + this.moveCounter + "\n");
            this.updateLevel(nextMove);
            this.myPlayer.updatePosition(nextMove);
            this.checkIfPlayerOnGoal(nextMove);
            this.checkIfLevelPassed();
        }

        return this.levelPassed;

    }

    public void undo() {
        int x = previousPlayerPosition[0];
        int y = previousPlayerPosition[1];
        int[] newcoords = new int[]{x, y};
        this.myPlayer.currentDirection = this.myPlayer.lastDirection;
        this.moveCounter += 1;
        System.out.println("\nYour total moves so far: " + this.moveCounter + "\n");
        this.updateLevel(newcoords);
        this.myPlayer.updatePosition(newcoords);
    }

    public void updateLevel(int[] userMoveCoords) {
        this.removePlayerPosition();
        this.clearMyLevel();
        this.addTiles();
        this.setPlayerPosition(userMoveCoords);
        this.printMyLevel();
    }

    public boolean checkIfValidMove(int[] userMoveCoords, String nextMove) { //Supposed to say "Check if Invalid move".... LeRoi!!!!"
        boolean isInvalidMove = false;
        boolean isOutOfBounds = this.checkIfMoveWillBeOutOfBounds(userMoveCoords);
        boolean isBackwards = this.myPlayer.checkUserNotMovingBackwards(nextMove);
        boolean isNotInLineDirection = this.myPlayer.checkIfStraightDirection(userMoveCoords);
        boolean isInvalidLocation = this.checkIfNextMoveInValidLocation(userMoveCoords);
        if (isNotInLineDirection) {
            isInvalidMove = true;
            System.out.println("NO MOVING IN DIAGONALS YOU CHEATER...");
        } else if (isBackwards) {
            isInvalidMove = true;
            System.out.println("You can't move backwards");
        } else if (isOutOfBounds) {
            isInvalidMove = true;
            System.out.println("You can't go to empty tiles");
        } else if (isInvalidLocation) {
            isInvalidMove = true;
            System.out.println("Please move to location with same symbol or with same color.");
        }
        return isInvalidMove;
    }

    public String whyInvalidMove(int[] userMoveCoords, String nextMove) {
        boolean isOutOfBounds = this.checkIfMoveWillBeOutOfBounds(userMoveCoords);
        boolean isBackwards = this.myPlayer.checkUserNotMovingBackwards(nextMove);
        boolean isNotInLineDirection = this.myPlayer.checkIfStraightDirection(userMoveCoords);
        boolean isInvalidLocation = this.checkIfNextMoveInValidLocation(userMoveCoords);
        if (isNotInLineDirection) {
            return "NO MOVING IN DIAGONALS YOU CHEATER...";
        } else if (isBackwards) {
            return "You can't move backwards";
        } else if (isOutOfBounds) {
            return "You can't go to empty tiles";
        } else if (isInvalidLocation) {
            return "Please move to location with same symbol or with same color.";
        } else {
            return "I don't know why this is an invalid move :(";
        }
    }

    private boolean checkIfNextMoveInValidLocation(int[] userMoveCoords) {
        boolean isInvalidLocation = true;
        Tile player = this.getPlayer();
        Tile aTile = this.getTile(userMoveCoords);
        if (player.color == aTile.color || player.symbol == aTile.symbol) {
            isInvalidLocation = false;
        }
        return isInvalidLocation;
    }

    private void setPlayerPosition(int[] userMoveCoords) {
        for (int i = 0; i < this.myLevelSize; i++) {
            if (myLevel.get(i).x == userMoveCoords[0] & myLevel.get(i).y == userMoveCoords[1]) {
                this.myLevel.get(i).isPlayer = true;
                String currentInfo = this.myLevel.get(i).tileInfo;
                this.myLevel.get(i).tileInfo = currentInfo.substring(0, 3) + '@' + currentInfo.substring(4);
                int row = userMoveCoords[1];
                int column = userMoveCoords[0];
                this.allMyTiles[row][column] = this.myLevel.get(i).tileInfo;
                break;
            }
        }
    }

    public boolean checkIfMoveWillBeOutOfBounds(int[] userMoveCoords) {
        boolean isOutOfBounds = false;
        Tile nextTile = this.getTile(userMoveCoords);
        if (userMoveCoords[0] < 0 || userMoveCoords[1] < 0) {
            isOutOfBounds = true;
        } else if (userMoveCoords[0] > this.myRowSize || userMoveCoords[1] > this.myColumnSize) {
            isOutOfBounds = true;
        } else if (nextTile.isBlankTile) {
            isOutOfBounds = true;
        }
        return isOutOfBounds;
    }

    public Tile getPlayer() {
        Tile myPlayer = null;
        for (Tile aTile : this.myLevel) {
            if (aTile.isPlayer) {
                myPlayer = aTile;
            }
        }
        return myPlayer;
    }


    private void checkIfPlayerOnGoal(int[] userMoveCoords) {
        for (Tile aTile : this.myLevel) {
            if (aTile.isPlayerOnGoal()) {
                this.changeToBlankGoalTile(userMoveCoords);
            }
        }
    }

    private void changeToBlankGoalTile(int[] userMoveCoords) {
        for (int i = 0; i < this.myLevelSize; i++) {
            if (myLevel.get(i).x == userMoveCoords[0] & myLevel.get(i).y == userMoveCoords[1]) {
                this.myLevel.get(i).isPlayer = true;
                String currentInfo = this.myLevel.get(i).tileInfo;
                String aPartOfInfo = "  ";
                this.myLevel.get(i).tileInfo = aPartOfInfo.substring(0, 2) + " " + currentInfo.substring(3);
                int row = userMoveCoords[1];
                int column = userMoveCoords[0];
                this.allMyTiles[row][column] = this.myLevel.get(i).tileInfo;
                break;
            }
        }
    }


    public Tile getTile(int[] tileCoords) {
        Tile myTile = null;
        for (Tile aTile : this.myLevel) {
            if (tileCoords[0] == aTile.x & tileCoords[1] == aTile.y) {
                myTile = aTile;
            }
        }
        return myTile;
    }

    private void addPlayer() {
        for (Tile aTile : this.myLevel) {
            if (aTile.isPlayer == true) {
                int playerY = aTile.y;
                int playerX = aTile.x;
                Player aPlayer = new Player(playerX, playerY);
                this.myPlayer = aPlayer;
            }
        }
    }

    private void clearMyLevel() {
        this.myLevel.clear();
    }

    private void removePlayerPosition() {
        for (int i = 0; i < this.myLevelSize; i++) {
            if (myLevel.get(i).isPlayer == true) {
                String currentInfo = this.myLevel.get(i).tileInfo;
                String newInfo = currentInfo.substring(0, 3) + ' ' + currentInfo.substring(4);
                int row = this.myLevel.get(i).y;
                int column = this.myLevel.get(i).x;
                this.previousPlayerPosition[1] = this.myLevel.get(i).y;
                this.previousPlayerPosition[0] = this.myLevel.get(i).x;
                this.allMyTiles[row][column] = newInfo;
            }
        }

    }

    private void checkIfLevelPassed() {
        boolean aWin = false;
        for (int i = 0; i < this.myLevelSize; i++) {
            if (myLevel.get(i).isGoal == true) {
                aWin = true;
                return;
            } else {
                aWin = false;
            }
        }
        if (this.levelPassed == aWin) {
            this.levelPassed = true;
        }
    }

    private void addTiles() {
        this.myRowSize = this.allMyTiles.length;
        for (int i = 0; i < this.myRowSize; i++) {
            int y = i;
            this.myColumnSize = this.allMyTiles[i].length;
            for (int j = 0; j < this.allMyTiles[i].length; j++) {
                int x = j;
                Tile aNewTile = new Tile(x, y, this.allMyTiles[i][j]);
                aNewTile.buildMyTile();
                this.myLevel.add(aNewTile);
            }
        }
        this.myLevelSize = this.myLevel.size();
    }

    private void printMyLevel() {
        for (String[] aRow : this.allMyTiles) {
            System.out.println(Arrays.toString(aRow));
        }
    }
//	public String toString() {
//		return ("Object: " + this.myLevel);
//	}


}
