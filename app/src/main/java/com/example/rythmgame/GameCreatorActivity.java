package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game_creator);

            // Переходы между активити
            findViewById(R.id.pauseButton).setOnClickListener(v -> gameCreator.closeCreatingGame());

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

            // Запуск таймера
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + 1));
                }
                public void onFinish() {
                    timerTextView.setText("");
                    startCreateGame();
                }
            }.start();
        } catch (Exception ex) {
            Log.e("GameCreatorActivity", "Error: " + ex.getMessage());
        }
    }

    private void startCreateGame() {
        gamePlay = new GamePlay(this);
        gameCreator = new GameCreator(this);
        gameCreator.startCreatingGame();
    }
}