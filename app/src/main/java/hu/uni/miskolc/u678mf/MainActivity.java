package hu.uni.miskolc.u678mf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.uni.miskolc.u678mf.musicplayer.SongsActivity;
import hu.uni.miskolc.u678mf.news.NewsActivity;
import hu.uni.miskolc.u678mf.storeData.StoreDataActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button musicPlayerButton;
    private  EditText musicPlayerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("NYELV", "Az aktuális nyelv" + getResources().getConfiguration().locale.getLanguage());

        musicPlayerButton = findViewById(R.id.musicPlayerButton);
        musicPlayerButton.setEnabled(false);

        musicPlayerEditText = findViewById(R.id.musicPlayerEditText);
        musicPlayerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    musicPlayerButton.setEnabled(true);
                } else {
                    musicPlayerButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Log.i(TAG, "Ez egy proba üzenet");
        Log.d(TAG, "Ez egy debug üzenet");
    }

    public void goToMusicPlayerActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SongsActivity.class);
        intent.putExtra("name", musicPlayerEditText.getText().toString());
        startActivity(intent);
    }

    public void goToStoreData(View view) {
        Intent intent = new Intent(MainActivity.this, StoreDataActivity.class);
        startActivity(intent);
    }

    public void goToNews(View view) {
        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
        startActivity(intent);
    }
}