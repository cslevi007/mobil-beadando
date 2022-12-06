package hu.uni.miskolc.u678mf.storeData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import hu.uni.miskolc.u678mf.R;
import hu.uni.miskolc.u678mf.storeData.model.BasketballPlayer;

public class StoreDataActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText jerseyNumber;
    private EditText team;
    private CheckBox conditions;

    private Button saveButton;

    private BasketballPlayerDatabase db;
    private BasketballPlayerDao basketballPlayerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_data);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        jerseyNumber = findViewById(R.id.jerseyNumberEditText);
        team = findViewById(R.id.teamEditText);
        conditions = findViewById(R.id.conditionsCheckBox);
        saveButton = findViewById(R.id.saveButton);

        db = Room.databaseBuilder(getApplicationContext(), BasketballPlayerDatabase.class, "basketballPlayers").build();
        basketballPlayerDao = db.basketballPlayerDao();

        conditions.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                saveButton.setEnabled(true);
            } else {
                saveButton.setEnabled(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup dataLayout = findViewById(R.id.dataLayout);
                if(formInputControll(dataLayout)) {
                    BasketballPlayer basketballPlayer = new BasketballPlayer(
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            Integer.parseInt(jerseyNumber.getText().toString()),
                            team.getText().toString()
                    );
                    StringBuilder sb = new StringBuilder();
                    sb.append(basketballPlayer.getFirstName());
                    sb.append(" ");
                    sb.append(basketballPlayer.getLastName());
                    sb.append(" - ");
                    sb.append(basketballPlayer.getJerseyNumber());
                    sb.append(" - ");
                    sb.append(basketballPlayer.getTeam());

                    File file = new File(getExternalFilesDir("") + "/players.txt");

//                  Write in file
                    try {
                        FileWriter writer = new FileWriter(file, true);
                        writer.write(sb.toString());
                        writer.append("\n");
                        writer.flush();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.saveFailedToast), Toast.LENGTH_SHORT).show();
                    }

                    // Database
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(basketballPlayerDao.findSame(basketballPlayer).size() == 0) {
                                basketballPlayerDao.insert(basketballPlayer);

                                File file = new File(getExternalFilesDir("") + "/databaseContent.txt");

                                try {
                                    FileWriter writer = new FileWriter(file, false);
                                    writer.write(basketballPlayerDao.getAll().toString());
                                    writer.flush();
                                    writer.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getBaseContext(), getResources().getString(R.string.saveFailedToast), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                    Toast.makeText(getBaseContext(), getResources().getString(R.string.savedSuccessfullyToast), Toast.LENGTH_LONG).show();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            firstName.setText(null);
                            lastName.setText(null);
                            jerseyNumber.setText(null);
                            team.setText(null);
                            conditions.setChecked(false);
                            jerseyNumber.clearFocus();
                            team.clearFocus();
                        }
                    });
                }
            }
        });
    }

    private boolean formInputControll(ViewGroup layout) {
        boolean result = true;
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof EditText) {
                EditText editText = (EditText) child;
                int inputType = editText.getInputType();
                if (editText.getText().toString().trim().isEmpty()) {
                    result = false;
                    editText.setError("Kötelező!");
                }
            }
        }
        return result;
    }
}