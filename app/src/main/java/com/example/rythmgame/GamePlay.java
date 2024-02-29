package com.example.rythmgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GamePlay {

    // Игровые переменные
    private static int score;  // Счет
    private static float accuracy; // Точность
    // ____________________________________________________


    // Переменные для работы в активити
    @SuppressLint("StaticFieldLeak")
    public static Context context;  // Контекст активити игры
    private final RelativeLayout NoteContainerParent;   // Контейнер для размещения нот
    @SuppressLint("StaticFieldLeak")
    private static final TextView scoreTextView = GameActivity.scoreTextView;   // TextView для счета
    @SuppressLint("StaticFieldLeak")
    private static final TextView accuracyTextView = GameActivity.accuracyTextView;    // TextView для точности
    private final View trackbar;    // Трекбар для продолжительности уровня
    private final MediaPlayer songMusic;    // Игровая музыка
    // ____________________________________________________


    // Для функционала игры
    private static final ArrayList<Float> accuracyList = new ArrayList<>();    // Список всех точностей
    private final List<NoteTiming> selectedSongNoteTimings; // Тайминги нот для выбранной песни
    private int currentNoteIndex = 0;    // Текущая нота
    private CountDownTimer gameTimer;    // Игровой таймер
    // ____________________________________________________


    // Конструктор класса
    public GamePlay(Context context, Song selectedSong, List<Song> songsList) {
        this.context = context;
        this.NoteContainerParent = GameActivity.gameContainer;
        this.trackbar = GameActivity.trackbar;
        this.songMusic = GameActivity.songMusic;

        score = 0;
        accuracy = 100;

        // Выбранная песня
        this.selectedSongNoteTimings = selectedSong.getNoteTimings();
    }

    // Сеттеры
    public static void setScore(int value) {
        score += value;

        scoreTextView.setText(String.valueOf(score));
    }

    @SuppressLint("SetTextI18n")
    public static void setAccuracy(float value) {
        try {
            accuracyList.add(value);

            // Подсчет общей суммы точности
            float accuracySum = 0;
            for (float el : accuracyList) {
                accuracySum += el;
            }

            accuracy = (float) Math.round((accuracySum / accuracyList.size()) * 100) / 100;

            accuracyTextView.setText(accuracy + "%");
        } catch (Exception ex) {
            Log.e("Setting accuracy", ex.getMessage());
        }
    }


    // Начало игры
    public void startGame() {
        try {
            // Запуск трекбара
            GameHelper gameHelper = new GameHelper(context);
            gameHelper.startTrackbar(this.trackbar, songMusic.getDuration());

            // Запуск музыки
            songMusic.start();

            // Запуск игрового таймера
            gameTimer = GameTimer.start(songMusic.getDuration(), 1,
                    (long millisUntilFinished) -> {
                        NoteTiming noteTiming = this.selectedSongNoteTimings.get(this.currentNoteIndex);
                        if (GameHelper.TimeInRange(noteTiming.getTime(), millisUntilFinished)) {
                            createAndPlaceNote();
                            this.currentNoteIndex++;

                            if (this.currentNoteIndex >= this.selectedSongNoteTimings.size())
                                gameTimer.cancel();
                        }
                    }, this::closeGame);
        } catch (Exception ex) {
            Log.e("Starting game", ex.getMessage());
        }
    }


    // Закрытие игры
    public void closeGame() {
        try {
            // Завершение музыки
            songMusic.release();

            // Закрытие игрового таймера
            gameTimer.cancel();

            // Переход на следующую активити
            GameTransitionHelper.startChooseLevelActivity(context);
        } catch (Exception ex) {
            Log.e("Closing game", ex.getMessage());
        }
    }


    // Создание и размещение ноты
    public void createAndPlaceNote() {
        try {
            // Получение координат для размещения ноты
            float[] coordinates = selectedSongNoteTimings.get(this.currentNoteIndex).getCoordinates();

            new GameNote(context, this.NoteContainerParent)
                    .create()
                    .place(coordinates[0] - 90, coordinates[1] + 90);

            Log.d("Creating and placing note", "Successfully");
        } catch (Exception ex) {
            Log.e("Creating and placing note", ex.getMessage());
        }
    }
}
