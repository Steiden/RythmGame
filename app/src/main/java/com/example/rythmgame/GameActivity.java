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

    GamePlay gamePlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

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
                @SuppressLint("SetTextI18n")
                public void onTick(long millisUntilFinished) {
                    // Таймер отсчета до начала игры
                    timerTextView.setText("" + (millisUntilFinished / 1000 + 1));
                }

                public void onFinish() {
                    timerTextView.setText("");

                    // Запуск игры
                    gamePlay = new GamePlay(getApplicationContext());
                    gamePlay.startGame();
                }
            }.start();
        } catch (Exception ex) {
            Log.e("GameActivity", "Error: " + ex.getMessage());
            Toast.makeText(this, "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Переходы между активити
    public void startChooseLevelActivity(View view) {
        try {
            gamePlay.closeGame();
            GameTransitionHelper.startChooseLevelActivity(this);
        }
        catch (Exception ex) {
            Log.e("GameActivity", "Error: " + ex.getMessage());
        }
    }
}