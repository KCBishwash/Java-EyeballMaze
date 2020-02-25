package src.eyeball_game_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void alertHelp(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.readme)
                .setTitle("Here's a bit more Help!");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onClickBtn(View v) {
        String currentModel;
        switch (v.getId()) {
            case R.id.button_leroi:
            default:
                currentModel = "leroi";
                break;
        }
        Intent myIntent = new Intent(this, GameActivity.class);
        myIntent.putExtra("currentModel", currentModel);
        startActivity(myIntent);
    }

}