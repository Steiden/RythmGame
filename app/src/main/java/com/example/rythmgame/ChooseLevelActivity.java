package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChooseLevelActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    // Переменные для работы приложения

    private Button selectedLevel;   // Выбранный уровень

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_level);

            sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

            // Получение компонентов activity
            ImageButton LevelImageButton = findViewById(R.id.LevelImageButton);

            // Установка обложки выбранной песни
            String selectedSong = sharedPreferences.getString("selectedSong", ChooseSongActivity.songName.get(0));
            LevelImageButton.setImageResource(ChooseSongActivity.songs.get(selectedSong));

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

    // Переходы между активити
    public void startActivityChooseSong(View view) {
        GameTransitionHelper.startChooseSongActivity(this);
    }
    public void startActivityGame(View view) {
        GameTransitionHelper.startGameActivity(this);
    }
}