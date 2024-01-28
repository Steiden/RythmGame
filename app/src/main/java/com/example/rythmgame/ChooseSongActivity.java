package com.example.rythmgame;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_song);

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("selectedSong", selectedSong);
    }

    public void goToActivityMain(View view) {
        // Переключение на другую activity
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void startActivityChooseLevel(View view) {
        // Сохранение выбранной песни
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedSong", selectedSong);
        editor.apply();

        // Переход к активити выбора уровня песни
        Intent i = new Intent(getApplicationContext(), ChooseLevelActivity.class);
        startActivity(i);
    }

    public void nextSong(View view) {
        try {
            if (songName.indexOf(selectedSong) + 1 >= songName.size()) return;

            selectedSong = songName.get(songName.indexOf(selectedSong) + 1);
            SelectedSongImageButton.setImageResource(songs.get(selectedSong));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void prevSong(View view) {
        try {
            if (songName.indexOf(selectedSong) - 1 < 0) return;

            selectedSong = songName.get(songName.indexOf(selectedSong) - 1);
            SelectedSongImageButton.setImageResource(songs.get(selectedSong));
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}