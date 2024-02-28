package com.example.rythmgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
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

    private GameCreator gameCreator;
    private Song selectedSong;

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
            // Получение timerTextView
            TextView timerTextView = findViewById(R.id.timerTextView);

            // _________________________________________________


            // Получение выбранной песни
            this.selectedSong = GameFileHelper.getSelectedSong(this);

            // Установка музыки для песни
            assert this.selectedSong != null;
            songMusic = MediaPlayer.create(this, this.selectedSong.getSong());

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
        List<Song> songsList = GameFileHelper.getSongsList(this);

        gameCreator = new GameCreator(this, this.selectedSong, songsList);
        gameCreator.startCreatingGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            gameCreator.closeCreatingGame();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}