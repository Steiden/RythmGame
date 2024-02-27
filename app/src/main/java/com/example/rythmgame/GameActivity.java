package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

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
    private Song selectedSong;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            // Событие при нажатии на кнопку паузы
            findViewById(R.id.pauseButton).setOnClickListener(v -> gamePlay.closeGame());

            // Получение данных о смартфоне
            this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            // Получение главного контейнера игры
            gameContainer = findViewById(R.id.gameContainer);
            // Получение TextView счета
            scoreTextView = findViewById(R.id.scoreTextView);
            // Получение TextView точности
            accuracyTextView = findViewById(R.id.accuracyTextView);
            // Получение View трекбара
            trackbar = findViewById(R.id.trackbar);

            // Получение timerTextView
            timerTextView = findViewById(R.id.timerTextView);

            // Получение выбранной песни
            this.selectedSong = GameFileHelper.getSelectedSong(this);

            // Установка музыки песни
            songMusic = MediaPlayer.create(this, this.selectedSong.getSong());

            // Запуск таймера
            GameTimer.start(3000, 1000,
                    (long millisUntilFinished) -> timerTextView.setText(String.valueOf(millisUntilFinished / 1000 + 1)),
                    () -> {
                        timerTextView.setText("");
                        startGame();
                    });

        } catch (Exception ex) {
            Log.e("GameActivity", ex.getMessage());
        }
    }

    private void startGame() {
        List<Song> songsList = GameFileHelper.getSongsList(this);

        gamePlay = new GamePlay(this, this.selectedSong, songsList);
        gamePlay.startGame();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            gamePlay.closeGame();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}