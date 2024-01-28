package com.example.rythmgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.logging.Level;

public class ChooseLevelActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    // Получение компонентов activity
    // ...

    // Переменные для работы приложения
    private Button selectedLevel;   // Выбранный уровень
    private String selectedSong;    // Выбранная песня

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_level);

            sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

            // Получение компонентов activity
            ImageButton LevelImageButton = findViewById(R.id.LevelImageButton);

            // Установка обложки выбранной песни
            selectedSong = sharedPreferences.getString("selectedSong", ChooseSongActivity.songName.get(0));
            LevelImageButton.setImageResource(ChooseSongActivity.songs.get(selectedSong));

            // Первый уровень выбран по умолчанию
            selectedLevel = findViewById(R.id.level1);
            selectedLevel.setBackgroundResource(R.drawable.button_level_selected_style);
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void backToActivityChooseSong(View view) {
        // Переход к activity выбора песни
        Intent i = new Intent(getApplicationContext(), ChooseSongActivity.class);
        startActivity(i);
    }


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

    public void startActivityGame(View view) {
        Intent i = new Intent(getApplicationContext(), Game.class);
        startActivity(i);
    }
}