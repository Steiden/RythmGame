package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ChooseSongActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    // Компоненты activity
    private ImageButton SelectedSongImageButton;

    // Переменные для работы приложения
    public static HashMap<String, Integer> songs; // Песни и их обложка
    public static ArrayList<String> songName;  // Названия песен
    private String selectedSong;   // Выбранная песня

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_song);

            // Переходы между активити
            findViewById(R.id.back).setOnClickListener(v -> GameTransitionHelper.startMainActivity(this));
            findViewById(R.id.selectedSongImageButton).setOnClickListener(v -> GameTransitionHelper.startChooseLevelActivity(this));

            sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

            // Получение компонентов activity
            SelectedSongImageButton = findViewById(R.id.selectedSongImageButton);

            // Массив с названиями песен
            songName = new ArrayList<>();
            songName.add("believer");
            songName.add("natural");

            // Заполнение списка песен
            songs = new HashMap<>();
            songs.put(songName.get(0), R.drawable.believer_imagine_dragons);
            songs.put(songName.get(1), R.drawable.natural_imagine_dragons);

            // Установка последнего выбранного уровня
            selectedSong = sharedPreferences.getString("selectedSong", songName.get(0));
            SelectedSongImageButton.setImageResource(songs.get(selectedSong));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Переключение на следующую песню
    public void setNextSong(View view) {
        // Ничего не делать, если достигнута последняя песня
        if (songName.indexOf(selectedSong) + 1 >= songName.size()) return;

        // Сохранение выбранной песни
        saveSelectedSong(songName.get(songName.indexOf(selectedSong) + 1));
    }

    // Переключение на предыдущую песню
    public void setPrevSong(View view) {
        // Ничего не делать, если достигнута первая песня
        if (songName.indexOf(selectedSong) - 1 < 0) return;

        // Сохранение выбранном песни
        saveSelectedSong(songName.get(songName.indexOf(selectedSong) - 1));
    }

    // Сохранение выбранной песни
    private void saveSelectedSong(String song) {
        try {
            selectedSong = song;

            // Сохранение выбранной песни
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("selectedSong", selectedSong);
            editor.apply();

            // Установка изображения для выбранной песни
            SelectedSongImageButton.setImageResource(songs.get(selectedSong));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}