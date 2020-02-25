package src.eyeball_game_android;

import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class GameActivity extends AppCompatActivity {

    private Menu menu;
    private String currentModel;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.currentModel = getIntent().getStringExtra("currentModel");
    }

    private void createController() {
        switch (this.currentModel) {
            case "leroi":
            default:
                this.adapter = new AdapterLeroi(this, findViewById(R.id.layout), this.menu, findViewById(R.id.toolbar));
                break;
        }

    }

    private String namesToUpper(String input) {
        switch (input) {
            case "LeRoi":
            default:
                return "LeRoi";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;
        this.menu.findItem(R.id.action_current).setTitle("Current Model: " + this.namesToUpper(this.currentModel));
        this.createController();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_load:
                this.promptForInput(true);
                break;
            case R.id.action_restart:
                this.adapter.restart();
                break;
            case R.id.action_undo:
                this.adapter.undo();
                break;
            case R.id.action_sound:
                this.adapter.sound(item);
                break;
            case R.id.action_save:
                this.promptForInput(false);
                break;
            case R.id.action_home:
                this.finish();
                break;
            default:
                Toast.makeText(getApplicationContext(), "This button does not have an effect", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void promptForInput(Boolean load) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Maze Name:");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            if (load) this.adapter.load(input.getText().toString());
            else this.adapter.save(input.getText().toString());
        });
        builder.show();
    }

}
