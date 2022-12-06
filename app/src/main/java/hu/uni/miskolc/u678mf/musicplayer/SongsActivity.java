package hu.uni.miskolc.u678mf.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import hu.uni.miskolc.u678mf.MainActivity;
import hu.uni.miskolc.u678mf.R;

public class SongsActivity extends AppCompatActivity {

    TextView songsTextView;
    RecyclerView songsRecyclerView;
    TextView noSongsTextView;
    ArrayList<AudioModel> songsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        songsTextView = findViewById(R.id.songs_text);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if(getResources().getConfiguration().locale.getLanguage() == "hu") {
            songsTextView.setText(name + " " + getBaseContext().getString(R.string.whoseMusicPlayer));
        } else {
            songsTextView.setText(name + "'s " + getBaseContext().getString(R.string.whoseMusicPlayer));
        }


        songsRecyclerView = findViewById(R.id.songsRecyclerView);
        noSongsTextView = findViewById(R.id.no_songs_text);

        if(checkPermission() == false) {
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";

        // Store all the music files in cursor
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);

        // Create a list of songs from the cursor
        while(cursor.moveToNext()) {
            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));
            if(new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if(songsList.size() == 0) {
            noSongsTextView.setVisibility(View.VISIBLE);
        } else {
            // recyclerView
            songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            songsRecyclerView.setAdapter(new SongsListAdapter(songsList, getApplicationContext()));
        }
    }
    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(SongsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED) {
            return  true;
        } else {
            return false;
        }
    }
    void requestPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(SongsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(SongsActivity.this, getResources().getString(R.string.readPermissionRequiredToast), Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(SongsActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(songsRecyclerView != null){
            songsRecyclerView.setAdapter(new SongsListAdapter(songsList, getApplicationContext()));
        }
    }
}