package com.example.rythmgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameCreatorActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static RelativeLayout gameContainer;
    @SuppressLint("StaticFieldLeak")
    public static View trackbar;
    @SuppressLint("StaticFieldLeak")
    public static TextView noteCount;
    public static MediaPlayer songMusic;

    private GamePlay gamePlay;
    private GameCreator gameCreator;

    private File songsListFile;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_creator);

            // Переходы между активити
            findViewById(R.id.pauseButton).setOnClickListener(v -> gameCreator.closeCreatingGame());

            // __________Получение элементов activity__________

            // Получение контейнера, где располагаются ноты
            gameContainer = findViewById(R.id.gameContainer);
            // Получение трекбара
            trackbar = findViewById(R.id.trackbar);
            // Получение TextView количества созданных нот
            noteCount = findViewById(R.id.noteCount);
            // Получение текущей музыки
            songMusic = MediaPlayer.create(this, R.raw.komarovo);
            // Получение timerTextView
            TextView timerTextView = findViewById(R.id.timerTextView);

            // _________________________________________________

            Song song = new Song("Believer");

            Gson gson = new Gson();
            String json = gson.toJson(song);

            songsListFile = new File(getExternalFilesDir(null), "songsList.json");
            if(!songsListFile.exists()) {
                if(!songsListFile.createNewFile()) {
                    Log.e("Creating new songs list file", "File doesn't create");
                }
                else {
                    Log.println(Log.INFO, "Creating songs list file", "File was create");
                }
            }

            try (FileWriter writer = new FileWriter(songsListFile)) {
                writer.write(json);
                Log.println(Log.INFO, "Write data into songs list file", "Data saved");
            } catch (Exception e) {
                Log.e("Save test data", e.getMessage());
            }

            // Запуск таймера
            GameTimer.start(3000, 1000,
                    (long millisUntilFinished) -> timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + 1)),
                    () -> {
                        timerTextView.setText("");
                        startCreateGame();
                    });
        } catch (Exception ex) {
            Log.e("GameCreatorActivity", "Error: " + ex.getMessage());
        }
    }

    private void startCreateGame() {
        gamePlay = new GamePlay(this);
        gameCreator = new GameCreator(this);
        gameCreator.startCreatingGame();

        Gson gson = new Gson();

        try (FileReader reader = new FileReader(songsListFile)) {
            Song song = gson.fromJson(reader, Song.class);
            Toast.makeText(this, song.getName(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Log.e("Load text data", ex.getMessage());
        }
    }
}