package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    public void startActivityChooseSong(View view) {
        Intent i = new Intent(getApplicationContext(), ChooseSongActivity.class);
        startActivity(i);
    }

    public void startActivitySettings(View view) {
        Intent i = new Intent(getApplication(), SettingsActivity.class);
        startActivity(i);
    }

    public void ExitActivity(View view) {
        this.finishAffinity();
    }
}