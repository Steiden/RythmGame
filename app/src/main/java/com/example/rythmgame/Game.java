package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends AppCompatActivity {

    // Элементы активити
    private RelativeLayout gameContainer;
    private TextView scoreTextView;
    private TextView accuracyTextView;

    private TextView timerTextView; // Текст таймера обратного отсчета
    private CountDownTimer timer;   // Игровой таймер

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_game);

            // Получение главного контейнера игры
            gameContainer = findViewById(R.id.gameContainer);
            // Получение TextView счета
            scoreTextView = findViewById(R.id.scoreTextView);
            // Получение TextView точности
            accuracyTextView = findViewById(R.id.accuracyTextView);

            // Получение timerTextView
            timerTextView = findViewById(R.id.timerTextView);

            // Запуск таймера
            timer = new CountDownTimer(3000, 1000) {
                @SuppressLint("SetTextI18n")
                public void onTick(long millisUntilFinished) {
                    timerTextView.setText("" + (millisUntilFinished / 1000 + 1));
                }

                public void onFinish() {
                    timerTextView.setText("");
                    new GamePlay(getApplicationContext(), gameContainer, scoreTextView, accuracyTextView)
                            .createAndPlaceNote();
                }
            }.start();

        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Переходы между активити
    public void startChooseLevelActivity(View view) {
        GameTransitionHelper.startChooseLevelActivity(this);
    }
}