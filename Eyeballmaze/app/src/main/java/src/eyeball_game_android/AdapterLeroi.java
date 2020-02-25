package src.eyeball_game_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

import src.leroi_chua_model.Color;
import src.leroi_chua_model.Level;
import src.leroi_chua_model.Symbol;
import src.leroi_chua_model.Tile;

public class AdapterLeroi extends Adapter {

    private String[][][] myLevels = {
            {{"SB   ", "FYX *", "FG   ", "FB  *", "SR  *"},
                    {"CR   ", "DG   ", "CY   ", "CG   ", "CY   "},
                    {"DR   ", "CG   ", "FY   ", "DB  *", "DR  *"},
                    {"     ", "DY @*", "     ", "     ", "     "}},

            {{"SB   ", "FY  *", "FG   ", "FB  *", "SR  *"},
                    {"CR   ", "DGX  ", "CY   ", "CG   ", "CY   "},
                    {"DR   ", "CG   ", "FY   ", "DB  *", "DR  *"},
                    {"     ", "DY @*", "     ", "     ", "     "}},

            {{"SBX  ", "FY  *", "FG   "},
                    {"CR   ", "DG   ", "CY   "},
                    {"SR   ", "SY   ", "FY   "},
                    {"     ", "DY @*", "     "}}};

    private int currentLevel = 0;
    private String[][] currentLevelString;
    private ArrayList<ArrayList<ArrayList<Drawable>>> map;
    private MazeDisplay display;
    private Level level;

    AdapterLeroi(Context newContext, TableLayout newLayout, Menu newMenu, Toolbar newToolbar) {
        super(newContext, newLayout, newMenu, newToolbar);
        this.display = new MazeDisplay(this.layout);
    }

    public static String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }

        final String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
            // For Java versions prior to Java 6 use the next:
            // System.arraycopy(original[i], 0, result[i], 0, original[i].length);
        }
        return result;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private ArrayList<ArrayList<ArrayList<Drawable>>> mapFromLevelString(String[][] allMyTiles) {
        ArrayList<ArrayList<ArrayList<Drawable>>> output = new ArrayList<>();
        for (int i = 0; i < allMyTiles.length; i++) {
            ArrayList<ArrayList<Drawable>> rowImages = new ArrayList<>();
            for (int i2 = 0; i2 < allMyTiles[i].length; i2++) {
                int[] xy = {i2, i};
                ArrayList<Drawable> tileImages = new ArrayList<>();
                Tile current = this.level.getTile(xy);
                if (current.isBlankTile) {
                    tileImages.add(this.context.getResources().getDrawable(R.drawable.empty));
                } else {
                    tileImages.add(this.tileToDrawable(current));
                }
                if (current.isGoal && !current.isBlankGoal) {
                    tileImages.add(this.context.getResources().getDrawable(R.drawable.goal));
                }
                if (current.isPlayer) {
                    tileImages.add(this.getPlayerAsDrawable(this.level.myPlayer.currentDirection));
                }
                rowImages.add(tileImages);
            }
            output.add(rowImages);
        }
        return output;
    }

    private Drawable getPlayerAsDrawable(String direction) {
        Drawable drawable = this.context.getResources().getDrawable(R.drawable.player);
        switch (direction) {
            case "UP":
            default:
                break;
            case "RIGHT":
                drawable = getRotateDrawable(drawable, 90);
                break;
            case "LEFT":
                drawable = getRotateDrawable(drawable, 270);
                break;
            case "DOWN":
                drawable = getRotateDrawable(drawable, 180);
                break;

        }
        return drawable;
    }

    private Drawable tileToDrawable(Tile tile) {
        String result = "";
        result += Color.getStringFromColor(tile.color) + "_" + Symbol.getStringFromSymbol(tile.symbol);
        int id = this.context.getResources().getIdentifier(result, "drawable", this.context.getPackageName());
        if (id != 0) {
            return this.context.getResources().getDrawable(id);
        } else {
            return this.context.getResources().getDrawable(R.drawable.empty);
        }
    }

    public void load(String fileName) {
        this.resolveCurrentLevel(fileName);
        this.clear();
        // we need to deep copy this array or else the moves we make keep getting updated into it and we can never reset the array
        this.currentLevelString = deepCopy(this.myLevels[this.currentLevel]);
        this.level = new Level("Level " + this.currentLevel, currentLevelString);
        this.level.buildMyLevel();
        this.updateMaze();
    }

    private void resolveCurrentLevel(String fileName) {
        if (isNumeric(fileName)) {
            int input = Integer.parseInt(fileName) - 1;
            if (input < this.myLevels.length && input >= 0) {
                this.currentLevel = input;
                Toast.makeText(context, "Loading Maze!" + (input + 1) + " out of " + this.myLevels.length, Toast.LENGTH_SHORT).show();
            }
        } else {
            String adjustedFileName = "LeRoi_Maze_" + fileName;
            String result = readFromStorage(adjustedFileName);
            if (result.equals("NOTHING")) {
                this.currentLevel = 0;
            } else {
                this.currentLevel = Integer.parseInt(result);
                Toast.makeText(context, "Loaded File " + adjustedFileName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clear() {
        this.map = new ArrayList<>();
        this.display = new MazeDisplay(this.layout);
        this.layout.removeAllViews();
        this.level = null;
    }

    private void updateMaze() {
        this.map = this.mapFromLevelString(this.level.getAllMyTiles());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] xy = (int[]) view.getTag();
                String newDirection = level.myPlayer.orientPlayer(xy);
                if (!level.checkIfValidMove(xy, newDirection)) {
                    level.updateMyLevel(xy);
                } else {
                    Toast.makeText(context, level.whyInvalidMove(xy, newDirection), Toast.LENGTH_SHORT).show();
                }
                updateMaze();
            }
        };
        this.display.updateLayout(this.context, this.map, listener);
        this.layout = this.display.getLayout();
        this.setGoalsMoves(this.level.getRemainingGoalsCount(), this.level.moveCounter);
        if (this.level.levelPassed) {
            this.mazeComplete();
            this.currentLevel++;
        }
        if (this.level.moveCounter > 20) {
            this.mazeFailed();
        }
    }

    public void save(String fileName) {
        String adjustedFileName = "LeRoi_Maze_" + fileName + ".txt";
        this.writeToStorage(this.context, adjustedFileName, Integer.toString(this.currentLevel));
        Toast.makeText(context, "Saved as " + adjustedFileName, Toast.LENGTH_SHORT).show();
    }

    public void restart() {
        this.load("");
    }

    void undo() {
        level.undo();
        this.updateMaze();
    }
}
