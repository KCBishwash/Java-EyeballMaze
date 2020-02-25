package src.leroi_chua_model;

public class Player {
    public int px;
    public int py;
    public String currentDirection = "UP";
    public String lastDirection;

    public Player(int x, int y) {
        this.px = x;
        this.py = y;
    }

    public boolean checkUserNotMovingBackwards(String nextMove) {
        boolean isBackwards = false;
        if (this.currentDirection.equalsIgnoreCase("UP") & nextMove.equalsIgnoreCase("DOWN")) {
            isBackwards = true;
        } else if (this.currentDirection.equalsIgnoreCase("DOWN") & nextMove.equalsIgnoreCase("UP")) {
            isBackwards = true;
        } else if (this.currentDirection.equalsIgnoreCase("LEFT") & nextMove.equalsIgnoreCase("RIGHT")) {
            isBackwards = true;
        } else if (this.currentDirection.equalsIgnoreCase("RIGHT") & nextMove.equalsIgnoreCase("LEFT")) {
            isBackwards = true;
        } else if (nextMove.equalsIgnoreCase("DIAGONAL")) {
            isBackwards = true;
        }
        return isBackwards;
    }

    public String orientPlayer(int[] nextMove) {
        String playerDirection = null;
        if (this.px > nextMove[0] & this.py == nextMove[1]) {
            playerDirection = "LEFT";
        } else if (this.px < nextMove[0] & this.py == nextMove[1]) {
            playerDirection = "RIGHT";
        } else if (this.py > nextMove[1] & this.px == nextMove[0]) {
            playerDirection = "UP";
        } else if (this.py < nextMove[1] & this.px == nextMove[0]) {
            playerDirection = "DOWN";
        } else {
            playerDirection = "DIAGONAL";
        }
        return playerDirection;
    }

    public void setLastDirection() {
        this.lastDirection = this.currentDirection;
    }

    public void setCurrentDirection(String nextMove) {
        this.currentDirection = nextMove;
    }

    public boolean checkIfStraightDirection(int[] userMoveCoords) {
        boolean notInLine = false;
        if (this.px != userMoveCoords[0] & this.py != userMoveCoords[1]) {
            notInLine = true;
        }
        return notInLine;
    }


    public void updatePosition(int[] coords) {
        this.py = coords[1];
        this.px = coords[0];
    }

    public String toString() {
        return "I am player at: " + this.px + " py: " + this.py;
    }

}
