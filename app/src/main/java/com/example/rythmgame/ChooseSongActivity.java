package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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


            // Если списка песен нет, то нужно создать новый__________________________
            if (songsList == null) {
                this.songsList = new ArrayList<>();

                // Первая песня_______________________________________________________
                Song song1 = new Song(1, "Believer", "Imagine Dragons",
                        R.drawable.believer_imagine_dragons, R.raw.believer);
                HashMap<Integer, List<NoteTiming>> difficults1 = new HashMap<>();
                difficults1.put(2, new ArrayList<>());
                difficults1.put(6, new ArrayList<>());
                difficults1.put(12, new ArrayList<>());
                song1.setDifficults(difficults1);

                songsList.add(song1);

                // Вторая песня_______________________________________________________
                Song song2 = new Song(2, "Natural", "Imagine Dragons",
                        R.drawable.natural_imagine_dragons, R.raw.natural);
                HashMap<Integer, List<NoteTiming>> difficults2 = new HashMap<>();
                difficults2.put(1, new ArrayList<>());
                difficults2.put(4, new ArrayList<>());
                difficults2.put(9, new ArrayList<>());
                song2.setDifficults(difficults2);

                songsList.add(song2);
            }
            // ___________________________________________________________________



            /*
              TODO: То, что выше. Сделать, желательно, отдельный класс или метод для восстановления
               базовых уровней, если такие не найдены. Пусть их будет как минимум 5. Все NoteTimings
               для каждой сложности песни должны также быть подставлены туда по дефолту
            */


            // Сохранение списка песен____________________________________________
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