package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseLevelActivity extends AppCompatActivity {

    private Button selectedLevel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_level);

            // Переходы между активити
            findViewById(R.id.back).setOnClickListener(v -> GameTransitionHelper.startChooseSongActivity(this));
            findViewById(R.id.play).setOnClickListener(v -> GameTransitionHelper.startGameActivity(this));
            findViewById(R.id.create).setOnClickListener(v -> GameTransitionHelper.startGameCreatorActivity(this));

            // Получение выбранной песни
            Song selectedSong = GameFileHelper.getSelectedSong(this);

            // Установка названия, автора и обложки песни
            assert selectedSong != null;
            ((TextView) findViewById(R.id.songName)).setText(selectedSong.getName());
            ((TextView) findViewById(R.id.songAuthor)).setText(selectedSong.getAuthor());
            ((ImageButton) findViewById(R.id.levelImageButton)).setImageResource(selectedSong.getImage());

            // Первый уровень выбран по умолчанию
            selectedLevel = findViewById(R.id.level1);
            selectedLevel.setBackgroundResource(R.drawable.button_level_selected_style);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Выбор уровня для песни
    public void selectLevel(View view) {
        if(selectedLevel != null) {
            // Возврат цвета старого выбранного уровня к начальному
            selectedLevel.setBackgroundResource(R.drawable.button_level_style);
        }

        // Установка выбранного уровня
        selectedLevel = findViewById(view.getId());
        // Установка цвета выбранного уровня выбранному уровню
        view.setBackgroundResource(R.drawable.button_level_selected_style);
    }
}