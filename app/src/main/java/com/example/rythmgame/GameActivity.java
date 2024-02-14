package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    // Данные смартфона
    public static Vibrator vibrator;

    // Элементы активити
    @SuppressLint("StaticFieldLeak")
    public static RelativeLayout gameContainer;
    @SuppressLint("StaticFieldLeak")
    public static TextView scoreTextView;
    @SuppressLint("StaticFieldLeak")
    public static TextView accuracyTextView;
    @SuppressLint("StaticFieldLeak")
    public static View trackbar;
    public static MediaPlayer songMusic;
    private TextView timerTextView; // Текст таймера обратного отсчета

    private GamePlay gamePlay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            // Событие при нажатии на кнопку паузы
            findViewById(R.id.pauseButton).setOnClickListener(v -> gamePlay.closeGame());

            // Получение данных о смартфоне
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            // Получение главного контейнера игры
            gameContainer = findViewById(R.id.gameContainer);
            // Получение TextView счета
            scoreTextView = findViewById(R.id.scoreTextView);
            // Получение TextView точности
            accuracyTextView = findViewById(R.id.accuracyTextView);
            // Получение View трекбара
            trackbar = findViewById(R.id.trackbar);
            // Получение музыки для игры
            songMusic = MediaPlayer.create(this, R.raw.komarovo);

            // Получение timerTextView
            timerTextView = findViewById(R.id.timerTextView);

            // Запуск таймера
            new CountDownTimer(3000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + 1));
                }
                public void onFinish() {
                    timerTextView.setText("");
                    startGame();
                }
            }.start();

        } catch (Exception ex) {
            Log.e("GameActivity", "Error: " + ex.getMessage());
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void startGame() {
        gamePlay = new GamePlay(this);
        gamePlay.startGame();
    }
}