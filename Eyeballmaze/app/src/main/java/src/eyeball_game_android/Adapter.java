package src.eyeball_game_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public abstract class Adapter {

    protected SoundManager sound = new SoundManager();
    protected Context context;
    protected TableLayout layout;
    protected Menu menu;
    protected Toolbar toolbar;

    Adapter(Context newContext, TableLayout newLayout, Menu newMenu, Toolbar newToolbar) {
        this.context = newContext;
        this.layout = newLayout;
        this.menu = newMenu;
        this.toolbar = newToolbar;
    }

    protected void mazeComplete() {
        Toast.makeText(this.context, "Well Done!!!!", Toast.LENGTH_SHORT).show();
        this.sound.playSound(true, this.context);
        this.inflateMenu();
    }

    protected void mazeFailed() {
        Toast.makeText(this.context, "Too Many Moves, Try Again!!!", Toast.LENGTH_SHORT).show();
        this.sound.playSound(false, this.context);
        this.inflateMenu();
    }

    protected void setGoalsMoves(int newGoalsCount, int newMovesCount) {
        MenuItem goals = this.menu.findItem(R.id.action_goals);
        MenuItem moves = this.menu.findItem(R.id.action_moves);
        goals.setTitle("Goals Remaining: " + newGoalsCount);
        moves.setTitle("Moves: " + newMovesCount);
    }

    abstract void load(String fileName);

    abstract void save(String fileName);

    abstract void restart();

    abstract void undo();

    public void sound(MenuItem item) {
        this.sound.toggleSound(item);
    }

    protected Drawable getRotateDrawable(final Drawable d, final float angle) {
        final Drawable[] arD = {d};
        return new LayerDrawable(arD) {
            @Override
            public void draw(final Canvas canvas) {
                canvas.save();
                canvas.rotate(angle, d.getBounds().width() / 2, d.getBounds().height() / 2);
                super.draw(canvas);
                canvas.restore();
            }
        };
    }


    protected String readFromStorage(String fileName) {
        String output;
        try {
            FileInputStream fis = new FileInputStream(new File(context.getFilesDir() + "/Mazes/" + fileName + ".txt"));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
            }
            bufferedReader.close();
            output = sb.toString();
        } catch (Exception e) {
            output = "NOTHING";
        }
        return output;
    }

    protected void writeToStorage(Context mcoContext, String sFileName, String sBody) {
        File file = new File(mcoContext.getFilesDir(), "Mazes");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File newFile = new File(file, sFileName);
            FileWriter writer = new FileWriter(newFile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e) {

        }
    }

    protected void inflateMenu() {
        this.toolbar.showOverflowMenu();
    }
}
