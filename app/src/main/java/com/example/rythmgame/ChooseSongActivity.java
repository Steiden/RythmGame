package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseSongActivity extends AppCompatActivity {

    // Переменные для работы приложения
    private List<Song> songsList;  // Список песен
    private Song selectedSong;   // Выбранная песня
    private GameFile<Song> gameFileOfSelectedSong;  // Управление игровыми файлами

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_choose_song);

            // Переходы между активити
            findViewById(R.id.back).setOnClickListener(v -> GameTransitionHelper.startMainActivity(this));
            findViewById(R.id.selectedSongImageButton).setOnClickListener(v -> GameTransitionHelper.startChooseLevelActivity(this));

            // Получение списка песен
            this.songsList = GameFileHelper.getSongsList(this);

            if (songsList == null) {
                this.songsList = new ArrayList<>();
                songsList.add(new Song(1, "Believer", "Imagine Dragons",
                        R.drawable.believer_imagine_dragons, R.raw.believer));
                songsList.add(new Song(2, "Natural", "Imagine Dragons",
                        R.drawable.natural_imagine_dragons, R.raw.natural));
            }

            GameFileHelper.saveSongsList(this, songsList);

            // Установка выбранного уровня в качестве первого
            saveSelectedSong(songsList.get(0));
        } catch (Exception ex) {
            Log.e("ChooseSongActivity Error", ex.getMessage());
        }
    }

    // Переключение на следующую песню
    public void setNextSong(View view) {
        // Ничего не делать, если достигнута последняя песня
        if (this.songsList.indexOf(this.selectedSong) + 1 >= this.songsList.size()) return;

        // Сохранение выбранной песни
        saveSelectedSong(this.songsList.get(this.songsList.indexOf(this.selectedSong) + 1));
    }

    // Переключение на предыдущую песню
    public void setPrevSong(View view) {
        // Ничего не делать, если достигнута первая песня
        if (this.songsList.indexOf(this.selectedSong) - 1 < 0) return;

        // Сохранение выбранном песни
        saveSelectedSong(this.songsList.get(this.songsList.indexOf(this.selectedSong) - 1));
    }

    // Сохранение выбранной песни
    private void saveSelectedSong(Song song) {
        try {
            // Установка выбранной песни
            this.selectedSong = song;

            // Сохранение выбранной песни в файл
            GameFileHelper.saveSelectedSong(this, this.selectedSong);

            // Установка названия, автора и обложки песни
            ((TextView) findViewById(R.id.songName)).setText(this.selectedSong.getName());
            ((TextView) findViewById(R.id.songAuthor)).setText(this.selectedSong.getAuthor());
            ((ImageButton) findViewById(R.id.selectedSongImageButton)).setImageResource(this.selectedSong.getImage());
        } catch (Exception ex) {
            Log.e("Save selected song", ex.getMessage());
        }
    }
}