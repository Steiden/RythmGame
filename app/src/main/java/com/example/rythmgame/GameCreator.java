package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameCreator {

    // Переменные из активити создания игры
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final RelativeLayout gameContainer;
    private final View trackbar;
    private final TextView noteCountTextView;
    private final MediaPlayer songMusic;

    private int noteCount;  // Общее количество созданных нот
    private long millisToEnd;   // Количество оставшихся миллисекунд до окончания создания уровня
    private final Song selectedSong;  // Выбранная песня
    private final List<Song> songsList; // Список песен
    private final List<NoteTiming> noteTimingsList;   // Список таймингов нот для песни

    public GameCreator(Context context, Song selectedSong, List<Song> songsList) {
        this.context = context;
        this.gameContainer = GameCreatorActivity.gameContainer;
        this.trackbar = GameCreatorActivity.trackbar;
        this.noteCountTextView = GameCreatorActivity.noteCount;
        this.songMusic = GameCreatorActivity.songMusic;

        this.selectedSong = selectedSong;
        this.songsList = songsList;

        this.noteTimingsList = new ArrayList<>();

        noteCount = 0;
        millisToEnd = this.songMusic.getDuration();
    }

    public void setMillisToEnd(long value) { this.millisToEnd = value; }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    public void startCreatingGame() {
        // При клике на контейнер нот, сохранять время и положение нажатия
        gameContainer.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Получение координат нажатия
                float[] coordinates = new float[2];
                coordinates[0] = event.getX();
                coordinates[1] = event.getY();

                // Создание нового тайминга для ноты
                NoteTiming noteTiming = new NoteTiming(coordinates, this.millisToEnd);

                Log.d("Note timing", "x: " + coordinates[0] + " " + "y: " + coordinates[1] + " " + "ms: " + millisToEnd);

                // Добавление тайминга ноты в список
                noteTimingsList.add(noteTiming);

                // Инкрементирование количества созданных нот и их вывод
                noteCount++;
                noteCountTextView.setText("notes: " + noteCount);

                // Создание вспышки после нажатия
                new FlashElement(this.context, gameContainer)
                        .create()
                        .place(coordinates[0], coordinates[1]);


                return true;
            }

            return false;
        });

        // Запуск трекбара
        GameHelper gameHelper = new GameHelper(context);
        gameHelper.startTrackbar(this.trackbar, songMusic.getDuration());

        // Запуск музыки
        songMusic.setOnCompletionListener(l -> closeCreatingGame());
        songMusic.start();

        // Запуск таймера
        GameTimer.start(this.songMusic.getDuration(), 1,
                this::setMillisToEnd, this::saveData);
    }

    public void closeCreatingGame() {
        // Завершение музыки
        songMusic.release();

        // Сохранение данных
        saveData();

        // Переход на следующую активити
        GameTransitionHelper.startChooseLevelActivity(context);
    }

    private void saveData() {
        this.selectedSong.setNoteTimings(this.noteTimingsList);
        this.songsList.set(this.selectedSong.getId() - 1, this.selectedSong);

        GameFileHelper.saveSelectedSong(this.context, this.selectedSong);
        GameFileHelper.saveSongsList(this.context, this.songsList);

        Log.d("Save note timings", "Successfully");
    }
}
