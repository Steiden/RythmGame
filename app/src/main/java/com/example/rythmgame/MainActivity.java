package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Переходы между активити
        findViewById(R.id.play).setOnClickListener(v -> GameTransitionHelper.startChooseSongActivity(this));
        findViewById(R.id.settings).setOnClickListener(v -> GameTransitionHelper.startSettingsActivity(this));
        findViewById(R.id.exit).setOnClickListener(v -> finishAffinity());

        // Получение всех звезд на главном экране
        TextView[] stares = new TextView[]{
                findViewById(R.id.star1),
                findViewById(R.id.star2),
                findViewById(R.id.star3),
                findViewById(R.id.star4),
                findViewById(R.id.star5),
                findViewById(R.id.star6),
                findViewById(R.id.star7)
        };

        // Установка анимации всем звездам
        for (TextView star : stares) {
            star.animate().alpha(1).rotation(360).translationY(1500).setDuration(10000);
        }
    }
}