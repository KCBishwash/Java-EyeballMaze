package src.eyeball_game_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

public class MazeDisplay {

    private TableLayout layout;

    MazeDisplay(TableLayout newLayout) {
        this.layout = newLayout;
    }

    public TableLayout getLayout() {
        return layout;
    }

    public void updateLayout(Context context, ArrayList<ArrayList<ArrayList<Drawable>>> map, View.OnClickListener imageClickListener) {
        this.layout.removeAllViews();
        for (int i1 = 0; i1 < map.size(); i1++) {
            TableRow row = new TableRow(context);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setPadding(1, 1, 1, 1);
            for (int i2 = 0; i2 < map.get(i1).size(); i2++) {
                ImageView image = new ImageView(context);
                Drawable[] drawables = new Drawable[3];
                drawables = map.get(i1).get(i2).toArray(drawables);
                if (map.get(i1).get(i2).size() > 1) {
                    LayerDrawable layerDrawable = new LayerDrawable(this.removeNull(drawables));
                    image.setImageDrawable(layerDrawable);
                } else {
                    image.setImageDrawable(drawables[0]);
                }
                int[] xy = {i2, i1};
                image.setTag(xy);
                image.setPadding(1, 1, 1, 1);
                image.setOnClickListener(imageClickListener);
                row.addView(image);
            }
            layout.addView(row);
        }
    }

    public Drawable[] removeNull(Drawable[] a) {
        ArrayList<Drawable> removedNull = new ArrayList<Drawable>();
        for (Drawable str : a)
            if (str != null)
                removedNull.add(str);
        return removedNull.toArray(new Drawable[0]);
    }
}
